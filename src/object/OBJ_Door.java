package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

    GamePanel gp;
    public static final String objName = "Door";

    public OBJ_Door(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_obstacle());
        setName(objName);
        down1 = setup("/objects/door", gp.getTileSize(), gp.getTileSize());
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
        getDialogues()[0][0] = "You need a key to open this door.";
    }

    @Override
    public void interact() {
        startDialogue(this, 0);
    }
}
