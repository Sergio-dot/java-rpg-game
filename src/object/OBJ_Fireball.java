package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {

    GamePanel gp;
    public static final String objName = "Fireball";

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setName(objName);
        setSpeed(6);
        setMaxLife(80);
        setLife(getMaxLife());
        setAttack(2);
        setKnockBackPower(0);
        setUseCost(1);
        setAlive(false);
        getSprite();
    }

    public void getSprite() {
        up1 = setup("/projectile/fireball_up_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/projectile/fireball_up_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/projectile/fireball_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/projectile/fireball_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/projectile/fireball_left_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/projectile/fireball_left_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/projectile/fireball_right_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/projectile/fireball_right_2", gp.getTileSize(), gp.getTileSize());
    }

    @Override
    public boolean haveResource(Entity user) {
        boolean haveResource = false;

        if (user.getMana() >= getUseCost()) {
            haveResource = true;
        }

        return haveResource;
    }

    @Override
    public void subtractResource(Entity user) {
        user.setMana(user.getMana() - getUseCost());
    }

    @Override
    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);

        return color;
    }

    @Override
    public int getParticleSize() {
        int size = 10; // 10 pixels

        return size;
    }

    @Override
    public int getParticleSpeed() {
        int speed = 1;

        return speed;
    }

    @Override
    public int getParticleMaxLife() {
        int maxLife = 20;

        return maxLife;
    }

}
