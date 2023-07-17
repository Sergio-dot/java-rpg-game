package data;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DataStorage implements Serializable {

    // Player stats
    private int level;
    private int maxLife;
    private int life;
    private int mana;
    private int maxMana;
    private int strength;
    private int dexterity;
    private int exp;
    private int nextLevelExp;
    private int coin;

    // Player inventory
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<Integer> itemAmounts = new ArrayList<>();
    private int currentWeaponSlot;
    private int currentShieldSlot;

    // Object on map
    private String mapObjectNames[][];
    private int mapObjectWorldX[][];
    private int mapObjectWorldY[][];
    private String mapObjectLootNames[][];
    private boolean mapObjectOpened[][];

    int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }

    int getMaxLife() {
        return maxLife;
    }

    void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    int getLife() {
        return life;
    }

    void setLife(int life) {
        this.life = life;
    }

    int getMana() {
        return mana;
    }

    void setMana(int mana) {
        this.mana = mana;
    }

    int getMaxMana() {
        return maxMana;
    }

    void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    int getStrength() {
        return strength;
    }

    void setStrength(int strength) {
        this.strength = strength;
    }

    int getDexterity() {
        return dexterity;
    }

    void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    int getExp() {
        return exp;
    }

    void setExp(int exp) {
        this.exp = exp;
    }

    int getNextLevelExp() {
        return nextLevelExp;
    }

    void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    int getCoin() {
        return coin;
    }

    void setCoin(int coin) {
        this.coin = coin;
    }

    ArrayList<String> getItemNames() {
        return itemNames;
    }

    void setItemNames(ArrayList<String> itemNames) {
        this.itemNames = itemNames;
    }

    ArrayList<Integer> getItemAmounts() {
        return itemAmounts;
    }

    void setItemAmounts(ArrayList<Integer> itemAmounts) {
        this.itemAmounts = itemAmounts;
    }

    int getCurrentWeaponSlot() {
        return currentWeaponSlot;
    }

    void setCurrentWeaponSlot(int currentWeaponSlot) {
        this.currentWeaponSlot = currentWeaponSlot;
    }

    int getCurrentShieldSlot() {
        return currentShieldSlot;
    }

    void setCurrentShieldSlot(int currentShieldSlot) {
        this.currentShieldSlot = currentShieldSlot;
    }

    String[][] getMapObjectNames() {
        return mapObjectNames;
    }

    void setMapObjectNames(String mapObjectNames[][]) {
        this.mapObjectNames = mapObjectNames;
    }

    int[][] getMapObjectWorldX() {
        return mapObjectWorldX;
    }

    void setMapObjectWorldX(int mapObjectWorldX[][]) {
        this.mapObjectWorldX = mapObjectWorldX;
    }

    int[][] getMapObjectWorldY() {
        return mapObjectWorldY;
    }

    void setMapObjectWorldY(int mapObjectWorldY[][]) {
        this.mapObjectWorldY = mapObjectWorldY;
    }

    String[][] getMapObjectLootNames() {
        return mapObjectLootNames;
    }

    void setMapObjectLootNames(String mapObjectLootNames[][]) {
        this.mapObjectLootNames = mapObjectLootNames;
    }

    boolean[][] getMapObjectOpened() {
        return mapObjectOpened;
    }

    void setMapObjectOpened(boolean mapObjectOpened[][]) {
        this.mapObjectOpened = mapObjectOpened;
    }

}
