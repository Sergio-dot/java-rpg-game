package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Particle extends Entity {

    private Entity generator;
    private Color color;
    private int size;
    private int xd;
    private int yd;

    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.setSpeed(speed);
        this.setMaxLife(maxLife);
        this.xd = xd;
        this.yd = yd;

        setLife(maxLife);
        int offset = (gp.getTileSize() / 2) - (size / 2);
        setWorldX(generator.getWorldX() + offset);
        setWorldY(generator.getWorldY() + offset);
    }

    public Entity getGenerator() {
        return generator;
    }

    public void setGenerator(Entity generator) {
        this.generator = generator;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getXd() {
        return xd;
    }

    public void setXd(int xd) {
        this.xd = xd;
    }

    public int getYd() {
        return yd;
    }

    public void setYd(int yd) {
        this.yd = yd;
    }

    @Override
    public void update() {
        setLife(getLife() - 1);

        if (getLife() < getMaxLife() / 3) {
            setYd(getYd() + 1);
        }

        setWorldX(getWorldX() + getXd() * getSpeed());
        setWorldY(getWorldY() + getYd() * getSpeed());

        if (getLife() == 0) {
            setAlive(false);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        g2.setColor(getColor());
        // Squared particle effect
        g2.fillRect(screenX, screenY, getSize(), getSize());
        // Rounded particle effect
        // g2.fillRoundRect(screenX, screenY, size, size, 10, 10);
    }

}
