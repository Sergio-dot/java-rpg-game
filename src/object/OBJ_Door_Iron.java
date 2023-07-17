package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity {

    GamePanel gp;
    public static final String objName = "Iron Door";

    public OBJ_Door_Iron(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_obstacle());
        setName(objName);
        down1 = setup("/objects/door_iron", gp.getTileSize(), gp.getTileSize());
        setCollision(true);

        getSolidArea().x = 0;
        getSolidArea().y = 16;
        getSolidArea().width = 48;
        getSolidArea().height = 32;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        setDialogue();
    }

    public void setDialogue() {
        getDialogues()[0][0] = "It won't budge";
    }

    @Override
    public void interact() {
        startDialogue(this, 0);
    }

}
