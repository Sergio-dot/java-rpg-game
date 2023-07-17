package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {

    GamePanel gp;
    public static final String itName = "Dry Tree";

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.setWorldX(gp.getTileSize() * col);
        this.setWorldY(gp.getTileSize() * row);

        setLife(2);
        setName(itName);
        down1 = setup("/tiles_interactive/drytree2", gp.getTileSize(), gp.getTileSize());
        destructible = true;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if (entity.getCurrentWeapon().getType() == getType_axe()) {
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    @Override
    public void playSFX() {
        gp.playSFX(11);
    }

    @Override
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, getWorldX() / gp.getTileSize(), getWorldY() / gp.getTileSize());

        return tile;
    }

    @Override
    public Color getParticleColor() {
        Color color = new Color(65, 50, 30);

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
