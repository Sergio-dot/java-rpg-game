package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {

    private KeyHandler keyH;
    private int screenX;
    private int screenY;
    private boolean attackCanceled = false;
    private boolean lightUpdated = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        screenY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);

        // Override default entity solidArea
        setSolidArea(new Rectangle());
        getSolidArea().x = 8;
        getSolidArea().y = 16;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
        getSolidArea().width = 30;
        getSolidArea().height = 30;

        // Override default entity attackArea
//	attackArea.width = 36;
//	attackArea.height = 36;
        setDefaultValues();
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public void setKeyH(KeyHandler keyH) {
        this.keyH = keyH;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public boolean isAttackCanceled() {
        return attackCanceled;
    }

    public void setAttackCanceled(boolean attackCanceled) {
        this.attackCanceled = attackCanceled;
    }

    public boolean isLightUpdated() {
        return lightUpdated;
    }

    public void setLightUpdated(boolean lightUpdated) {
        this.lightUpdated = lightUpdated;
    }

    public void setDefaultValues() {
        // Starting position - worldX = Column, worldY = Row
        setWorldX(gp.getTileSize() * 23); // World Map X
        setWorldY(gp.getTileSize() * 21); // World Map Y
//		setWorldX(gp.tileSize * 12); // Merchant Hut X
//		setWorldY(gp.tileSize * 11); // Merchant Hut Y
//		setWorldX(gp.tileSize * 9); // Dungeon Map F1 X
//		setWorldY(gp.tileSize * 40); // Dungeon Map F1 Y
//		setWorldX(gp.tileSize * 25); // Dungeon Map F2 X
//		setWorldY(gp.tileSize * 9); // Dungeon Map F2 Y
        setDefaultSpeed(4);
        setSpeed(getDefaultSpeed());
        setDirection("down");

        // Player status
        setLevel(1);
        setMaxLife(6);
        setLife(getMaxLife());
        setMaxMana(4);
        setMana(getMaxMana());
        setAmmo(10); // Used if switch fireball with rock or implement arrows
        setStrength(1); // Strength is the multiplier used to increase effective attack stat
        setDexterity(1); // Dexterity is the multiplier used to increase effective defense stat
        setExp(0);
        setNextLevelExp(5);
        setCoin(100);
        setCurrentWeapon(new OBJ_Sword_Normal(gp));
        setCurrentShield(new OBJ_Shield_Wood(gp));
        setCurrentLight(null);
        setProjectile(new OBJ_Fireball(gp));
        setAttack(getPlayerAttack()); // Total attack value, affected by strength and current weapon
        setDefense(getPlayerDefense()); // Total defense value, affected by dexterity and current shield

        getSprite();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
    }

    public void setDefaultPosition() {
        gp.setCurrentMap(0);
        setWorldX(gp.getTileSize() * 23); // Starting position - X axis
        setWorldY(gp.getTileSize() * 21); // Starting position - Y axis
        setDirection("down");
    }

    public void setDialogue() {
        getDialogues()[0][0] = "You are level " + getLevel() + " now!\n"
                + "You feel stronger!";
    }

    public void restoreStatus() {
        setLife(getMaxLife());
        setMana(getMaxMana());
        setSpeed(getDefaultSpeed());
        setInvincible(false);
        setTransparent(false);
        setAttacking(false);
        setGuarding(false);
        setKnockBack(false);
        setLightUpdated(true);
    }

    public void setItems() {
        getInventory().clear();
        getInventory().add(getCurrentWeapon());
        getInventory().add(getCurrentShield());
    }

    public int getPlayerAttack() {
        setAttackArea(getCurrentWeapon().getAttackArea());
        setMotion1_duration(getCurrentWeapon().getMotion1_duration());
        setMotion2_duration(getCurrentWeapon().getMotion2_duration());

        int tempAttack = getStrength() * getCurrentWeapon().getAttackValue();

        return tempAttack;
    }

    public int getPlayerDefense() {
        int tempDefense = getDexterity() * getCurrentShield().getDefenseValue();
        return tempDefense;
    }

    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < getInventory().size(); i++) {
            if (getInventory().get(i) == getCurrentWeapon()) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for (int i = 0; i < getInventory().size(); i++) {
            if (getInventory().get(i) == getCurrentShield()) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }

    public void getSprite() {
        up1 = setup("/player/boy_up_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/player/boy_up_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/player/boy_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/player/boy_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/player/boy_left_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/player/boy_left_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/player/boy_right_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/player/boy_right_2", gp.getTileSize(), gp.getTileSize());
    }

    public void getSleepingImage(BufferedImage image) {
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }

    public void getAttackImage() {

        // Sword sprites
        if (getCurrentWeapon().getType() == getType_sword()) {
            attackUp1 = setup("/player/boy_attack_up_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackUp2 = setup("/player/boy_attack_up_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", gp.getTileSize() * 2, gp.getTileSize());
            attackLeft2 = setup("/player/boy_attack_left_2", gp.getTileSize() * 2, gp.getTileSize());
            attackRight1 = setup("/player/boy_attack_right_1", gp.getTileSize() * 2, gp.getTileSize());
            attackRight2 = setup("/player/boy_attack_right_2", gp.getTileSize() * 2, gp.getTileSize());
        }

        // Axe sprites
        if (getCurrentWeapon().getType() == getType_axe()) {
            attackUp1 = setup("/player/boy_axe_up_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.getTileSize() * 2, gp.getTileSize());
            attackLeft2 = setup("/player/boy_axe_left_2", gp.getTileSize() * 2, gp.getTileSize());
            attackRight1 = setup("/player/boy_axe_right_1", gp.getTileSize() * 2, gp.getTileSize());
            attackRight2 = setup("/player/boy_axe_right_2", gp.getTileSize() * 2, gp.getTileSize());
        }

        // Pickaxe sprites
        if (getCurrentWeapon().getType() == getType_pickaxe()) {
            attackUp1 = setup("/player/boy_pick_up_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackUp2 = setup("/player/boy_pick_up_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown1 = setup("/player/boy_pick_down_1", gp.getTileSize(), gp.getTileSize() * 2);
            attackDown2 = setup("/player/boy_pick_down_2", gp.getTileSize(), gp.getTileSize() * 2);
            attackLeft1 = setup("/player/boy_pick_left_1", gp.getTileSize() * 2, gp.getTileSize());
            attackLeft2 = setup("/player/boy_pick_left_2", gp.getTileSize() * 2, gp.getTileSize());
            attackRight1 = setup("/player/boy_pick_right_1", gp.getTileSize() * 2, gp.getTileSize());
            attackRight2 = setup("/player/boy_pick_right_2", gp.getTileSize() * 2, gp.getTileSize());
        }
    }

    public void getGuardImage() {

        guardUp = setup("/player/boy_guard_up", gp.getTileSize(), gp.getTileSize());
        guardDown = setup("/player/boy_guard_down", gp.getTileSize(), gp.getTileSize());
        guardLeft = setup("/player/boy_guard_left", gp.getTileSize(), gp.getTileSize());
        guardRight = setup("/player/boy_guard_right", gp.getTileSize(), gp.getTileSize());
    }

    @Override
    public void update() {

        if (isKnockBack() == true) {

            setCollisionOn(false);
            gp.getcChecker().checkTile(this);
            gp.getcChecker().checkObject(this, true);
            gp.getcChecker().checkEntity(this, gp.getNpc());
            gp.getcChecker().checkEntity(this, gp.getMonster());
            gp.getcChecker().checkEntity(this, gp.getiTile());

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
        } else if (getKeyH().isSpacePressed() == true) {
            setGuarding(true);
            setGuardCounter(getGuardCounter() + 1);
        } else if (getKeyH().isUpPressed() == true || getKeyH().isDownPressed() == true
                || getKeyH().isLeftPressed() == true || getKeyH().isRightPressed() == true || getKeyH().isEnterPressed() == true) {

            if (getKeyH().isUpPressed() == true) {
                setDirection("up");
            } else if (getKeyH().isDownPressed() == true) {
                setDirection("down");
            } else if (getKeyH().isLeftPressed() == true) {
                setDirection("left");
            } else if (getKeyH().isRightPressed() == true) {
                setDirection("right");
            }

            // Check tile collision
            setCollisionOn(false);
            gp.getcChecker().checkTile(this);

            // Check object collision
            int objIndex = gp.getcChecker().checkObject(this, true);
            pickUpObject(objIndex);

            // Check NPC collision
            int npcIndex = gp.getcChecker().checkEntity(this, gp.getNpc());
            interactNPC(npcIndex);

            // Check monster collision
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
            contactMonster(monsterIndex);

            // Check interactive tiles collision
            gp.getcChecker().checkEntity(this, gp.getiTile());

            // Check event
            gp.geteHandler().checkEvent();

            // If collision is false, player can move
            if (isCollisionOn() == false && getKeyH().isEnterPressed() == false) {
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

            if (getKeyH().isEnterPressed() == true && isAttackCanceled() == false) {
                gp.playSFX(7);
                setAttacking(true);
                setSpriteCounter(0);
            }

            setAttackCanceled(false);
            gp.getKeyH().setEnterPressed(false);
            setGuarding(false);
            setGuardCounter(0);

            setSpriteCounter(getSpriteCounter() + 1);
            if (getSpriteCounter() > 15) {
                if (getSpriteNum() == 1) {
                    setSpriteNum(2);
                } else if (getSpriteNum() == 2) {
                    setSpriteNum(1);
                }
                setSpriteCounter(0);
            }
            setGuarding(false);
            setGuardCounter(0);
        }

        // Handle projectiles
        if (gp.getKeyH().isShotKeyPressed() == true && getProjectile().isAlive() == false
                && getShotAvailableCounter() == 30 && getProjectile().haveResource(this) == true) {
            // Set default coordinates, direction and user
            getProjectile().set(getWorldX(), getWorldY(), getDirection(), true, this);

            // Subtract the cost (Mana)
            getProjectile().subtractResource(this);

            // Check empty slot
            for (int i = 0; i < gp.getProjectile()[1].length; i++) {
                if (gp.getProjectile()[gp.getCurrentMap()][i] == null) {
                    gp.getProjectile()[gp.getCurrentMap()][i] = getProjectile();
                    break;
                }
            }

            setShotAvailableCounter(0);

            gp.playSFX(10);
        }

        // IMPORTANT! This needs to be outside of key if() statement
        if (isInvincible() == true) {
            setInvincibleCounter(getInvincibleCounter() + 1);
            if (getInvincibleCounter() > 60) {
                setInvincible(false);
                setTransparent(false);
                setInvincibleCounter(0);
            }
        }

        // Prevent to shoot two fireballs when player is next to a monster
        if (getShotAvailableCounter() < 30) {
            setShotAvailableCounter(getShotAvailableCounter() + 1);
        }

        // Prevent recovering life over maxLife value
        if (getLife() > getMaxLife()) {
            setLife(getMaxLife());
        }

        // Prevent recovering mana over maxMana value
        if (getMana() > getMaxMana()) {
            setMana(getMaxMana());
        }

        if (getKeyH().isGodMode() == false) {
            // Handle player death
            if (getLife() <= 0) {
                gp.setGameState(gp.getGameOverState());
                gp.getUi().commandNum = -1;
                gp.stopBGM();
                gp.playSFX(12);
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

            // Pick up only items
            if (gp.getObj()[gp.getCurrentMap()][i].getType() == getType_pickUpOnly()) {
                gp.getObj()[gp.getCurrentMap()][i].use(this);
                gp.getObj()[gp.getCurrentMap()][i] = null;
            } // Obstacle
            else if (gp.getObj()[gp.getCurrentMap()][i].getType() == getType_obstacle()) {
                if (getKeyH().isEnterPressed() == true) {
                    setAttackCanceled(true);
                    gp.getObj()[gp.getCurrentMap()][i].interact();
                }
            } // Inventory items
            else {
                String text;

                if (canObtainItem(gp.getObj()[gp.getCurrentMap()][i]) == true) {
                    gp.playSFX(1);
                    text = "Got a " + gp.getObj()[gp.getCurrentMap()][i].getName() + "!";
                } else {
                    text = "You cannot carry any more!";
                }
                gp.getUi().addMessage(text);
                gp.getObj()[gp.getCurrentMap()][i] = null;
            }
        }
    }

    public void interactNPC(int i) {

        if (i != 999) {
            if (gp.getKeyH().isEnterPressed() == true) {
                setAttackCanceled(true);
                gp.getNpc()[gp.getCurrentMap()][i].speak();
            }
            gp.getNpc()[gp.getCurrentMap()][i].move(getDirection());
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (isInvincible() == false && gp.getMonster()[gp.getCurrentMap()][i].isDying() == false) {
                gp.playSFX(6);

                int damage = gp.getMonster()[gp.getCurrentMap()][i].getAttack() - getDefense();

                if (damage < 1) {
                    damage = 1;
                }

                setLife(getLife() - damage);
                setInvincible(true);
                setTransparent(true);
            }
        }
    }

    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
        if (i != 999) {
            if (gp.getMonster()[gp.getCurrentMap()][i].isInvincible() == false) {
                gp.playSFX(5);

                if (knockBackPower > 0) {
                    setKnockBack(gp.getMonster()[gp.getCurrentMap()][i], attacker, knockBackPower);
                }

                if (gp.getMonster()[gp.getCurrentMap()][i].isOffBalance() == true) {
                    attack *= 5;
                }

                int damage = attack - gp.getMonster()[gp.getCurrentMap()][i].getDefense();

                if (damage < 0) {
                    damage = 0;
                }

                gp.getMonster()[gp.getCurrentMap()][i].setLife(gp.getMonster()[gp.getCurrentMap()][i].getLife() - damage);
                gp.getUi().addMessage(damage + " damage!");

                gp.getMonster()[gp.getCurrentMap()][i].setInvincible(true);
                gp.getMonster()[gp.getCurrentMap()][i].damageReaction();

                if (gp.getMonster()[gp.getCurrentMap()][i].getLife() <= 0) {
                    gp.getMonster()[gp.getCurrentMap()][i].setDying(true);
                    // gp.ui.addMessage("Killed the " + gp.monster[i].name + "!");
                    gp.getUi().addMessage("Exp + " + gp.getMonster()[gp.getCurrentMap()][i].getExp());
                    setExp(getExp() + gp.getMonster()[gp.getCurrentMap()][i].getExp());
                    checkLevelUp();
                }
            }
        }
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.getiTile()[gp.getCurrentMap()][i].destructible == true
                && gp.getiTile()[gp.getCurrentMap()][i].isCorrectItem(this) == true && gp.getiTile()[gp.getCurrentMap()][i].isInvincible() == false) {
            gp.getiTile()[gp.getCurrentMap()][i].playSFX();
            gp.getiTile()[gp.getCurrentMap()][i].setLife(gp.getiTile()[gp.getCurrentMap()][i].getLife() - 1);
            gp.getiTile()[gp.getCurrentMap()][i].setInvincible(true);

            // Generate particle
            generateParticle(gp.getiTile()[gp.getCurrentMap()][i], gp.getiTile()[gp.getCurrentMap()][i]);

            if (gp.getiTile()[gp.getCurrentMap()][i].getLife() == 0) {
                gp.getiTile()[gp.getCurrentMap()][i] = gp.getiTile()[gp.getCurrentMap()][i].getDestroyedForm();
            }
        }
    }

    public void damageProjectile(int i) {
        if (i != 999) {
            Entity projectile = gp.getProjectile()[gp.getCurrentMap()][i];
            projectile.setAlive(false);
            generateParticle(projectile, projectile);
        }
    }

    public void checkLevelUp() {
        if (getExp() >= getNextLevelExp()) {
            setLevel(getLevel() + 1);
            setNextLevelExp(getNextLevelExp() * 2);
            setMaxLife(getMaxLife() + 2);
            setStrength(getStrength() + 1);
            setDexterity(getDexterity() + 1);
            setAttack(getPlayerAttack());
            setDefense(getPlayerDefense());

            gp.playSFX(8);

            setDialogue();
            startDialogue(this, 0);
        }
    }

    public void selectItem() {
        int itemIndex = gp.getUi().getItemIndexOnSlot(gp.getUi().playerSlotCol, gp.getUi().playerSlotRow);

        if (itemIndex < getInventory().size()) {
            Entity selectedItem = getInventory().get(itemIndex);

            if (selectedItem.getType() == getType_sword() || selectedItem.getType() == getType_axe() || selectedItem.getType() == getType_pickaxe()) {
                setCurrentWeapon(selectedItem);
                setAttack(getPlayerAttack()); // Update attack value
                getAttackImage(); // Update attack image
            }

            if (selectedItem.getType() == getType_shield()) {
                setCurrentShield(selectedItem);
                setDefense(getPlayerDefense()); // Update defense value
            }

            if (selectedItem.getType() == getType_light()) {
                if (getCurrentLight() == selectedItem) {
                    setCurrentLight(null);
                } else {
                    setCurrentLight(selectedItem);
                }
                setLightUpdated(true);
            }

            if (selectedItem.getType() == getType_consumable()) {
                if (selectedItem.use(this) == true) {
                    if (selectedItem.getAmount() > 1) {
                        selectedItem.setAmount(selectedItem.getAmount() - 1);
                    } else {
                        getInventory().remove(itemIndex);
                    }
                }
            }
        }
    }

    public int searchItemInInventory(String itemName) {
        int itemIndex = 999;

        for (int i = 0; i < getInventory().size(); i++) {
            if (getInventory().get(i).getName().equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item) {

        boolean canObtain = false;

        Entity newItem = gp.geteGenerator().getObject(item.getName());

        // Check if item is stackable
        if (newItem.isStackable() == true) {
            int index = searchItemInInventory(newItem.getName());

            if (index != 999) {
                getInventory().get(index).setAmount(getInventory().get(index).getAmount() + 1);
                canObtain = true;
            } else { // New item, need to check vacancy
                if (getInventory().size() != getMaxInventorySize()) {
                    getInventory().add(newItem);
                    canObtain = true;
                }
            }
        } else { // If not stackable
            // Check vacancy
            if (getInventory().size() != getMaxInventorySize()) {
                getInventory().add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = getScreenX();
        int tempScreenY = getScreenY();

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
                    tempScreenY = getScreenY() - gp.getTileSize();
                    if (getSpriteNum() == 1) {
                        image = attackUp1;
                    }
                    if (getSpriteNum() == 2) {
                        image = attackUp2;
                    }
                }

                if (isGuarding() == true) {
                    image = guardUp;
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

                if (isGuarding() == true) {
                    image = guardDown;
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
                    tempScreenX = getScreenX() - gp.getTileSize();
                    if (getSpriteNum() == 1) {
                        image = attackLeft1;
                    }
                    if (getSpriteNum() == 2) {
                        image = attackLeft2;
                    }
                }

                if (isGuarding() == true) {
                    image = guardLeft;
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

                if (isGuarding() == true) {
                    image = guardRight;
                }
            }
        }

        if (isTransparent() == true) {
            changeAlpha(g2, 0.4f);
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset alpha
        changeAlpha(g2, 1f);

        // Debug
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
}
