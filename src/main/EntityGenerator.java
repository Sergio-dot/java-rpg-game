package main;

import entity.Entity;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Door_Iron;
import object.OBJ_Fireball;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Pickaxe;
import object.OBJ_Potion_Red;
import object.OBJ_Rock;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Legendary;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class EntityGenerator {

    GamePanel gp;

    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            case OBJ_Axe.objName -> obj = new OBJ_Axe(gp);
            case OBJ_Boots.objName -> obj = new OBJ_Boots(gp);
            case OBJ_Chest.objName -> obj = new OBJ_Chest(gp);
            case OBJ_Coin_Bronze.objName -> obj = new OBJ_Coin_Bronze(gp);
            case OBJ_Door_Iron.objName -> obj = new OBJ_Door_Iron(gp);
            case OBJ_Door.objName -> obj = new OBJ_Door(gp);
            case OBJ_Fireball.objName -> obj = new OBJ_Fireball(gp);
            case OBJ_Heart.objName -> obj = new OBJ_Heart(gp);
            case OBJ_Key.objName -> obj = new OBJ_Key(gp);
            case OBJ_Lantern.objName -> obj = new OBJ_Lantern(gp);
            case OBJ_ManaCrystal.objName -> obj = new OBJ_ManaCrystal(gp);
            case OBJ_Pickaxe.objName -> obj = new OBJ_Pickaxe(gp);
            case OBJ_Potion_Red.objName -> obj = new OBJ_Potion_Red(gp);
            case OBJ_Rock.objName -> obj = new OBJ_Rock(gp);
            case OBJ_Shield_Blue.objName -> obj = new OBJ_Shield_Blue(gp);
            case OBJ_Shield_Wood.objName -> obj = new OBJ_Shield_Wood(gp);
            case OBJ_Sword_Legendary.objName -> obj = new OBJ_Sword_Legendary(gp);
            case OBJ_Sword_Normal.objName -> obj = new OBJ_Sword_Normal(gp);
            case OBJ_Tent.objName -> obj = new OBJ_Tent(gp);
        }
        return obj;
    }
}
