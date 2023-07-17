package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

    GamePanel gp;
    public static final String objName = "Key";

    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_consumable());
        setName(objName);
        down1 = setup("/objects/key", gp.getTileSize(), gp.getTileSize());
        setDescription("[" + getName() + "]\nIt opens a door.");
        setPrice(100);
        setStackable(true);

        setDialogue();
    }

    public void setDialogue() {

        getDialogues()[0][0] = "You used the " + getName() + " and opened the door.";

        getDialogues()[1][0] = "I think a door is needed to use this key.";
    }

    @Override
    public boolean use(Entity entity) {
        gp.setGameState(gp.getDialogueState());

        int objIndex = getDetected(entity, gp.getObj(), "Door");

        if (objIndex != 999) {
            startDialogue(this, 0);
            gp.playSFX(3);
            gp.getObj()[gp.getCurrentMap()][objIndex] = null;
            return true;
        } else {
            startDialogue(this, 1);
            return false;
        }
    }
}
