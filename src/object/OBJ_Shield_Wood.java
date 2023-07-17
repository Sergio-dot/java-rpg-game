package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

    public static final String objName = "Wood Shield";

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        setType(getType_shield());
        setName(objName);
        down1 = setup("/objects/shield_wood", gp.getTileSize(), gp.getTileSize());
        setDefenseValue(1);
        setDescription("[" + getName() + "]\nMade by wood.");
        setPrice(35);
    }

}
