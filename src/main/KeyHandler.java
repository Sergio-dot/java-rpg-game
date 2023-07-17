package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import tile.TileManager;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    TileManager tileM;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean enterPressed;
    private boolean shotKeyPressed;
    private boolean spacePressed;

    // Debugging
    private boolean showDebugText = false;
    private boolean godMode = false;

    public KeyHandler(GamePanel gp, TileManager tileM) {
        this.gp = gp;
        this.tileM = tileM;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Title state
        if (gp.getGameState() == gp.getTitleState()) {
            titleState(code);
        } // Play state
        else if (gp.getGameState() == gp.getPlayState()) {
            playState(code);
        } // Pause state
        else if (gp.getGameState() == gp.getPauseState()) {
            pauseState(code);
        } // Dialogue state
        else if (gp.getGameState() == gp.getDialogueState()) {
            dialogueState(code);
        } // Character state
        else if (gp.getGameState() == gp.getCharacterState()) {
            characterState(code);
        } // Option state
        else if (gp.getGameState() == gp.getOptionState()) {
            optionState(code);
        } // Game over state
        else if (gp.getGameState() == gp.getGameOverState()) {
            gameOverState(code);
        } // Trade state
        else if (gp.getGameState() == gp.getTradeState()) {
            tradeState(code);
        } // Map state
        else if (gp.getGameState() == gp.getMapState()) {
            mapState(code);
        }
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.getUi().commandNum--;

            if (gp.getUi().commandNum < 0) {
                gp.getUi().commandNum = 2;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.getUi().commandNum++;

            if (gp.getUi().commandNum > 2) {
                gp.getUi().commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER) {

            if (gp.getUi().commandNum == 0) { // New game
                gp.setGameState(gp.getPlayState());
                gp.playBGM(0);
            }

            if (gp.getUi().commandNum == 1) {
                gp.getSaveLoad().load();
                gp.setGameState(gp.getPlayState());
                gp.playBGM(0);
            }

            if (gp.getUi().commandNum == 2) {
                System.exit(0);
            }
        }
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            setUpPressed(true);
        } // Up movement
        if (code == KeyEvent.VK_S) {
            setDownPressed(true);
        } // Down movement
        if (code == KeyEvent.VK_A) {
            setLeftPressed(true);
        } // Left movement
        if (code == KeyEvent.VK_D) {
            setRightPressed(true);
        } // Right movement
        if (code == KeyEvent.VK_P) {
            gp.setGameState(gp.getPauseState());
        } // Pause game
        if (code == KeyEvent.VK_C) {
            gp.setGameState(gp.getCharacterState());
        } // Character window
        if (code == KeyEvent.VK_ENTER) {
            setEnterPressed(true);
        } // Attack
        if (code == KeyEvent.VK_SPACE) {
            setSpacePressed(true);
        } // Guard
        if (code == KeyEvent.VK_F) {
            setShotKeyPressed(true);
        } // Fireball
        if (code == KeyEvent.VK_ESCAPE) {
            gp.setGameState(gp.getOptionState());
        } // Option menu
        if (code == KeyEvent.VK_M) {
            gp.setGameState(gp.getMapState());
        } // Full map
        if (code == KeyEvent.VK_X) { // Minimap
            gp.getMap().miniMapOn = gp.getMap().miniMapOn == false;
        }

        // Debugging
        if (code == KeyEvent.VK_F1) { // Show debug text
            if (isShowDebugText() == false) {
                setShowDebugText(true);
                tileM.drawPath = true;
            } else if (isShowDebugText() == true) {
                setShowDebugText(false);
                tileM.drawPath = false;
            }
        }

        // Enable/disable God Mode
        if (code == KeyEvent.VK_F2) { 
            if (isGodMode() == false) {
                setGodMode(true);
                gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
            } else if (isGodMode() == true) {
                setGodMode(false);
                gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
            }
        }

        // Reset map
        if (code == KeyEvent.VK_F3) {
            switch (gp.getCurrentMap()) {
                case 0 -> {
                    gp.getTileM().loadMap("/maps/worldmap.txt", 0);
                    gp.getaSetter().setObject();
                    gp.getaSetter().setNPC();
                    gp.getaSetter().setMonster();
                    gp.getaSetter().setInteractiveTiles();
                }
                case 1 -> {
                    gp.getTileM().loadMap("/maps/indoor01.txt", 1);
                    gp.getaSetter().setObject();
                    gp.getaSetter().setNPC();
                    gp.getaSetter().setMonster();
                    gp.getaSetter().setInteractiveTiles();
                }
                case 2 -> {
                    gp.getTileM().loadMap("/maps/dungeon01.txt", 2);
                    gp.getaSetter().setObject();
                    gp.getaSetter().setNPC();
                    gp.getaSetter().setMonster();
                    gp.getaSetter().setInteractiveTiles();
                }
                case 3 -> {
                    gp.getTileM().loadMap("/maps/dungeon02.txt", 3);
                    gp.getaSetter().setObject();
                    gp.getaSetter().setNPC();
                    gp.getaSetter().setMonster();
                    gp.getaSetter().setInteractiveTiles();
                }
            }
            gp.getUi().addMessage("MAP RESTORED");
        }
        
        // RETURN TO START POINT
        if (code == KeyEvent.VK_F4) {
            switch(gp.getCurrentMap()) {
                case 0 -> { // WORLD MAP -> MERCHANT HUT
                    gp.geteHandler().teleport(1, 12, 12, gp.getIndoor());
                }
                case 1 -> { // MERCHANT HUT -> DUNGEON F1
                    gp.geteHandler().teleport(2, 9, 41, gp.getDungeon());
                }
                case 2 -> { // DUNGEON F1 -> DUNGEON F2
                    gp.geteHandler().teleport(3, 26, 41, gp.getDungeon());
                }
                case 3 -> { // DUNGEON F2 -> WORLD MAP
                    gp.geteHandler().teleport(0, 23, 21, gp.getOutside());
                }
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.setGameState(gp.getPlayState());
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            setEnterPressed(true);
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.setGameState(gp.getPlayState());
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.getPlayer().selectItem();
        }
        playerInventory(code);
    }

    public void optionState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.setGameState(gp.getPlayState());
        }
        if (code == KeyEvent.VK_ENTER) {
            setEnterPressed(true);
        }

        int maxCommandNum = 0;

        switch (gp.getUi().subState) {
            case 0 -> maxCommandNum = 5;
            case 3 -> maxCommandNum = 1;
        }

        if (code == KeyEvent.VK_W) {
            gp.getUi().commandNum--;
            gp.playSFX(9);
            if (gp.getUi().commandNum < 0) {
                gp.getUi().commandNum = maxCommandNum;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.getUi().commandNum++;
            gp.playSFX(9);
            if (gp.getUi().commandNum > maxCommandNum) {
                gp.getUi().commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.getUi().subState == 0) {
                if (gp.getUi().commandNum == 1 && gp.getBGM().volumeScale > 0) {
                    gp.getBGM().volumeScale--;
                    gp.getBGM().checkVolume();
                    gp.playSFX(9);
                }

                if (gp.getUi().commandNum == 2 && gp.getSFX().volumeScale > 0) {
                    gp.getSFX().volumeScale--;
                    gp.playSFX(9);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.getUi().subState == 0) {
                if (gp.getUi().commandNum == 1 && gp.getBGM().volumeScale < 5) {
                    gp.getBGM().volumeScale++;
                    gp.getBGM().checkVolume();
                    gp.playSFX(9);
                }

                if (gp.getUi().commandNum == 2 && gp.getSFX().volumeScale < 5) {
                    gp.getSFX().volumeScale++;
                    gp.playSFX(9);
                }
            }
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.getUi().commandNum--;

            if (gp.getUi().commandNum < 0) {
                gp.getUi().commandNum = 1;
            }
            gp.playSFX(9);
        }

        if (code == KeyEvent.VK_S) {
            gp.getUi().commandNum++;
            if (gp.getUi().commandNum > 1) {
                gp.getUi().commandNum = 0;
            }

            gp.playSFX(9);
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.getUi().commandNum == 0) {
                gp.setGameState(gp.getPlayState());
                gp.resetGame(false);
                gp.playBGM(0);
            } else if (gp.getUi().commandNum == 1) {
                gp.setGameState(gp.getTitleState());
                gp.getUi().commandNum = 0;
                gp.resetGame(true);
            }
        }
    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            setEnterPressed(true);
        }

        if (gp.getUi().subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.getUi().commandNum--;

                if (gp.getUi().commandNum < 0) {
                    gp.getUi().commandNum = 2;
                }
                gp.playSFX(9);
            }

            if (code == KeyEvent.VK_S) {
                gp.getUi().commandNum++;

                if (gp.getUi().commandNum > 2) {
                    gp.getUi().commandNum = 0;
                }
                gp.playSFX(9);
            }
        }

        if (gp.getUi().subState == 1) {
            npcInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.getUi().subState = 0;
            }
        }

        if (gp.getUi().subState == 2) {
            playerInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.getUi().subState = 0;
            }
        }
    }

    public void mapState(int code) {
        if (code == KeyEvent.VK_M) {
            gp.setGameState(gp.getPlayState());
        }
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {

            if (gp.getUi().playerSlotRow != 0) {
                gp.getUi().playerSlotRow--;
                gp.playSFX(9);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.getUi().playerSlotCol != 0) {

                gp.getUi().playerSlotCol--;
                gp.playSFX(9);
            }
        }

        if (code == KeyEvent.VK_S) {

            if (gp.getUi().playerSlotRow != 3) {
                gp.getUi().playerSlotRow++;
                gp.playSFX(9);
            }
        }

        if (code == KeyEvent.VK_D) {

            if (gp.getUi().playerSlotCol != 4) {
                gp.getUi().playerSlotCol++;
                gp.playSFX(9);
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {

            if (gp.getUi().npcSlotRow != 0) {
                gp.getUi().npcSlotRow--;
                gp.playSFX(9);
            }
        }

        if (code == KeyEvent.VK_A) {

            if (gp.getUi().npcSlotCol != 0) {
                gp.getUi().npcSlotCol--;
                gp.playSFX(9);
            }
        }

        if (code == KeyEvent.VK_S) {

            if (gp.getUi().npcSlotRow != 3) {
                gp.getUi().npcSlotRow++;
                gp.playSFX(9);
            }
        }

        if (code == KeyEvent.VK_D) {

            if (gp.getUi().npcSlotCol != 4) {
                gp.getUi().npcSlotCol++;
                gp.playSFX(9);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            setUpPressed(false);
        }
        if (code == KeyEvent.VK_S) {
            setDownPressed(false);
        }
        if (code == KeyEvent.VK_A) {
            setLeftPressed(false);
        }
        if (code == KeyEvent.VK_D) {
            setRightPressed(false);
        }
        if (code == KeyEvent.VK_F) {
            setShotKeyPressed(false);
        }
        if (code == KeyEvent.VK_ENTER) {
            setEnterPressed(false);
        }
        if (code == KeyEvent.VK_SPACE) {
            setSpacePressed(false);
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public void setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
    }

    public boolean isShotKeyPressed() {
        return shotKeyPressed;
    }

    public void setShotKeyPressed(boolean shotKeyPressed) {
        this.shotKeyPressed = shotKeyPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }

    public boolean isShowDebugText() {
        return showDebugText;
    }

    public void setShowDebugText(boolean showDebugText) {
        this.showDebugText = showDebugText;
    }

    public boolean isGodMode() {
        return godMode;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

}
