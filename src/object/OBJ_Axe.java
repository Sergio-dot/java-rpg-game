package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

    public static final String objName = "Woodcutter's Axe";

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        setType(getType_axe());
        setName(objName);
        down1 = setup("/objects/axe", gp.getTileSize(), gp.getTileSize());
        setAttackValue(2);
        getAttackArea().width = 30;
        getAttackArea().height = 30;
        setDescription("[" + getName() + "]\nA bit rusty, but still \ncan cut some trees.");
        setPrice(75);
        setKnockBackPower(10);
        setMotion1_duration(20);
        setMotion2_duration(40);
    }

}
