package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Legendary extends Entity {

    public static final String objName = "Legendary Sword";

    public OBJ_Sword_Legendary(GamePanel gp) {
        super(gp);

        setType(getType_sword());
        setName(objName);
        down1 = setup("/objects/sword_legendary", gp.getTileSize(), gp.getTileSize());
        setAttackValue(10);
        getAttackArea().width = 36;
        getAttackArea().height = 36;
        setDescription("[" + getName() + "]\nA sword engulfed by power.");
        setPrice(2000);
        setKnockBackPower(2);
        setMotion1_duration(5);
        setMotion2_duration(15);
    }

}
