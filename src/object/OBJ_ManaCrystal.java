package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {

    GamePanel gp;
    public static final String objName = "Mana Crystal";

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_pickUpOnly());
        setName(objName);
        setValue(1);
        down1 = setup("/objects/manacrystal_full", gp.getTileSize(), gp.getTileSize());
        image = setup("/objects/manacrystal_full", gp.getTileSize(), gp.getTileSize());
        image2 = setup("/objects/manacrystal_blank", gp.getTileSize(), gp.getTileSize());
    }

    @Override
    public boolean use(Entity entity) {
        gp.playSFX(2);
        gp.getUi().addMessage("Mana +" + getValue());
        entity.setMana(entity.getMana() + getValue());

        return true;
    }

}
