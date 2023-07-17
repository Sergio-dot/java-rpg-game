package main;

import entity.NPC_BigRock;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_Bat;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import monster.MON_RedSlime;
import monster.MON_SkeletonLord;
import object.OBJ_Axe;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Door_Iron;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Pickaxe;
import object.OBJ_Potion_Red;
import object.OBJ_Sword_Legendary;
import object.OBJ_Tent;
import tile_interactive.IT_DestructibleWall;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_Metalplate;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // World Map
        int mapNum = 0;
        int i = 0;

        gp.getObj()[mapNum][i] = new OBJ_Axe(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 33);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 7);
        i++;
//		gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
//		gp.obj[mapNum][i].worldX = gp.tileSize * 35;
//		gp.obj[mapNum][i].worldY = gp.tileSize * 21;
//		i++;
        gp.getObj()[mapNum][i] = new OBJ_Lantern(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 18);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 20);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Tent(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 19);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 20);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 14);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 28);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 12);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 12);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setObjectLoot(new OBJ_Key(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 30);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 29);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setObjectLoot(new OBJ_Tent(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 16);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 20);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setObjectLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 17);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 20);
        i++;

        // Dungeon Map F1
        mapNum = 2;
        i = 0;

        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setObjectLoot(new OBJ_Pickaxe(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 40);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 41);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setObjectLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 13);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 16);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setObjectLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 26);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 34);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setObjectLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 27);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 15);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door_Iron(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 18);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 23);
        i++;

        // Dungeon Map F2
        mapNum = 3;
        i = 0;

        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setObjectLoot(new OBJ_Sword_Legendary(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 25);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 8);
        i++;
    }

    public void setNPC() {

        // World Map
        int mapNum = 0;
        int i = 0;

        gp.getNpc()[mapNum][i] = new NPC_OldMan(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize() * 21);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize() * 21);
        i++;

        // Merchant Hut
        mapNum = 1;
        i = 0;

        gp.getNpc()[mapNum][i] = new NPC_Merchant(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize() * 12);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize() * 7);
        i++;

        // Dungeon F1
        mapNum = 2;
        i = 0;

        gp.getNpc()[mapNum][i] = new NPC_BigRock(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize() * 20);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize() * 25);
        i++;
        gp.getNpc()[mapNum][i] = new NPC_BigRock(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize() * 11);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize() * 18);
        i++;
        gp.getNpc()[mapNum][i] = new NPC_BigRock(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize() * 23);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize() * 14);
        i++;
    }

    public void setMonster() {

        // World Map
        int mapNum = 0;
        int i = 0;

        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 21);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 38);
        i++;
        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 23);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 42);
        i++;
        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 24);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 37);
        i++;
        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 34);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 42);
        i++;
        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 38);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 37);
        i++;
        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 35);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 8);
        i++;
        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 40);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 8);
        i++;
        gp.getMonster()[mapNum][i] = new MON_Orc(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 12);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 33);
        i++;

        // Dungeon F1 Map
        mapNum = 2;
        i = 0;

        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 34);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 39);
        i++;
        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 36);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 25);
        i++;
        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 39);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 26);
        i++;
        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 28);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 11);
        i++;
        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 10);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 19);
        i++;

        // Dungeon F2 Map (Boss)
        mapNum = 3;
        i = 0;

        gp.getMonster()[mapNum][i] = new MON_SkeletonLord(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 23);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 16);
        i++;
    }

    public void setInteractiveTiles() {

        // World Map
        int mapNum = 0;
        int i = 0;

        // Near the lake
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 27, 12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 28, 12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 29, 12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 30, 12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 31, 12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 32, 12);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 33, 12);
        i++;
        // Near the lake

        // Path to the hut
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 18, 40);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 17, 40);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 16, 40);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 15, 40);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 14, 40);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 13, 40);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 13, 41);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 12, 41);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 11, 41);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 10, 41);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 10, 40);
        i++;
        // Path to the hut

        // Path to a chest
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 29, 31);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 28, 31);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 27, 31);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 27, 30);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 27, 29);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 27, 28);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 27, 27);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 26, 27);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp, 25, 27);
        i++;
        // Path to a chest

        // Dungeon
        mapNum = 2;
        i = 0;

        // Dungeon F1
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 18, 30);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 17, 31);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 17, 32);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 17, 34);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 18, 34);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 18, 33);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 10, 22);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 10, 24);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 38, 18);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 38, 19);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 38, 20);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 38, 21);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 18, 13);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 18, 14);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 22, 28);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 30, 28);
        i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp, 32, 28);
        i++;

        gp.getiTile()[mapNum][i] = new IT_Metalplate(gp, 20, 22);
        i++;
        gp.getiTile()[mapNum][i] = new IT_Metalplate(gp, 8, 17);
        i++;
        gp.getiTile()[mapNum][i] = new IT_Metalplate(gp, 39, 31);
        i++;
        // Dungeon F1
    }
}
