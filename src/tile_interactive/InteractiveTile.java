package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    // Overridden by subclasses
    public void playSFX() {
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;

        return tile;
    }

    @Override
    public void update() {
        if (isInvincible() == true) {
            setInvincibleCounter(getInvincibleCounter() + 1);
            if (getInvincibleCounter() > 20) {
                setInvincible(false);
                setInvincibleCounter(0);
            }
        }
    }

}
