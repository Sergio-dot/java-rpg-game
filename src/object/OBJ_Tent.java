package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {

    GamePanel gp;
    public static final String objName = "Tent";

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_consumable());
        setName(objName);
        down1 = setup("/objects/tent", gp.getTileSize(), gp.getTileSize());
        setDescription("[" + getName() + "]\nYou can sleep until\nnext morning.");
        setPrice(100);
        setStackable(true);
    }

    @Override
    public boolean use(Entity entity) {
        gp.setGameState(gp.getSleepState());
        gp.playSFX(14);
        gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
        gp.getPlayer().setMana(gp.getPlayer().getMaxMana());
        gp.getPlayer().getSleepingImage(down1);

        return true;
    }

}
