package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_Orc extends Entity {

    GamePanel gp;
    public static final String monName = "Orc";

    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_monster());
        setName(monName);
        setDefaultSpeed(1);
        setSpeed(getDefaultSpeed());
        setMaxLife(10);
        setLife(getMaxLife());
        setAttack(8);
        setDefense(2);
        setExp(10);
        setKnockBackPower(5);

        // Override default entity solidArea
        getSolidArea().x = 4;
        getSolidArea().y = 4;
        getSolidArea().width = 40;
        getSolidArea().height = 44;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
        getAttackArea().width = 48;
        getAttackArea().height = 48;
        setMotion1_duration(40);
        setMotion2_duration(85);

        getSprite();
        getAttackSprite();
    }

    public void getSprite() {
        up1 = setup("/monster/orc_up_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/monster/orc_up_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/monster/orc_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/monster/orc_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/monster/orc_left_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/monster/orc_left_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/monster/orc_right_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/monster/orc_right_2", gp.getTileSize(), gp.getTileSize());
    }

    public void getAttackSprite() {
        attackUp1 = setup("/monster/orc_attack_up_1", gp.getTileSize(), gp.getTileSize() * 2);
        attackUp2 = setup("/monster/orc_attack_up_2", gp.getTileSize(), gp.getTileSize() * 2);
        attackDown1 = setup("/monster/orc_attack_down_1", gp.getTileSize(), gp.getTileSize() * 2);
        attackDown2 = setup("/monster/orc_attack_down_2", gp.getTileSize(), gp.getTileSize() * 2);
        attackLeft1 = setup("/monster/orc_attack_left_1", gp.getTileSize() * 2, gp.getTileSize());
        attackLeft2 = setup("/monster/orc_attack_left_2", gp.getTileSize() * 2, gp.getTileSize());
        attackRight1 = setup("/monster/orc_attack_right_1", gp.getTileSize() * 2, gp.getTileSize());
        attackRight2 = setup("/monster/orc_attack_right_2", gp.getTileSize() * 2, gp.getTileSize());
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

        // Check if it attacks
        if (isAttacking() == false) {
            checkAttack(30, gp.getTileSize() * 4, gp.getTileSize());
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
