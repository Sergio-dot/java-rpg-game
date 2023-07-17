package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    GamePanel gp;
    public static final String objName = "Heart";

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_pickUpOnly());
        setName(objName);
        setValue(2);
        down1 = setup("/objects/heart_full", gp.getTileSize(), gp.getTileSize()); // Used for pick up only item
        image = setup("/objects/heart_full", gp.getTileSize(), gp.getTileSize()); // HUD image
        image2 = setup("/objects/heart_half", gp.getTileSize(), gp.getTileSize()); // HUD image
        image3 = setup("/objects/heart_blank", gp.getTileSize(), gp.getTileSize()); // HUD image
    }

    @Override
    public boolean use(Entity entity) {
        gp.playSFX(2);
        gp.getUi().addMessage("Life +" + getValue());
        entity.setLife(entity.getLife() + getValue());

        return true;
    }
}
