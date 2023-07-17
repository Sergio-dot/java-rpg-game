package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_Bat extends Entity {

    GamePanel gp;
    public static final String monName = "Bat";

    public MON_Bat(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_monster());
        setName(monName);
        setDefaultSpeed(4);
        setSpeed(getDefaultSpeed());
        setMaxLife(7);
        setLife(getMaxLife());
        setAttack(7);
        setDefense(1);
        setExp(7);

        // Override default entity solidArea
        getSolidArea().x = 3;
        getSolidArea().y = 15;
        getSolidArea().width = 42;
        getSolidArea().height = 21;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        getSprite();
    }

    public void getSprite() {
        up1 = setup("/monster/bat_down_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/monster/bat_down_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/monster/bat_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/monster/bat_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/monster/bat_down_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/monster/bat_down_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/monster/bat_down_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/monster/bat_down_2", gp.getTileSize(), gp.getTileSize());
    }

    @Override
    public void setAction() {

        // UNCOMMENT BELOW TO MAKE PATHFINDING ALGORITHM WORK, OTHERWISE MOVEMENT WILL BE JUST RANDOM
        if (isOnPath() == true) {
            // Check if it stops chasing
//            checkStopChasing(gp.player, 20, 100);

            // To make following player works, disable the "if reaches the goal, stop the search" snippet
            // Search the direction to go
//            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        } else {
            // Check if it starts chasing
//            checkStartChasing(gp.player, 5, 100);

            // Get a random direction
            getRandomDirection(30);
        }
    }

    @Override
    public void damageReaction() {
        int tempActionLockCounter = getActionLockCounter();
        tempActionLockCounter = 0;
        setActionLockCounter(tempActionLockCounter);
        // direction = gp.player.direction;
        // onPath = true;
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
