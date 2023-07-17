package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][]; // [MapNumber][Col][Row]
    public boolean drawPath = false;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Read tile data file
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Getting tile names and collision info from file
        String line;

        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
        }

        // Get tile images
        tile = new Tile[fileNames.size()];
        getTileImage();

        // Get maxWorldCol and maxWorldRow based on map size (50x50, 100x100, 250x250)
        is = getClass().getResourceAsStream("/maps/worldmap.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");

            gp.setMaxWorldCol(maxTile.length);
            gp.setMaxWorldRow(maxTile.length);
            mapTileNum = new int[gp.getMaxMap()][gp.getMaxWorldCol()][gp.getMaxWorldRow()];

            br.close();
        } catch (IOException e) {
        }

        // Load maps
//		loadMap("/maps/sample.txt", 0);
        loadMap("/maps/worldmap.txt", 0);
        loadMap("/maps/indoor01.txt", 1);
        loadMap("/maps/dungeon01.txt", 2);
        loadMap("/maps/dungeon02.txt", 3);
    }

    public void getTileImage() {
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName;
            boolean collision;

            // Get file name
            fileName = fileNames.get(i);

            // Get collision status
            if (collisionStatus.get(i).equals("true")) {
                collision = true;
            } else {
                collision = false;
            }
            setup(i, fileName, collision);
        }
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.getTileSize(), gp.getTileSize());
            tile[index].collision = collision;
        } catch (IOException e) {
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                int col = 0;
                int row = 0;

                while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
                    String line = br.readLine();
                    while (col < gp.getMaxWorldCol()) {
                        String numbers[] = line.split(" ");
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[map][col][row] = num;
                        col++;
                    }
                    if (col == gp.getMaxWorldCol()) {
                        col = 0;
                        row++;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {
            int tileNum = mapTileNum[gp.getCurrentMap()][worldCol][worldRow];
            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

            if (worldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX()
                    && worldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX()
                    && worldY + gp.getTileSize() > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY()
                    && worldY - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) { // Does not render tiles if they are off the screen size and not visible			
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if (worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }

        if (drawPath == true) {
            g2.setColor(new Color(255, 0, 0, 70));

            for (int i = 0; i < gp.getpFinder().pathList.size(); i++) {
                int worldX = gp.getpFinder().pathList.get(i).getCol() * gp.getTileSize();
                int worldY = gp.getpFinder().pathList.get(i).getRow() * gp.getTileSize();
                int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
                int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

                g2.fillRect(screenX, screenY, gp.getTileSize(), gp.getTileSize());
            }
        } else if (drawPath == false) {
            gp.getpFinder().clearPath();
        }
    }
}
