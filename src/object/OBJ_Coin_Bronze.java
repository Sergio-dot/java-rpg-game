package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {

    GamePanel gp;
    public static final String objName = "Bronze Coin";

    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_pickUpOnly());
        setName(objName);
        setValue(10);
        down1 = setup("/objects/coin_bronze", gp.getTileSize(), gp.getTileSize());
    }

    @Override
    public boolean use(Entity entity) {
        gp.playSFX(1);
        gp.getUi().addMessage("Coin: " + getValue());
        gp.getPlayer().setCoin(gp.getPlayer().getCoin() + getValue());

        return true;
    }
}
