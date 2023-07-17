package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        int entityRightCol = entityRightWorldX / gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        int tileNum1, tileNum2;

        // Use a temporal direction when it's being knockbacked
        String direction = entity.getDirection();
        if (entity.isKnockBack() == true) {
            direction = entity.getKnockBackDirection();
        }

        switch (direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().mapTileNum[gp.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().mapTileNum[gp.getCurrentMap()][entityRightCol][entityTopRow];
                if (gp.getTileM().tile[tileNum1].collision == true || gp.getTileM().tile[tileNum2].collision == true) {
                    entity.setCollisionOn(true);
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().mapTileNum[gp.getCurrentMap()][entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileM().mapTileNum[gp.getCurrentMap()][entityRightCol][entityBottomRow];
                if (gp.getTileM().tile[tileNum1].collision == true || gp.getTileM().tile[tileNum2].collision == true) {
                    entity.setCollisionOn(true);
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().mapTileNum[gp.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().mapTileNum[gp.getCurrentMap()][entityLeftCol][entityBottomRow];
                if (gp.getTileM().tile[tileNum1].collision == true || gp.getTileM().tile[tileNum2].collision == true) {
                    entity.setCollisionOn(true);
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().mapTileNum[gp.getCurrentMap()][entityRightCol][entityTopRow];
                tileNum2 = gp.getTileM().mapTileNum[gp.getCurrentMap()][entityRightCol][entityBottomRow];
                if (gp.getTileM().tile[tileNum1].collision == true || gp.getTileM().tile[tileNum2].collision == true) {
                    entity.setCollisionOn(true);
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        // Use a temporal direction when it's being knockbacked
        String direction = entity.getDirection();
        if (entity.isKnockBack() == true) {
            direction = entity.getKnockBackDirection();
        }

        for (int i = 0; i < gp.getObj()[1].length; i++) {
            if (gp.getObj()[gp.getCurrentMap()][i] != null) {
                // Get entity's solid  area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;
                // Get the object's solid area position
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x = gp.getObj()[gp.getCurrentMap()][i].getWorldX() + gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x;
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y = gp.getObj()[gp.getCurrentMap()][i].getWorldY() + gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y;

                switch (direction) {
                    case "up" ->
                        entity.getSolidArea().y -= entity.getSpeed();
                    case "down" ->
                        entity.getSolidArea().y += entity.getSpeed();
                    case "left" ->
                        entity.getSolidArea().x -= entity.getSpeed();
                    case "right" ->
                        entity.getSolidArea().x += entity.getSpeed();
                }

                if (entity.getSolidArea().intersects(gp.getObj()[gp.getCurrentMap()][i].getSolidArea())) { // If entity's rectangle touch object's rectangle
                    if (gp.getObj()[gp.getCurrentMap()][i].isCollision() == true) {
                        entity.setCollisionOn(true);
                    }
                    if (player == true) {
                        index = i;
                    }
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x = gp.getObj()[gp.getCurrentMap()][i].getSolidAreaDefaultX();
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y = gp.getObj()[gp.getCurrentMap()][i].getSolidAreaDefaultY();
            }
        }

        return index;
    }

    // Check NPC or monster collision
    public int checkEntity(Entity entity, Entity[][] target) {

        int index = 999;

        // Use a temporal direction when it's being knockbacked
        String direction = entity.getDirection();
        if (entity.isKnockBack() == true) {
            direction = entity.getKnockBackDirection();
        }

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.getCurrentMap()][i] != null) {
                // Get entity's solid  area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;
                // Get target's solid area position
                target[gp.getCurrentMap()][i].getSolidArea().x = target[gp.getCurrentMap()][i].getWorldX() + target[gp.getCurrentMap()][i].getSolidArea().x;
                target[gp.getCurrentMap()][i].getSolidArea().y = target[gp.getCurrentMap()][i].getWorldY() + target[gp.getCurrentMap()][i].getSolidArea().y;

                switch (direction) {
                    case "up" ->
                        entity.getSolidArea().y -= entity.getSpeed();
                    case "down" ->
                        entity.getSolidArea().y += entity.getSpeed();
                    case "left" ->
                        entity.getSolidArea().x -= entity.getSpeed();
                    case "right" ->
                        entity.getSolidArea().x += entity.getSpeed();
                }

                if (entity.getSolidArea().intersects(target[gp.getCurrentMap()][i].getSolidArea())) {
                    if (target[gp.getCurrentMap()][i] != entity) {
                        entity.setCollisionOn(true);
                        index = i;
                    }
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                target[gp.getCurrentMap()][i].getSolidArea().x = target[gp.getCurrentMap()][i].getSolidAreaDefaultX();
                target[gp.getCurrentMap()][i].getSolidArea().y = target[gp.getCurrentMap()][i].getSolidAreaDefaultY();
            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // Get entity's solid  area position
        entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;
        // Get player's solid area position
        gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;

        switch (entity.getDirection()) {
            case "up" ->
                entity.getSolidArea().y -= entity.getSpeed();
            case "down" ->
                entity.getSolidArea().y += entity.getSpeed();
            case "left" ->
                entity.getSolidArea().x -= entity.getSpeed();
            case "right" ->
                entity.getSolidArea().x += entity.getSpeed();
        }

        if (entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
            entity.setCollisionOn(true);
            contactPlayer = true;
        }

        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();
        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();

        return contactPlayer;
    }
}
