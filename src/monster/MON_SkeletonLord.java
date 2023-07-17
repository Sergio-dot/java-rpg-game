package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_SkeletonLord extends Entity {

    GamePanel gp;
    public static final String monName = "Skeleton Lord";

    public MON_SkeletonLord(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_monster());
        setName(monName);
        setDefaultSpeed(1);
        setSpeed(getDefaultSpeed());
        setMaxLife(100);
        setLife(getMaxLife());
        setAttack(10);
        setDefense(4);
        setExp(50);
        setKnockBackPower(5);

        // Override default entity solidArea
        int size = gp.getTileSize() * 5;
        getSolidArea().x = 48;
        getSolidArea().y = 48;
        getSolidArea().width = size - 48 * 2;
        getSolidArea().height = size - 48;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
        getAttackArea().width = 170;
        getAttackArea().height = 170;
        setMotion1_duration(25);
        setMotion2_duration(50);

        getSprite();
        getAttackSprite();
    }

    public void getSprite() {

        int i = 5;

        if (isInRage() == false) {
            up1 = setup("/monster/skeletonlord_up_1", gp.getTileSize() * i, gp.getTileSize() * i);
            up2 = setup("/monster/skeletonlord_up_2", gp.getTileSize() * i, gp.getTileSize() * i);
            down1 = setup("/monster/skeletonlord_down_1", gp.getTileSize() * i, gp.getTileSize() * i);
            down2 = setup("/monster/skeletonlord_down_2", gp.getTileSize() * i, gp.getTileSize() * i);
            left1 = setup("/monster/skeletonlord_left_1", gp.getTileSize() * i, gp.getTileSize() * i);
            left2 = setup("/monster/skeletonlord_left_2", gp.getTileSize() * i, gp.getTileSize() * i);
            right1 = setup("/monster/skeletonlord_right_1", gp.getTileSize() * i, gp.getTileSize() * i);
            right2 = setup("/monster/skeletonlord_right_2", gp.getTileSize() * i, gp.getTileSize() * i);
        }

        if (isInRage() == true) {
            up1 = setup("/monster/skeletonlord_phase2_up_1", gp.getTileSize() * i, gp.getTileSize() * i);
            up2 = setup("/monster/skeletonlord_phase2_up_2", gp.getTileSize() * i, gp.getTileSize() * i);
            down1 = setup("/monster/skeletonlord_phase2_down_1", gp.getTileSize() * i, gp.getTileSize() * i);
            down2 = setup("/monster/skeletonlord_phase2_down_2", gp.getTileSize() * i, gp.getTileSize() * i);
            left1 = setup("/monster/skeletonlord_phase2_left_1", gp.getTileSize() * i, gp.getTileSize() * i);
            left2 = setup("/monster/skeletonlord_phase2_left_2", gp.getTileSize() * i, gp.getTileSize() * i);
            right1 = setup("/monster/skeletonlord_phase2_right_1", gp.getTileSize() * i, gp.getTileSize() * i);
            right2 = setup("/monster/skeletonlord_phase2_right_2", gp.getTileSize() * i, gp.getTileSize() * i);
        }
    }

    public void getAttackSprite() {

        int i = 5;

        if (isInRage() == false) {
            attackUp1 = setup("/monster/skeletonlord_attack_up_1", gp.getTileSize() * i, gp.getTileSize() * i * 2);
            attackUp2 = setup("/monster/skeletonlord_attack_up_2", gp.getTileSize() * i, gp.getTileSize() * i * 2);
            attackDown1 = setup("/monster/skeletonlord_attack_down_1", gp.getTileSize() * i, gp.getTileSize() * i * 2);
            attackDown2 = setup("/monster/skeletonlord_attack_down_2", gp.getTileSize() * i, gp.getTileSize() * i * 2);
            attackLeft1 = setup("/monster/skeletonlord_attack_left_1", gp.getTileSize() * i * 2, gp.getTileSize() * i);
            attackLeft2 = setup("/monster/skeletonlord_attack_left_2", gp.getTileSize() * i * 2, gp.getTileSize() * i);
            attackRight1 = setup("/monster/skeletonlord_attack_right_1", gp.getTileSize() * i * 2, gp.getTileSize() * i);
            attackRight2 = setup("/monster/skeletonlord_attack_right_2", gp.getTileSize() * i * 2, gp.getTileSize() * i);
        }

        if (isInRage() == true) {
            attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1", gp.getTileSize() * i, gp.getTileSize() * i * 2);
            attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2", gp.getTileSize() * i, gp.getTileSize() * i * 2);
            attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1", gp.getTileSize() * i, gp.getTileSize() * i * 2);
            attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2", gp.getTileSize() * i, gp.getTileSize() * i * 2);
            attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1", gp.getTileSize() * i * 2, gp.getTileSize() * i);
            attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2", gp.getTileSize() * i * 2, gp.getTileSize() * i);
            attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1", gp.getTileSize() * i * 2, gp.getTileSize() * i);
            attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2", gp.getTileSize() * i * 2, gp.getTileSize() * i);
        }
    }

    @Override
    public void setAction() {

        if (isInRage() == false && getLife() < getMaxLife() / 2) {
            setInRage(true);
            getSprite();
            getAttackSprite();
            setDefaultSpeed(getDefaultSpeed() + 1);
            setSpeed(getDefaultSpeed());
            setAttack(getAttack() * 2);
        }

        if (getTileDistance(gp.getPlayer()) < 10) {
            moveToPlayer(30);
        } else {
            // Get a random direction
            getRandomDirection(120);
        }

        // Check if it attacks
        if (isAttacking() == false) {
            checkAttack(60, gp.getTileSize() * 7, gp.getTileSize() * 5);
        }
    }

    @Override
    public void damageReaction() {
        int tempActionLockCounter = getActionLockCounter();
        tempActionLockCounter = 0;
        setActionLockCounter(tempActionLockCounter);
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
