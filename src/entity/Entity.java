package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import tile_interactive.InteractiveTile;

public class Entity extends AbstractEntity {
    
    GamePanel gp;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    public int getLeftX() {
        return getWorldX() + getSolidArea().x;
    }

    public int getRightX() {
        return getWorldX() + getSolidArea().x + getSolidArea().width;
    }

    public int getTopY() {
        return getWorldY() + getSolidArea().y;
    }

    public int getBottomY() {
        return getWorldY() + getSolidArea().y + getSolidArea().height;
    }

    public int getCol() {
        return (getWorldX() + getSolidArea().x) / gp.getTileSize();
    }

    public int getRow() {
        return (getWorldY() + getSolidArea().y) / gp.getTileSize();
    }

    public int getCenterX() {
        int centerX = getWorldX() + left1.getWidth() / 2;
        return centerX;
    }

    public int getCenterY() {
        int centerY = getWorldY() + up1.getHeight() / 2;
        return centerY;
    }

    public int getXdistance(Entity target) {
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }

    public int getYdistance(Entity target) {
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }

    public int getTileDistance(Entity target) {
        int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.getTileSize();
        return tileDistance;
    }

    public int getGoalCol(Entity target) {
        int goalCol = (target.getWorldX() + target.getSolidArea().x) / gp.getTileSize();
        return goalCol;
    }

    public int getGoalRow(Entity target) {
        int goalRow = (target.getWorldY() + target.getSolidArea().y) / gp.getTileSize();
        return goalRow;
    }

    public void resetCounter() {
        setSpriteCounter(0);
        setActionLockCounter(0);
        setInvincibleCounter(0);
        setShotAvailableCounter(0);
        setDyingCounter(0);
        setHpBarCounter(0);
        setKnockBackCounter(0);
        setGuardCounter(0);
        setOffBalanceCounter(0);
    }

    public void facePlayer() {
        switch (gp.getPlayer().getDirection()) {
            case "up" ->
                setDirection("down");
            case "down" ->
                setDirection("up");
            case "left" ->
                setDirection("right");
            case "right" ->
                setDirection("left");
        }
    }

    public void startDialogue(Entity entity, int setNum) {
        gp.setGameState(gp.getDialogueState());
        gp.getUi().npc = entity;
        setDialogueSet(setNum);
    }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.getObj()[1].length; i++) {
            if (gp.getObj()[gp.getCurrentMap()][i] == null) {
                gp.getObj()[gp.getCurrentMap()][i] = droppedItem;
                gp.getObj()[gp.getCurrentMap()][i].setWorldX(getWorldX()); // The dead monster's worldX position
                gp.getObj()[gp.getCurrentMap()][i].setWorldY(getWorldY()); // The dead monster's worldY position
                break; // Breaks the loop
            }
        }
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.getParticleList().add(p1);
        gp.getParticleList().add(p2);
        gp.getParticleList().add(p3);
        gp.getParticleList().add(p4);
    }

    public void checkCollision() {
        setCollisionOn(false);
        gp.getcChecker().checkTile(this);
        gp.getcChecker().checkObject(this, false);
        gp.getcChecker().checkEntity(this, gp.getNpc());
        gp.getcChecker().checkEntity(this, gp.getMonster());
        gp.getcChecker().checkEntity(this, gp.getiTile());
        boolean contactPlayer = gp.getcChecker().checkPlayer(this);

        // If monster touch player, it becomes invincible and loses 1 HP
        if (this.getType() == getType_monster() && contactPlayer == true) {
            damagePlayer(getAttack());
        }
    }

    public void update() {
        if (isKnockBack() == true) {
            checkCollision();

            if (isCollisionOn() == true) {
                setKnockBackCounter(0);
                setKnockBack(false);
                setSpeed(getDefaultSpeed());
            } else if (isCollisionOn() == false) {
                switch (getKnockBackDirection()) {
                    case "up" ->
                        setWorldY(getWorldY() - getSpeed());
                    case "down" ->
                        setWorldY(getWorldY() + getSpeed());
                    case "left" ->
                        setWorldX(getWorldX() - getSpeed());
                    case "right" ->
                        setWorldX(getWorldX() + getSpeed());
                }
            }

            setKnockBackCounter(getKnockBackCounter() + 1);
            if (getKnockBackCounter() == 10) {
                setKnockBackCounter(0);
                setKnockBack(false);
                setSpeed(getDefaultSpeed());
            }
        } else if (isAttacking() == true) {
            attacking();
        } else {
            setAction();
            checkCollision();

            // If collision is false, player can move
            if (isCollisionOn() == false) {
                switch (getDirection()) {
                    case "up" ->
                        setWorldY(getWorldY() - getSpeed());
                    case "down" ->
                        setWorldY(getWorldY() + getSpeed());
                    case "left" ->
                        setWorldX(getWorldX() - getSpeed());
                    case "right" ->
                        setWorldX(getWorldX() + getSpeed());
                }
            }

            setSpriteCounter(getSpriteCounter() + 1);
            if (getSpriteCounter() > 24) {
                if (getSpriteNum() == 1) {
                    setSpriteNum(2);
                } else if (getSpriteNum() == 2) {
                    setSpriteNum(1);
                }
                setSpriteCounter(0);
            }
        }

        // Give a temporal invinciple status upon hit
        if (isInvincible() == true) {
            setInvincibleCounter(getInvincibleCounter() + 1);
            if (getInvincibleCounter() > 40) {
                setInvincible(false);
                setInvincibleCounter(0);
            }
        }

        // Prevent to shoot two fireballs when monster is next to the player
        if (getShotAvailableCounter() < 30) {
            setShotAvailableCounter(getShotAvailableCounter() + 1);
        }

        if (isOffBalance() == true) {
            setOffBalanceCounter(getOffBalanceCounter() + 1);
            if (getOffBalanceCounter() > 60) {
                setOffBalance(false);
                setOffBalanceCounter(0);
            }
        }
    }

    public void checkAttack(int rate, int straight, int horizontal) {

        boolean targetInRange = false;
        int xDis = getXdistance(gp.getPlayer());
        int yDis = getYdistance(gp.getPlayer());

        switch (getDirection()) {
            case "up" -> {
                if (gp.getPlayer().getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "down" -> {
                if (gp.getPlayer().getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "left" -> {
                if (gp.getPlayer().getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "right" -> {
                if (gp.getPlayer().getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
            }
        }
        if (targetInRange == true) {
            // Check if it starts an attack
            int i = new Random().nextInt(rate);
            if (i == 0) {
                setAttacking(true);
                setSpriteNum(1);
                setSpriteCounter(0);
                setShotAvailableCounter(0);
            }
        }
    }

    public void checkShoot(int rate, int shotInterval) {

        int i = new Random().nextInt(rate);
        if (i == 0 && getProjectile().isAlive() == false && getShotAvailableCounter() == shotInterval) {
            getProjectile().set(getWorldX(), getWorldY(), getDirection(), true, this);

            // Check vacancy
            for (int j = 0; j < gp.getProjectile()[1].length; j++) {
                if (gp.getProjectile()[gp.getCurrentMap()][j] == null) {
                    gp.getProjectile()[gp.getCurrentMap()][j] = getProjectile();
                    break;
                }
            }

            setShotAvailableCounter(0);
        }
    }

    public void checkStartChasing(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                setOnPath(true);
            }
        }
    }

    public void checkStopChasing(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                setOnPath(false);
            }
        }
    }

    public void getRandomDirection(int interval) {
        setActionLockCounter(getActionLockCounter() + 1);

        if (getActionLockCounter() > interval) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // Pick a number between 1 and 100

            if (i <= 25) {
                setDirection("up");
            }
            if (i > 25 && i <= 50) {
                setDirection("down");
            }
            if (i > 50 && i <= 75) {
                setDirection("left");
            }
            if (i > 75 && i <= 100) {
                setDirection("right");
            }

            setActionLockCounter(0);
        }
    }

    public void moveToPlayer(int interval) {

        setActionLockCounter(getActionLockCounter() + 1);

        if (getActionLockCounter() > interval) {
            if (getXdistance(gp.getPlayer()) > getYdistance(gp.getPlayer())) {
                if (gp.getPlayer().getCenterX() < getCenterX()) { // Player is on left side
                    setDirection("left");
                } else { // Player is on right side
                    setDirection("right");
                }
            } else if (getXdistance(gp.getPlayer()) > getYdistance(gp.getPlayer())) {
                if (gp.getPlayer().getCenterY() < getCenterY()) { // Player is on top side
                    setDirection("up");
                } else { // Player is on down side
                    setDirection("down");
                }
            }
            setActionLockCounter(0);
        }
    }

    public String getOppositeDirection(String direction) {
        String oppositeDirection = "";

        switch (direction) {
            case "up" ->
                oppositeDirection = "down";
            case "down" ->
                oppositeDirection = "up";
            case "left" ->
                oppositeDirection = "right";
            case "right" ->
                oppositeDirection = "left";
        }

        return oppositeDirection;
    }

    public void attacking() {
        setSpriteCounter(getSpriteCounter() + 1);

        // Show attack image 1 during the first 5 frames
        if (getSpriteCounter() <= getMotion1_duration()) {
            setSpriteNum(1);
        }
        // Then shows attack image 2, during the next 20 frames
        if (getSpriteCounter() > getMotion1_duration() && getSpriteCounter() <= getMotion2_duration()) {
            setSpriteNum(2);

            // Save the current worldX, worldY, solidArea
            int currentWorldX = getWorldX();
            int currentWorldY = getWorldY();
            int solidAreaWidth = getSolidArea().width;
            int solidAreaHeight = getSolidArea().height;

            // Adjust player's worldX, worldY for the attack Area
            switch (getDirection()) {
                case "up" ->
                    setWorldY(getWorldY() - getAttackArea().height);
                case "down" ->
                    setWorldY(getWorldY() + getAttackArea().height);
                case "left" ->
                    setWorldX(getWorldX() - getAttackArea().width);
                case "right" ->
                    setWorldX(getWorldX() + getAttackArea().width);
            }

            // attackArea becomes solidArea
            getSolidArea().width = getAttackArea().width;
            getSolidArea().height = getAttackArea().height;

            if (getType() == getType_monster()) {
                if (gp.getcChecker().checkPlayer(this) == true) {
                    damagePlayer(getAttack());
                }
            } else { // Player
                // Check monster collision with updated worldX, worldY and solidArea
                int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
                gp.getPlayer().damageMonster(monsterIndex, this, gp.getPlayer().getPlayerAttack(),
                        getCurrentWeapon().getKnockBackPower());

                // Check collision with interactive tiles
                int iTileIndex = gp.getcChecker().checkEntity(this, gp.getiTile());
                gp.getPlayer().damageInteractiveTile(iTileIndex);

                // Check if weapon collides with projectile
                int projectileIndex = gp.getcChecker().checkEntity(this, gp.getProjectile());
                gp.getPlayer().damageProjectile(projectileIndex);
            }

            // After checking collision, restore original data
            setWorldX(currentWorldX);
            setWorldY(currentWorldY);
            getSolidArea().width = solidAreaWidth;
            getSolidArea().height = solidAreaHeight;
        }

        if (getSpriteCounter() > getMotion2_duration()) { // When counter hits 25 frames, we reset the counter and
            // finish attacking animation
            setSpriteNum(1);
            setSpriteCounter(0);
            setAttacking(false);
        }
    }

    public void damagePlayer(int attack) {

        if (gp.getPlayer().isInvincible() == false) {

            int damage = attack - gp.getPlayer().getDefense();

            // Get an opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(getDirection());

            if (gp.getPlayer().isGuarding() == true && gp.getPlayer().getDirection().equals(canGuardDirection)) {

                // Parry
                if (gp.getPlayer().getGuardCounter() < 10) {
                    damage = 0;
                    gp.playSFX(16);
                    setKnockBack(this, gp.getPlayer(), getKnockBackPower());
                    setOffBalance(true);
                    setSpriteCounter(getSpriteCounter() - 60);
                } else {
                    // Normal guard
                    damage /= 3;
                    gp.playSFX(15);
                }
            } else { // Not guarding
                gp.playSFX(6);
                if (damage < 1) {
                    damage = 1;
                }
            }

            if (damage != 0) {
                gp.getPlayer().setTransparent(true);
                setKnockBack(gp.getPlayer(), this, getKnockBackPower());
            }

            gp.getPlayer().setLife(gp.getPlayer().getLife() - damage);
            gp.getPlayer().setInvincible(true);
        }
    }

    public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {
        this.setAttacker(attacker);
        target.setKnockBackDirection(attacker.getDirection());
        target.setSpeed(target.getSpeed() + knockBackPower);
        target.setKnockBack(true);
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        if (getWorldX() + gp.getTileSize() * 5 > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX()
                && getWorldX() - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX()
                && getWorldY() + gp.getTileSize() * 5 > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY()
                && getWorldY() - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) { // Does not
            // render
            // tiles if
            // they are
            // off the
            // screen
            // size and
            // not
            // visible

            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (getDirection()) {
                case "up" -> {
                    if (isAttacking() == false) {
                        if (getSpriteNum() == 1) {
                            image = up1;
                        }
                        if (getSpriteNum() == 2) {
                            image = up2;
                        }
                    }
                    if (isAttacking() == true) {
                        tempScreenY = screenY - up1.getHeight();
                        if (getSpriteNum() == 1) {
                            image = attackUp1;
                        }
                        if (getSpriteNum() == 2) {
                            image = attackUp2;
                        }
                    }
                }
                case "down" -> {
                    if (isAttacking() == false) {
                        if (getSpriteNum() == 1) {
                            image = down1;
                        }
                        if (getSpriteNum() == 2) {
                            image = down2;
                        }
                    }
                    if (isAttacking() == true) {
                        if (getSpriteNum() == 1) {
                            image = attackDown1;
                        }
                        if (getSpriteNum() == 2) {
                            image = attackDown2;
                        }
                    }
                }
                case "left" -> {
                    if (isAttacking() == false) {
                        if (getSpriteNum() == 1) {
                            image = left1;
                        }
                        if (getSpriteNum() == 2) {
                            image = left2;
                        }
                    }
                    if (isAttacking() == true) {
                        tempScreenX = screenX - left1.getWidth();
                        if (getSpriteNum() == 1) {
                            image = attackLeft1;
                        }
                        if (getSpriteNum() == 2) {
                            image = attackLeft2;
                        }
                    }
                }
                case "right" -> {
                    if (isAttacking() == false) {
                        if (getSpriteNum() == 1) {
                            image = right1;
                        }
                        if (getSpriteNum() == 2) {
                            image = right2;
                        }
                    }
                    if (isAttacking() == true) {
                        if (getSpriteNum() == 1) {
                            image = attackRight1;
                        }
                        if (getSpriteNum() == 2) {
                            image = attackRight2;
                        }
                    }
                }
            }

            // Monster HP bar
            if (getType() == 2 && isHpBarOn() == true) {
                double oneScale = (double) gp.getTileSize() / getMaxLife(); // Divide bar length by the monster's max
                // life
                double hpBarValue = oneScale * getLife();

                g2.setColor(new Color(35, 35, 35)); // HP bar border color
                g2.fillRect(screenX - 1, screenY - 16, gp.getTileSize() + 2, 12);

                g2.setColor(new Color(255, 0, 30)); // HP bar inside color
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                setHpBarCounter(getHpBarCounter() + 1);

                if (getHpBarCounter() > 600) {
                    setHpBarCounter(0);
                    setHpBarOn(false);
                }
            }

            if (isInvincible() == true && !(this instanceof InteractiveTile)) {
                setHpBarOn(true);
                setHpBarCounter(0);
                changeAlpha(g2, 0.4f);
            }

            if (isDying() == true) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY, null);

            // Reset alpha
            changeAlpha(g2, 1f);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        setDyingCounter(getDyingCounter() + 1);

        // Interval
        int i = 5;

        // Switch monster alpha every 5 frames to give a blinking effect
        // To change death effect, just replace changeAlpha() with a method to replace
        // sprite
        if (getDyingCounter() <= i) {
            changeAlpha(g2, 0f);
        }
        if (getDyingCounter() > i && getDyingCounter() <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (getDyingCounter() > i * 2 && getDyingCounter() <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (getDyingCounter() > i * 3 && getDyingCounter() <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (getDyingCounter() > i * 4 && getDyingCounter() <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (getDyingCounter() > i * 5 && getDyingCounter() <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (getDyingCounter() > i * 6 && getDyingCounter() <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (getDyingCounter() > i * 7 && getDyingCounter() <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (getDyingCounter() > i * 8) {
            setAlive(false);
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (getWorldX() + getSolidArea().x) / gp.getTileSize();
        int startRow = (getWorldY() + getSolidArea().y) / gp.getTileSize();

        gp.getpFinder().setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.getpFinder().search() == true) {
            // Next worldX and worldY
            int nextX = gp.getpFinder().pathList.get(0).getCol() * gp.getTileSize();
            int nextY = gp.getpFinder().pathList.get(0).getRow() * gp.getTileSize();

            // Entity's solidArea position
            int entityLeftX = getWorldX() + getSolidArea().x;
            int entityRightX = getWorldY() + getSolidArea().x + getSolidArea().width;
            int entityTopY = getWorldY() + getSolidArea().y;
            int entityBottomY = getWorldY() + getSolidArea().y + getSolidArea().height;

            if (entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gp.getTileSize()) { // Entity can
                // go UP
                setDirection("up");
            }
            if (entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX + gp.getTileSize()) { // Entity can
                // go DOWN
                setDirection("down");
            }
            if (entityTopY >= nextY && entityBottomY < nextY + gp.getTileSize()) { // Entity can go LEFT or RIGHT
                if (entityLeftX > nextX) {
                    setDirection("left");
                }
                if (entityLeftX < nextX) {
                    setDirection("right");
                }
            } else if (entityTopY > nextY && entityLeftX > nextX) { // Entity can go UP or LEFT (depending on collision)
                setDirection("up");
                checkCollision();
                if (isCollisionOn() == true) {
                    setDirection("left");
                }
            } else if (entityTopY > nextY && entityLeftX < nextX) { // Entity can go UP or RIGHT (depending on
                // collision)
                setDirection("up");
                checkCollision();
                if (isCollisionOn() == true) {
                    setDirection("right");
                }
            } else if (entityTopY < nextY && entityLeftX > nextX) { // Entity can go DOWN or LEFT (depending on
                // collision)
                setDirection("down");
                checkCollision();
                if (isCollisionOn() == true) {
                    setDirection("left");
                }
            } else if (entityTopY < nextY && entityLeftX < nextX) { // Entity can go DOWN or RIGHT (depending on
                // collision)
                setDirection("down");
                checkCollision();
                if (isCollisionOn() == true) {
                    setDirection("right");
                }
            }

            // If reaches the goal, stop the search
            int nextCol = gp.getpFinder().pathList.get(0).getCol();
            int nextRow = gp.getpFinder().pathList.get(0).getRow();
            if (nextCol == goalCol && nextRow == goalRow) {
                setOnPath(false);
            }
        }
    }

    public int getDetected(Entity user, Entity target[][], String targetName) {
        int index = 999;

        // Check surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.getDirection()) {
            case "up" ->
                nextWorldY = user.getTopY() - gp.getPlayer().getSpeed();
            case "down" ->
                nextWorldY = user.getBottomY() + gp.getPlayer().getSpeed();
            case "left" ->
                nextWorldX = user.getLeftX() - gp.getPlayer().getSpeed();
            case "right" ->
                nextWorldX = user.getRightX() + gp.getPlayer().getSpeed();
        }

        int col = nextWorldX / gp.getTileSize();
        int row = nextWorldY / gp.getTileSize();

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.getCurrentMap()][i] != null) {
                if (target[gp.getCurrentMap()][i].getCol() == col && target[gp.getCurrentMap()][i].getRow() == row
                        && target[gp.getCurrentMap()][i].getName().equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
