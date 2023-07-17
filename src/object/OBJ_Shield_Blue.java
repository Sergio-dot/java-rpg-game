package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {

    public static final String objName = "Blue Shield";

    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        setType(getType_shield());
        setName(objName);
        down1 = setup("/objects/shield_blue", gp.getTileSize(), gp.getTileSize());
        setDefenseValue(2);
        setDescription("[" + getName() + "]\nA shiny blue shield.");
        setPrice(250);
    }

}
