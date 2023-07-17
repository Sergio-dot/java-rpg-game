package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.Pathfinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 12;
    private final int tileSize = originalTileSize * scale; // 48x48 tile
    private final int screenWidth = tileSize * maxScreenCol; // 960px
    private final int screenHeight = tileSize * maxScreenRow; // 576px

    // World settings
    private int maxWorldCol;
    private int maxWorldRow;
    private final int maxMap = 10;
    private int currentMap = 0;

    // Full screen settings
    private int screenWidth2 = screenWidth;
    private int screenHeight2 = screenHeight;
    private BufferedImage tempScreen;
    private Graphics2D g2;
    private boolean fullScreenOn = false;

    // FPS
    private final int FPS = 60;

    // System
    private final TileManager tileM = new TileManager(this);
    private final KeyHandler keyH = new KeyHandler(this, tileM);
    private final CollisionChecker cChecker = new CollisionChecker(this);
    private final UI ui = new UI(this);
    private final EventHandler eHandler = new EventHandler(this);
    private final Pathfinder pFinder = new Pathfinder(this);
    private final EntityGenerator eGenerator = new EntityGenerator(this);
    private final AssetSetter aSetter = new AssetSetter(this);
    private final Config config = new Config(this);
    private final EnvironmentManager eManager = new EnvironmentManager(this);
    private final Map map = new Map(this);
    private final SaveLoad saveLoad = new SaveLoad(this);
    private final Sound music = new Sound();
    private final Sound SFX = new Sound();
    private Thread gameThread;

    // Entity and object
    private final Player player = new Player(this, keyH);
    private final Entity obj[][] = new Entity[maxMap][20];
    private final Entity npc[][] = new Entity[maxMap][10];
    private final Entity monster[][] = new Entity[maxMap][20];
    private final Entity projectile[][] = new Entity[maxMap][20];
    private final InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    private final ArrayList<Entity> particleList = new ArrayList<>();
    private final ArrayList<Entity> entityList = new ArrayList<>(); // We sort the order of the array. The entity that has the lowest worldY come in index 0.

    // Game state
    private int gameState;
    private final int titleState = 0;
    private final int playState = 1;
    private final int pauseState = 2;
    private final int dialogueState = 3;
    private final int characterState = 4;
    private final int optionState = 5;
    private final int gameOverState = 6;
    private final int transitionState = 7;
    private final int tradeState = 8;
    private final int sleepState = 9;
    private final int mapState = 10;

    // Areas
    private int currentArea;
    private int nextArea;
    private final int outside = 50;
    private final int indoor = 51;
    private final int dungeon = 52;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // May improve game rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        // Set default environment on map
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTiles();
        eManager.setup();

        gameState = titleState;

        // Set World Map (outside) as default map
        currentArea = outside;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn == true) {
            setFullScreen();
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void resetGame(boolean restart) {

        currentArea = outside;
        player.setDefaultPosition();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        if (restart == true) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTiles();
            eManager.getLighting().resetDay();
        }
    }

    public void setFullScreen() {
        // Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // Get full screen width and height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 1 billion nanoseconds = 1 second
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            // System.out.println("T:" + timer);
            // System.out.println("D:" + delta);

            if (delta >= 1) {
                update();
                drawToTempScreen(); // Draw everything to the buffered image
                drawToScreen(); // Draw the buffered image to the screen
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                // System.out.println("FPS: " + drawCount); // Print FPS on the console
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {

            // Player
            player.update();

            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            // Monster
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].isAlive() == true && monster[currentMap][i].isDying() == false) {
                        monster[currentMap][i].update();
                    }
                    if (monster[currentMap][i].isAlive() == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            // Projectile
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].isAlive() == true) {
                        projectile[currentMap][i].update();
                    }
                    if (projectile[currentMap][i].isAlive() == false) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            // Particle
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).isAlive() == true) {
                        particleList.get(i).update();
                    }
                    if (particleList.get(i).isAlive() == false) {
                        particleList.remove(i);
                    }
                }
            }

            // Interactive tiles
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
            eManager.update();
        }

        if (gameState == pauseState) {
            // Do nothing
        }
    }

    public void drawToTempScreen() {
        // Debugging
        long drawStart = 0;
        if (keyH.isShowDebugText() == true) {
            drawStart = System.nanoTime();
        }

        // Title screen
        if (gameState == titleState) {
            ui.draw(g2);
        } else if (gameState == mapState) {
            map.drawFullMapScreen(g2);
        } else { // Others
            // Tile
            tileM.draw(g2);

            // Interactive tiles
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // Add player to entity list
            entityList.add(player);

            // Add NPCs to entity list
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            // Add objects to entity list
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            // Add monsters to entity list
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            // Add projectiles to entity list
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }

            // Add particles to entity list
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            // Sort
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.getWorldY(), e2.getWorldY());

                    return result;
                }
            });

            // Draw entities from list
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // Empty entity list
            entityList.clear();

            // Environment
            eManager.draw(g2);

            // Minimap
            map.drawMiniMap(g2);

            // UI
            ui.draw(g2);
        }

        // Debugging
        if (keyH.isShowDebugText() == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX: " + player.getWorldX(), x, y);
            y += lineHeight;
            g2.drawString("WorldY: " + player.getWorldY(), x, y);
            y += lineHeight;
            g2.drawString("Col: " + (player.getWorldX() + player.getSolidArea().x) / getTileSize(), x, y);
            y += lineHeight;
            g2.drawString("Row: " + (player.getWorldY() + player.getSolidArea().y) / getTileSize(), x, y);
            y += lineHeight;
            g2.drawString("Draw time: " + passed, x, y);
            y += lineHeight;
            g2.drawString("God Mode: " + keyH.isGodMode(), x, y);
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playBGM(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopBGM() {
        music.stop();
    }

    public void playSFX(int i) {
        SFX.setFile(i);
        SFX.play();
    }

    public void changeArea() {
        if (nextArea != currentArea) {
            stopBGM();

            if (nextArea == outside) {
                playBGM(0);
            }

            if (nextArea == indoor) {
                playBGM(18);
            }

            if (nextArea == dungeon) {
                playBGM(19);
            }

            aSetter.setNPC();
        }
        currentArea = nextArea;
        aSetter.setMonster();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public void setMaxWorldCol(int maxWorldCol) {
        this.maxWorldCol = maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public void setMaxWorldRow(int maxWorldRow) {
        this.maxWorldRow = maxWorldRow;
    }

    public int getMaxMap() {
        return maxMap;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    public boolean isFullScreenOn() {
        return fullScreenOn;
    }

    public void setFullScreenOn(boolean fullScreenOn) {
        this.fullScreenOn = fullScreenOn;
    }

    public TileManager getTileM() {
        return tileM;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public CollisionChecker getcChecker() {
        return cChecker;
    }

    public UI getUi() {
        return ui;
    }

    public EventHandler geteHandler() {
        return eHandler;
    }

    public Pathfinder getpFinder() {
        return pFinder;
    }

    public EntityGenerator geteGenerator() {
        return eGenerator;
    }

    public AssetSetter getaSetter() {
        return aSetter;
    }

    public Config getConfig() {
        return config;
    }

    public EnvironmentManager geteManager() {
        return eManager;
    }

    public Map getMap() {
        return map;
    }

    public SaveLoad getSaveLoad() {
        return saveLoad;
    }

    public Sound getBGM() {
        return music;
    }

    public Sound getSFX() {
        return SFX;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity[][] getObj() {
        return obj;
    }

    public Entity[][] getNpc() {
        return npc;
    }

    public Entity[][] getMonster() {
        return monster;
    }

    public Entity[][] getProjectile() {
        return projectile;
    }

    public InteractiveTile[][] getiTile() {
        return iTile;
    }

    public ArrayList<Entity> getParticleList() {
        return particleList;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getTitleState() {
        return titleState;
    }

    public int getPlayState() {
        return playState;
    }

    public int getPauseState() {
        return pauseState;
    }

    public int getDialogueState() {
        return dialogueState;
    }

    public int getCharacterState() {
        return characterState;
    }

    public int getOptionState() {
        return optionState;
    }

    public int getGameOverState() {
        return gameOverState;
    }

    public int getTransitionState() {
        return transitionState;
    }

    public int getTradeState() {
        return tradeState;
    }

    public int getSleepState() {
        return sleepState;
    }

    public int getMapState() {
        return mapState;
    }

    public int getCurrentArea() {
        return currentArea;
    }

    public void setNextArea(int nextArea) {
        this.nextArea = nextArea;
    }

    public int getOutside() {
        return outside;
    }

    public int getIndoor() {
        return indoor;
    }

    public int getDungeon() {
        return dungeon;
    }
}
