package main;

import entity.Entity;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventMaster = new Entity(gp);

        eventRect = new EventRect[gp.getMaxMap()][gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.getMaxMap() && col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.getMaxWorldCol()) {
                col = 0;
                row++;

                if (row == gp.getMaxWorldRow()) {
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue();
    }

    public void setDialogue() {

        eventMaster.getDialogues()[0][0] = "Ouch! I fell into a pit!";

        eventMaster.getDialogues()[1][0] = "You drink the water.\nYour life and mana have been recovered.\nHowever... monsters are alive another time!";
        eventMaster.getDialogues()[1][1] = "YOUR PROGRESS HAS BEEN SAVED.";
    }

    public void checkEvent() {
        // Check if player is more than 1 tile away from last event
        int xDistance = Math.abs(gp.getPlayer().getWorldX() - previousEventX);
        int yDistance = Math.abs(gp.getPlayer().getWorldY() - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.getTileSize()) {
            canTouchEvent = true;
        }
        if (canTouchEvent == true) {
            // Damage pit
            if (hit(0, 27, 16, "right") == true) {
                damagePit(gp.getDialogueState());
            }
            // Healing pool
            else if (hit(0, 21, 12, "up") == true) {
                healingPool(gp.getDialogueState());
            } 
            // Healing pool
            else if (hit(0, 22, 12, "up") == true) {
                healingPool(gp.getDialogueState());
            } 
            // Healing pool
            else if (hit(0, 23, 12, "up") == true) {
                healingPool(gp.getDialogueState());
            } 
            // Healing pool
            else if (hit(0, 24, 12, "up") == true) {
                healingPool(gp.getDialogueState());
            } 
            // Healing pool
            else if (hit(0, 25, 12, "up") == true) {
                healingPool(gp.getDialogueState());
            } 
            // Teleport World -> Merchant Hut
            else if (hit(0, 10, 39, "any") == true) {
                teleport(1, 12, 12, gp.getIndoor());
            } 
            // Teleport Merchant Hut -> World
            else if (hit(1, 12, 13, "any") == true) {
                teleport(0, 10, 40, gp.getOutside());
            } 
            // Speak to NPC if press enter in front of table
            else if (hit(1, 12, 9, "up") == true) {
                speak(gp.getNpc()[1][0]);
            } 	
            // Teleport World -> Dungeon F1
            else if (hit(0, 12, 9, "any") == true) {
                teleport(2, 9, 41, gp.getDungeon());
            } 
            // Teleport Dungeon F1 -> World
            else if (hit(2, 9, 41, "any") == true) {
                teleport(0, 12, 9, gp.getOutside());
            } 
            // Teleport Dungeon F1 -> Dungeon F2
            else if (hit(2, 8, 7, "any") == true) {
                teleport(3, 26, 41, gp.getDungeon());
            } 
            // Teleport Dungeon F2 -> Dungeon F1
            else if (hit(3, 26, 41, "any") == true) {
                teleport(2, 8, 7, gp.getDungeon());
            } 
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.getCurrentMap()) {
            gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
            gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;
            eventRect[map][col][row].x = col * gp.getTileSize() + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.getTileSize() + eventRect[map][col][row].y;

            // Check if player's solidArea is colliding with eventRect's solidArea
            if (gp.getPlayer().getSolidArea().intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if (gp.getPlayer().getDirection().contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.getPlayer().getWorldX();
                    previousEventY = gp.getPlayer().getWorldY();
                }
            }

            gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
            gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    public void damagePit(int gameState) {
        gp.setGameState(gameState);
        gp.playSFX(6);
        eventMaster.startDialogue(eventMaster, 0);
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        if (gp.getKeyH().isEnterPressed() == true) {
            gp.setGameState(gameState);
            gp.getPlayer().setAttackCanceled(true);
            gp.playSFX(2);
            eventMaster.startDialogue(eventMaster, 1);
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
            gp.getPlayer().setMana(gp.getPlayer().getMaxMana());
            gp.getaSetter().setMonster();

            // Save game
            gp.getSaveLoad().save();
        }
    }

    public void teleport(int map, int col, int row, int area) {

        gp.setGameState(gp.getTransitionState());

        gp.setNextArea(area);

        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSFX(13);
    }

    public void speak(Entity entity) {
        if (gp.getKeyH().isEnterPressed() == true) {
            gp.setGameState(gp.getDialogueState());
            gp.getPlayer().setAttackCanceled(true);
            entity.speak();
        }
    }
}
