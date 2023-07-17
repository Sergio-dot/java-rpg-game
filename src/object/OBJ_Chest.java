package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

    GamePanel gp;
    public static final String objName = "Chest";

    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_obstacle());
        setName(objName);

        image = setup("/objects/chest", gp.getTileSize(), gp.getTileSize());
        image2 = setup("/objects/chest_opened", gp.getTileSize(), gp.getTileSize());
        down1 = image;
        setCollision(true);

        getSolidArea().x = 4;
        getSolidArea().y = 16;
        getSolidArea().width = 40;
        getSolidArea().height = 32;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
    }

    @Override
    public void setObjectLoot(Entity loot) {
        this.loot = loot;
        setDialogue();
    }

    public void setDialogue() {
        getDialogues()[0][0] = "You opened the chest and find a " + loot.getName() + "\n...But you can't carry any more items!";
        getDialogues()[1][0] = "You opened the chest and find a " + loot.getName() + "!\nYou obtain the " + loot.getName() + "!";
        getDialogues()[2][0] = "It's empty";
    }

    @Override
    public void interact() {

        if (isOpened() == false) {
            gp.playSFX(3);

            if (gp.getPlayer().canObtainItem(loot) == false) {
                startDialogue(this, 0);
            } else {
                startDialogue(this, 1);
                down1 = image2;
                setOpened(true);
            }
        } else {
            startDialogue(this, 2);
        }
    }
}
