package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DestructibleWall extends InteractiveTile {

    GamePanel gp;
    public static final String itName = "Destructible Wall";

    public IT_DestructibleWall(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.setWorldX(gp.getTileSize() * col);
        this.setWorldY(gp.getTileSize() * row);

        setName(itName);
        down1 = setup("/tiles_interactive/destructiblewall", gp.getTileSize(), gp.getTileSize());
        destructible = true;
        setLife(3);
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if (entity.getCurrentWeapon().getType() == getType_pickaxe()) {
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    @Override
    public void playSFX() {
        gp.playSFX(20);
    }

    @Override
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;

        return tile;
    }

    @Override
    public Color getParticleColor() {
        Color color = new Color(65, 65, 65);

        return color;
    }

    @Override
    public int getParticleSize() {
        int size = 6; // 6 pixels

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
