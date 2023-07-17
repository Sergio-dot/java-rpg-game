package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;
    public static final String objName = "Red Potion";

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_consumable());
        setName(objName);
        setValue(5);
        down1 = setup("/objects/potion_red", gp.getTileSize(), gp.getTileSize());
        setDescription("[" + getName() + "]\nHeals your life by " + getValue() + ".");
        setPrice(25);
        setStackable(true);

        setDialogue();
    }

    public void setDialogue() {
        getDialogues()[0][0] = "You drink the " + getName() + "!\n"
                + "Your life has been recovered by " + getValue() + ".";
    }

    @Override
    public boolean use(Entity entity) {
        startDialogue(this, 0);
        entity.setLife(entity.getLife() + getValue());
        gp.playSFX(2);
        return true;
    }

}
