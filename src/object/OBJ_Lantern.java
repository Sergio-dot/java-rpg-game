package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {

    public static final String objName = "Lantern";

    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        setType(getType_light());
        setName(objName);
        down1 = setup("/objects/lantern", gp.getTileSize(), gp.getTileSize());
        setDescription("[" + getName() + "]\nIlluminates your\nsurroundings.");
        setPrice(200);
        setLightRadius(350);
    }

}
