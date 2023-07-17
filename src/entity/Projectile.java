package entity;

import main.GamePanel;

public class Projectile extends Entity {

    private Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public Entity getUser() {
        return user;
    }

    public void setUser(Entity user) {
        this.user = user;
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.setWorldX(worldX);
        this.setWorldY(worldY);
        this.setDirection(direction);
        this.setAlive(alive);
        this.setUser(user);
        this.setLife(this.getMaxLife());
    }

    @Override
    public void update() {
        // If user is the player
        if (getUser() == gp.getPlayer()) {
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
            if (monsterIndex != 999) {
                gp.getPlayer().damageMonster(monsterIndex, this, getAttack(), getKnockBackPower());
                generateParticle(getUser().getProjectile(), gp.getMonster()[gp.getCurrentMap()][monsterIndex]);
                setAlive(false);
            }
        }

        // If user is not the player (e.g. a monster)
        if (getUser() != gp.getPlayer()) {
            boolean contactPlayer = gp.getcChecker().checkPlayer(this);

            if (gp.getPlayer().isInvincible() == false && contactPlayer == true) {
                damagePlayer(getAttack());
                generateParticle(getUser().getProjectile(), getUser().getProjectile());
                setAlive(false);
            }
        }

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

        setLife(getLife() - 1);
        if (getLife() <= 0) {
            setAlive(false);
        }

        setSpriteCounter(getSpriteCounter() + 1);
        if (getSpriteCounter() > 12) {
            if (getSpriteNum() == 1) {
                setSpriteNum(2);
            } else if (getSpriteNum() == 2) {
                setSpriteNum(1);
            }
            setSpriteCounter(0);
        }
    }

    // Overridden by subclass
    public boolean haveResource(Entity user) {
        boolean haveResource = false;

        return haveResource;
    }

    // Overridden by subclass
    public void subtractResource(Entity user) {
    }

}
