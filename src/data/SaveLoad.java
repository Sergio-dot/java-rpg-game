package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.GamePanel;

@SuppressWarnings("serial")
public class SaveLoad extends DataStorage {

    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            // Player stats
            ds.setLevel(gp.getPlayer().getLevel());
            ds.setMaxLife(gp.getPlayer().getMaxLife());
            ds.setLife(gp.getPlayer().getLife());
            ds.setMaxMana(gp.getPlayer().getMaxMana());
            ds.setMana(gp.getPlayer().getMana());
            ds.setStrength(gp.getPlayer().getStrength());
            ds.setDexterity(gp.getPlayer().getDexterity());
            ds.setExp(gp.getPlayer().getExp());
            ds.setNextLevelExp(gp.getPlayer().getNextLevelExp());
            ds.setCoin(gp.getPlayer().getCoin());

            // Player inventory
            for (int i = 0; i < gp.getPlayer().getInventory().size(); i++) {
                ds.getItemNames().add(gp.getPlayer().getInventory().get(i).getName());
                ds.getItemAmounts().add(gp.getPlayer().getInventory().get(i).getAmount());
            }

            // Player equipment
            ds.setCurrentWeaponSlot(gp.getPlayer().getCurrentWeaponSlot());
            ds.setCurrentShieldSlot(gp.getPlayer().getCurrentShieldSlot());

            // Objects on map
            ds.setMapObjectNames(new String[gp.getMaxMap()][gp.getObj()[1].length]);
            ds.setMapObjectWorldX(new int[gp.getMaxMap()][gp.getObj()[1].length]);
            ds.setMapObjectWorldY(new int[gp.getMaxMap()][gp.getObj()[1].length]);
            ds.setMapObjectLootNames(new String[gp.getMaxMap()][gp.getObj()[1].length]);
            ds.setMapObjectOpened(new boolean[gp.getMaxMap()][gp.getObj()[1].length]);

            for (int mapNum = 0; mapNum < gp.getMaxMap(); mapNum++) {
                for (int i = 0; i < gp.getObj()[1].length; i++) {
                    if (gp.getObj()[mapNum][i] == null) {
                        ds.getMapObjectNames()[mapNum][i] = "NA";
                    } else {
                        ds.getMapObjectNames()[mapNum][i] = gp.getObj()[mapNum][i].getName();
                        ds.getMapObjectWorldX()[mapNum][i] = gp.getObj()[mapNum][i].getWorldX();
                        ds.getMapObjectWorldY()[mapNum][i] = gp.getObj()[mapNum][i].getWorldY();
                        if (gp.getObj()[mapNum][i].loot != null) { // Check if object has loot or not
                            ds.getMapObjectLootNames()[mapNum][i] = gp.getObj()[mapNum][i].loot.getName();
                        }
                        ds.getMapObjectOpened()[mapNum][i] = gp.getObj()[mapNum][i].isOpened();
                    }
                }
            }

            // Write the DataStorage object
            oos.writeObject(ds);
        } catch (IOException e) {
        }
    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            // Read DataStorage object
            DataStorage ds = (DataStorage) ois.readObject();

            // Player stats
            gp.getPlayer().setLevel(ds.getLevel());
            gp.getPlayer().setMaxLife(ds.getMaxLife());
            gp.getPlayer().setLife(ds.getLife());
            gp.getPlayer().setMaxMana(ds.getMaxMana());
            gp.getPlayer().setMana(ds.getMana());
            gp.getPlayer().setStrength(ds.getStrength());
            gp.getPlayer().setDexterity(ds.getDexterity());
            gp.getPlayer().setExp(ds.getExp());
            gp.getPlayer().setNextLevelExp(ds.getNextLevelExp());
            gp.getPlayer().setCoin(ds.getCoin());

            // Player inventory
            gp.getPlayer().getInventory().clear(); // Clear player's inventory to prevent duplication
            for (int i = 0; i < ds.getItemNames().size(); i++) {
                gp.getPlayer().getInventory().add(gp.geteGenerator().getObject(ds.getItemNames().get(i))); // Convert item names in objects and insert them into inventory
                gp.getPlayer().getInventory().get(i).setAmount(ds.getItemAmounts().get(i)); // Load the amount of items in inventory
            }

            // Player equipment
            gp.getPlayer().setCurrentWeapon(gp.getPlayer().getInventory().get(ds.getCurrentWeaponSlot()));
            gp.getPlayer().setCurrentShield(gp.getPlayer().getInventory().get(ds.getCurrentShieldSlot()));
            gp.getPlayer().getAttack();
            gp.getPlayer().getPlayerAttack();
            gp.getPlayer().getDefense();
            gp.getPlayer().getPlayerDefense();
            gp.getPlayer().getAttackImage();

            // Objects on map
            for (int mapNum = 0; mapNum < gp.getMaxMap(); mapNum++) {
                for (int i = 0; i < gp.getObj()[1].length; i++) {
                    if (ds.getMapObjectNames()[mapNum][i].equals("NA")) {
                        gp.getObj()[mapNum][i] = null;
                    } else {
                        gp.getObj()[mapNum][i] = gp.geteGenerator().getObject(ds.getMapObjectNames()[mapNum][i]);
                        gp.getObj()[mapNum][i].setWorldX(ds.getMapObjectWorldX()[mapNum][i]);
                        gp.getObj()[mapNum][i].setWorldY(ds.getMapObjectWorldY()[mapNum][i]);
                        if (ds.getMapObjectLootNames()[mapNum][i] != null) { // If object has loot
                            gp.getObj()[mapNum][i].setObjectLoot(gp.geteGenerator().getObject(ds.getMapObjectLootNames()[mapNum][i]));
                        }
                        gp.getObj()[mapNum][i].setOpened(ds.getMapObjectOpened()[mapNum][i]);
                        if (gp.getObj()[mapNum][i].isOpened() == true) { // If object is already opened (e.g. a chest in the map)
                            gp.getObj()[mapNum][i].down1 = gp.getObj()[mapNum][i].image2; // Load opened object image
                        }
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
        }
    }

}
