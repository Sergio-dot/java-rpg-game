package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_GreenSlime extends Entity {

    GamePanel gp;
    public static final String monName = "Green Slime";

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_monster());
        setName(monName);
        setDefaultSpeed(1);
        setSpeed(getDefaultSpeed());
        setMaxLife(4);
        setLife(getMaxLife());
        setAttack(5);
        setDefense(0);
        setExp(2);
//		projectile = new OBJ_Rock(gp); // Changed: only Red Slime shoot rocks

        // Override default entity solidArea
        getSolidArea().x = 3;
        getSolidArea().y = 18;
        getSolidArea().width = 42;
        getSolidArea().height = 30;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        getSprite();
    }

    public void getSprite() {
        up1 = setup("/monster/greenslime_down_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/monster/greenslime_down_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/monster/greenslime_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/monster/greenslime_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/monster/greenslime_down_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/monster/greenslime_down_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/monster/greenslime_down_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/monster/greenslime_down_2", gp.getTileSize(), gp.getTileSize());
    }

    @Override
    public void setAction() {

        if (isOnPath() == true) {
            // Check if it stops chasing
            checkStopChasing(gp.getPlayer(), 20, 100);

            // To make following player works, disable the "if reaches the goal, stop the search" snippet
            // Search the direction to go
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));
        } else {
            // Check if it starts chasing
            checkStartChasing(gp.getPlayer(), 5, 100);

            // Get a random direction
            getRandomDirection(120);
        }
    }
    
    @Override
    public void damageReaction() {
        int tempActionLockCounter = getActionLockCounter();
        tempActionLockCounter = 0;
        setActionLockCounter(tempActionLockCounter);
        // direction = gp.player.direction;
        setOnPath(true);
    }

    @Override
    public void checkDrop() {
        // Cast a dice
        int i = new Random().nextInt(100) + 1;

        // Set the monster drop
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }

}
