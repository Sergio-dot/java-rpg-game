package entity;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

abstract class AbstractEntity {

    // Entity attributes
    private Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    private int solidAreaDefaultX;
    private int solidAreaDefaultY;
    private boolean collision = false;
    private String dialogues[][] = new String[20][20];
    private Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    private Entity attacker;
    private Entity linkedEntity;

    // NEED FIX - PRIVATE GIVE REFERENCE ERRORS
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage image, image2, image3;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
            attackRight2, guardUp, guardDown, guardLeft, guardRight;

    // Character attributes
    private String name;
    private int defaultSpeed;
    private int speed;
    private int maxLife;
    private int life;
    private int maxMana;
    private int mana;
    private int ammo;
    private int level;
    private int strength;
    private int dexterity;
    private int attack;
    private int defense;
    private int exp;
    private int nextLevelExp;
    private int coin;
    private Entity currentWeapon;
    private Entity currentShield;
    private Entity currentLight;
    private int motion1_duration;
    private int motion2_duration;
    private Projectile projectile;

    // State
    private int worldX;
    private int worldY;
    private String direction = "down";
    private int dialogueSet = 0;
    private int dialogueIndex = 0;
    private boolean collisionOn = false;
    private boolean alive = true;
    private boolean dying = false;
    private String knockBackDirection;
    private boolean knockBack = false;
    private boolean opened = false;
    private int spriteNum = 1;
    private boolean invincible = false;
    private boolean attacking = false;
    private boolean hpBarOn = false;
    private boolean onPath = false;
    private boolean guarding = false;
    private boolean transparent = false;
    private boolean offBalance = false;
    private boolean inRage = false;
    public Entity loot; // NEED FIX - PRIVATE CAUSES STACKOVERFLOW

    // Counters
    private int actionLockCounter = 0;
    private int spriteCounter = 0;
    private int invincibleCounter;
    private int shotAvailableCounter = 0;
    private int dyingCounter = 0;
    private int hpBarCounter = 0;
    private int knockBackCounter = 0;
    private int guardCounter = 0;
    private int offBalanceCounter = 0;

    // Item attributes
    private final int maxInventorySize = 20;
    private int value;
    private int attackValue;
    private int defenseValue;
    private int useCost;
    private int knockBackPower = 0;
    private boolean stackable = false;
    private String description = "";
    private int price;
    private ArrayList<Entity> inventory = new ArrayList<>();
    private int amount = 1;
    private int lightRadius;

    // Type
    private int type;
    private final int type_player = 0;
    private final int type_npc = 1;
    private final int type_monster = 2;
    private final int type_sword = 3;
    private final int type_axe = 4;
    private final int type_shield = 5;
    private final int type_consumable = 6;
    private final int type_pickUpOnly = 7;
    private final int type_obstacle = 8;
    private final int type_light = 9;
    private final int type_pickaxe = 10;

    public AbstractEntity() { } // Empty constructor

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public Entity getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Entity currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public Entity getCurrentShield() {
        return currentShield;
    }

    public void setCurrentShield(Entity currentShield) {
        this.currentShield = currentShield;
    }

    public Entity getCurrentLight() {
        return currentLight;
    }

    public void setCurrentLight(Entity currentLight) {
        this.currentLight = currentLight;
    }

    public int getMotion1_duration() {
        return motion1_duration;
    }

    public void setMotion1_duration(int motion1_duration) {
        this.motion1_duration = motion1_duration;
    }

    public int getMotion2_duration() {
        return motion2_duration;
    }

    public void setMotion2_duration(int motion2_duration) {
        this.motion2_duration = motion2_duration;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getDialogueSet() {
        return dialogueSet;
    }

    public void setDialogueSet(int dialogueSet) {
        this.dialogueSet = dialogueSet;
    }

    public int getDialogueIndex() {
        return dialogueIndex;
    }

    public void setDialogueIndex(int dialogueIndex) {
        this.dialogueIndex = dialogueIndex;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isDying() {
        return dying;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public String getKnockBackDirection() {
        return knockBackDirection;
    }

    public void setKnockBackDirection(String knockBackDirection) {
        this.knockBackDirection = knockBackDirection;
    }

    public boolean isKnockBack() {
        return knockBack;
    }

    public void setKnockBack(boolean knockBack) {
        this.knockBack = knockBack;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isHpBarOn() {
        return hpBarOn;
    }

    public void setHpBarOn(boolean hpBarOn) {
        this.hpBarOn = hpBarOn;
    }

    public boolean isOnPath() {
        return onPath;
    }

    public void setOnPath(boolean onPath) {
        this.onPath = onPath;
    }

    public boolean isGuarding() {
        return guarding;
    }

    public void setGuarding(boolean guarding) {
        this.guarding = guarding;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public boolean isOffBalance() {
        return offBalance;
    }

    public void setOffBalance(boolean offBalance) {
        this.offBalance = offBalance;
    }

    public boolean isInRage() {
        return inRage;
    }

    public void setInRage(boolean inRage) {
        this.inRage = inRage;
    }

    public int getActionLockCounter() {
        return actionLockCounter;
    }

    public void setActionLockCounter(int actionLockCounter) {
        this.actionLockCounter = actionLockCounter;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getInvincibleCounter() {
        return invincibleCounter;
    }

    public void setInvincibleCounter(int invincibleCounter) {
        this.invincibleCounter = invincibleCounter;
    }

    public int getShotAvailableCounter() {
        return shotAvailableCounter;
    }

    public void setShotAvailableCounter(int shotAvailableCounter) {
        this.shotAvailableCounter = shotAvailableCounter;
    }

    public int getDyingCounter() {
        return dyingCounter;
    }

    public void setDyingCounter(int dyingCounter) {
        this.dyingCounter = dyingCounter;
    }

    public int getHpBarCounter() {
        return hpBarCounter;
    }

    public void setHpBarCounter(int hpBarCounter) {
        this.hpBarCounter = hpBarCounter;
    }

    public int getKnockBackCounter() {
        return knockBackCounter;
    }

    public void setKnockBackCounter(int knockBackCounter) {
        this.knockBackCounter = knockBackCounter;
    }

    public int getGuardCounter() {
        return guardCounter;
    }

    public void setGuardCounter(int guardCounter) {
        this.guardCounter = guardCounter;
    }

    public int getOffBalanceCounter() {
        return offBalanceCounter;
    }

    public void setOffBalanceCounter(int offBalanceCounter) {
        this.offBalanceCounter = offBalanceCounter;
    }

    public int getMaxInventorySize() {
        return maxInventorySize;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    public int getUseCost() {
        return useCost;
    }

    public void setUseCost(int useCost) {
        this.useCost = useCost;
    }

    public int getKnockBackPower() {
        return knockBackPower;
    }

    public void setKnockBackPower(int knockBackPower) {
        this.knockBackPower = knockBackPower;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<Entity> getInventory() {
        return inventory;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getLightRadius() {
        return lightRadius;
    }

    public void setLightRadius(int lightRadius) {
        this.lightRadius = lightRadius;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType_monster() {
        return type_monster;
    }

    public int getType_sword() {
        return type_sword;
    }

    public int getType_axe() {
        return type_axe;
    }

    public int getType_shield() {
        return type_shield;
    }

    public int getType_consumable() {
        return type_consumable;
    }

    public int getType_pickUpOnly() {
        return type_pickUpOnly;
    }

    public int getType_obstacle() {
        return type_obstacle;
    }

    public int getType_light() {
        return type_light;
    }

    public int getType_pickaxe() {
        return type_pickaxe;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) {
        this.solidAreaDefaultX = solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) {
        this.solidAreaDefaultY = solidAreaDefaultY;
    }

    public String[][] getDialogues() {
        return dialogues;
    }

    public Rectangle getAttackArea() {
        return attackArea;
    }

    public void setAttackArea(Rectangle attackArea) {
        this.attackArea = attackArea;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setAttacker(Entity attacker) {
        this.attacker = attacker;
    }

    public Entity getLinkedEntity() {
        return linkedEntity;
    }

    public void setLinkedEntity(Entity linkedEntity) {
        this.linkedEntity = linkedEntity;
    }

    // Overridden by OBJ classes
    public void interact() {
    }

    // Overridden by consumable classes
    public boolean use(Entity entity) {
        return false;
    }

    // Overridden by MON classes
    public void checkDrop() {
    }

    // Overridden by OBJ classes
    public void setObjectLoot(Entity loot) {
    }

    // Overridden by NPC classes
    public void setAction() {
    }

    // Overridden by NPC_BigRock
    public void move(String direction) {
    }

    // Overridden by MON classes
    public void damageReaction() {
    }

    // Overridden by NPC classes
    public void speak() {
    }
    
    // Overridden by interactive tiles, spells
    public Color getParticleColor() {
        Color color = null;

        return color;
    }

    // Overridden by interactive tiles, spells
    public int getParticleSize() {
        int size = 0;

        return size;
    }

    // Overridden by interactive tiles, spells
    public int getParticleSpeed() {
        int speed = 0;

        return speed;
    }

    // Overridden by interactive tiles, spells
    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }

}
