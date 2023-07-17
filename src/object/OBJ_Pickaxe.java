package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {

    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);

        setType(getType_pickaxe());
        setName(objName);
        down1 = setup("/objects/pickaxe", gp.getTileSize(), gp.getTileSize());
        setAttackValue(1);
        getAttackArea().width = 30;
        getAttackArea().height = 30;
        setDescription("[" + getName() + "]\nWalls will fear you with\nthis in your hands!");
        setPrice(75);
        setKnockBackPower(10);
        setMotion1_duration(10);
        setMotion2_duration(20);
    }

}
