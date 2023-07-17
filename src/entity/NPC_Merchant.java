package entity;

import java.awt.Rectangle;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class NPC_Merchant extends Entity {
	
	public NPC_Merchant(GamePanel gp) {
		super(gp);
		
		setDirection("down");
		setSpeed(1);
		
		// Override default entity solidArea
		setSolidArea(new Rectangle());
		getSolidArea().x = 8;
		getSolidArea().y = 16;
		getSolidArea().width = 32;
		getSolidArea().height = 32;
		setSolidAreaDefaultX(getSolidArea().x);
		setSolidAreaDefaultY(getSolidArea().y);
		
		getSprite();
		setDialogue();
		setItems();
	}
	
	public void getSprite() {		
		up1 = setup("/npc/merchant_down_1", gp.getTileSize(), gp.getTileSize());
		up2 = setup("/npc/merchant_down_2", gp.getTileSize(), gp.getTileSize());
		down1 = setup("/npc/merchant_down_1", gp.getTileSize(), gp.getTileSize());
		down2 = setup("/npc/merchant_down_2", gp.getTileSize(), gp.getTileSize());
		left1 = setup("/npc/merchant_down_1", gp.getTileSize(), gp.getTileSize());
		left2 = setup("/npc/merchant_down_2", gp.getTileSize(), gp.getTileSize());
		right1 = setup("/npc/merchant_down_1", gp.getTileSize(), gp.getTileSize());
		right2 = setup("/npc/merchant_down_2", gp.getTileSize(), gp.getTileSize());
	}
	
	public void setDialogue() {
		getDialogues()[0][0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade?";
		getDialogues()[1][0] = "See you again... hehe!";
		getDialogues()[2][0] = "You haven't enough coins to buy that!";
		getDialogues()[3][0] = "You can't carry any more items.";
		getDialogues()[4][0] = "You can't sell an equipped item.";
	}
	
	private void setItems() {
		getInventory().add(new OBJ_Potion_Red(gp));
		getInventory().add(new OBJ_Key(gp));
		getInventory().add(new OBJ_Sword_Normal(gp));
		getInventory().add(new OBJ_Axe(gp));
		getInventory().add(new OBJ_Shield_Wood(gp));
		getInventory().add(new OBJ_Shield_Blue(gp));
                getInventory().add(new OBJ_Tent(gp));
	}
	
        @Override
	public void speak() {
		facePlayer();
		gp.setGameState(gp.getTradeState());
		gp.getUi().npc = this;
	}

}
