package entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_Metalplate;
import tile_interactive.InteractiveTile;

public class NPC_BigRock extends Entity {

    public static final String npcName = "Big Rock";

    public NPC_BigRock(GamePanel gp) {
        super(gp);

        setName(npcName);
        setDirection("down");
        setSpeed(4);

        // Override default entity solidArea
        setSolidArea(new Rectangle());
        getSolidArea().x = 2;
        getSolidArea().y = 6;
        getSolidArea().width = 44;
        getSolidArea().height = 40;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        setDialogueSet(-1);

        getSprite();
        setDialogue();
    }

    public void getSprite() {
        up1 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
    }

    public void setDialogue() {
        getDialogues()[0][0] = "It's a giant rock.";
    }

    @Override
    public void setAction() {
    } // Override

    @Override
    public void update() {
    } // Override

    @Override
    public void speak() {

        // Do this character specific class
        facePlayer();
        startDialogue(this, getDialogueSet());

        setDialogueSet(getDialogueSet() + 1);
        if (getDialogues()[getDialogueSet()][0] == null) {
            setDialogueSet(0);
        }
    }

    @Override
    public void move(String d) {

        this.setDirection(d);

        checkCollision();

        if (isCollisionOn() == false) {
            switch (getDirection()) {
                case "up" ->
                    setWorldY(getWorldY() - getSpeed());
                case "down" ->
                    setWorldY(getWorldY() + getSpeed());
                case "left" ->
                    setWorldX(getWorldX() - getSpeed());
                case "right" ->
                    setWorldX(getWorldX() + getSpeed());
            }
        }
        detectPlate();
    }

    public void detectPlate() {

        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        // Create a plate list
        for (int i = 0; i < gp.getiTile()[1].length; i++) {
            if (gp.getiTile()[gp.getCurrentMap()][i] != null
                    && gp.getiTile()[gp.getCurrentMap()][i].getName() != null
                    && gp.getiTile()[gp.getCurrentMap()][i].getName().equals(IT_Metalplate.itName)) {
                plateList.add(gp.getiTile()[gp.getCurrentMap()][i]);
            }
        }

        // Create rock list
        for (int i = 0; i < gp.getNpc()[1].length; i++) {
            if (gp.getNpc()[gp.getCurrentMap()][i] != null
                    && gp.getNpc()[gp.getCurrentMap()][i].getName().equals(NPC_BigRock.npcName)) {
                rockList.add(gp.getNpc()[gp.getCurrentMap()][i]);
            }
        }

        int count = 0;

        // Scan plate list
        for (int i = 0; i < plateList.size(); i++) {
            int xDistance = Math.abs(getWorldX() - plateList.get(i).getWorldX());
            int yDistance = Math.abs(getWorldY() - plateList.get(i).getWorldY());
            int distance = Math.max(xDistance, yDistance);

            if (distance < 8) {
                if (getLinkedEntity() == null) {
                    setLinkedEntity(plateList.get(i));
                    gp.playSFX(3);
                }
            } else {
                if (getLinkedEntity() == plateList.get(i)) {
                    setLinkedEntity(null);
                }
            }
        }

        // Scan rock list
        for (int i = 0; i < rockList.size(); i++) {
            // Count the rock on the plate
            if (rockList.get(i).getLinkedEntity() != null) {
                count++;
            }
        }

        // If all rocks are placed on each of the plates, the iron door gets opened
        if (count == rockList.size()) {
            for (int i = 0; i < gp.getObj()[1].length; i++) {
                if (gp.getObj()[gp.getCurrentMap()][i] != null
                        && gp.getObj()[gp.getCurrentMap()][i].getName().equals(OBJ_Door_Iron.objName)) {
                    gp.getObj()[gp.getCurrentMap()][i] = null;
                    gp.playSFX(21);
                }
            }
        }
    }
}
