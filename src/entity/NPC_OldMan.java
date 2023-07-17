package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        setDirection("down");
        setSpeed(1);

        // Override default entity solidArea
        setSolidArea(new Rectangle());
        getSolidArea().x = 8;
        getSolidArea().y = 16;
        getSolidArea().width = 30;
        getSolidArea().height = 30;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        setDialogueSet(-1);

        getSprite();
        setDialogue();
    }

    public void getSprite() {
        up1 = setup("/npc/oldman_up_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/npc/oldman_up_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/npc/oldman_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/npc/oldman_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/npc/oldman_left_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/npc/oldman_left_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/npc/oldman_right_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/npc/oldman_right_2", gp.getTileSize(), gp.getTileSize());
    }

    public void setDialogue() {
        getDialogues()[0][0] = "Hello, lad.";
        getDialogues()[0][1] = "So you've come to this island to \nfind the treasure?";
        getDialogues()[0][2] = "I used to be a great wizard but now... \nI'm a bit too old for taking an adventure";
        getDialogues()[0][3] = "Well, good luck on you.";

        getDialogues()[1][0] = "If you feel tired, rest at the water.";
        getDialogues()[1][1] = "It kinda replenish your energy and vitality.";
        getDialogues()[1][2] = "I don't know why but that's how it works.";
        getDialogues()[1][3] = "In any case, don't push yourself too hard...";

        getDialogues()[2][0] = "I wonder how to open that door...\nMaybe there's a key somewhere nearby?";
    }

    @Override
    public void setAction() {

        if (isOnPath() == true) {
            int goalCol = 12;
            int goalRow = 9;

            searchPath(goalCol, goalRow);
        } else {
            int tempActionLockCounter = getActionLockCounter();

            if (tempActionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // Pick a number between 1 and 100

                if (i <= 25) {
                    setDirection("up");
                }
                if (i > 25 && i <= 50) {
                    setDirection("down");
                }
                if (i > 50 && i <= 75) {
                    setDirection("left");
                }
                if (i > 75 && i <= 100) {
                    setDirection("right");
                }
                tempActionLockCounter = 0;
                setActionLockCounter(tempActionLockCounter);
            }
        }
    }

    @Override
    public void speak() {
        // Do this character specific class
        facePlayer();
        startDialogue(this, getDialogueSet());

        setDialogueSet(getDialogueSet() + 1);
        if (getDialogues()[getDialogueSet()][0] == null) {
            setDialogueSet(0);
        }

//		setOnPath(true);
    }
}
