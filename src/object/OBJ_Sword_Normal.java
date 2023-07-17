package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public static final String objName = "Normal Sword";

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        setType(getType_sword());
        setName(objName);
        down1 = setup("/objects/sword_normal", gp.getTileSize(), gp.getTileSize());
        setAttackValue(1);
        getAttackArea().width = 36;
        getAttackArea().height = 36;
        setDescription("[" + getName() + "]\nAn old sword.");
        setPrice(20);
        setKnockBackPower(2);
        setMotion1_duration(5);
        setMotion2_duration(25);
    }

}
