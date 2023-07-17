package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, charybdis;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;
    int charIndex;
    String combinedText = "";

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/marumonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/charybdis.ttf");
            charybdis = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
        }

        // Create HUD object
        // Hearts (Life)
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        // Crystals (Mana)
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        // Coins (Money)
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        // g2.setFont(charybdis); Charybdis font
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // Title state
        if (gp.getGameState() == gp.getTitleState()) {
            drawTitleScreen();
        }

        // Play state
        if (gp.getGameState() == gp.getPlayState()) {
            drawPlayerLife();
            drawPlayerMana();
            drawMessage();
        }

        // Pause state
        if (gp.getGameState() == gp.getPauseState()) {
            drawPlayerLife();
            drawPlayerMana();
            drawPauseScreen();
        }

        // Dialogue state
        if (gp.getGameState() == gp.getDialogueState()) {
            drawDialogueScreen();
        }

        // Character state
        if (gp.getGameState() == gp.getCharacterState()) {
            drawCharacterScreen();
            drawInventory(gp.getPlayer(), true);
        }

        // Option state
        if (gp.getGameState() == gp.getOptionState()) {
            drawOptionScreen();
        }

        // Game over state
        if (gp.getGameState() == gp.getGameOverState()) {
            drawGameOverScreen();
        }

        // Transition state
        if (gp.getGameState() == gp.getTransitionState()) {
            drawTransition();
        }

        // Trade state
        if (gp.getGameState() == gp.getTradeState()) {
            drawTradeScreen();
        }

        // Sleep state
        if (gp.getGameState() == gp.getSleepState()) {
            drawSleepScreen();
        }
    }

    public void drawPlayerLife() {

        int x = gp.getTileSize() / 2;
        int y = gp.getTileSize() / 2;
        int i = 0;

        // Draw max life
        while (i < gp.getPlayer().getMaxLife() / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.getTileSize();
        }

        // Reset values
        x = gp.getTileSize() / 2;
        y = gp.getTileSize() / 2;
        i = 0;

        // Draw current life
        while (i < gp.getPlayer().getLife()) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.getPlayer().getLife()) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.getTileSize();
        }
    }

    public void drawPlayerMana() {
        int x = (gp.getTileSize() / 2) - 5;
        int y = (int) (gp.getTileSize() * 1.5);
        int i = 0;

        // Draw max mana
        while (i < gp.getPlayer().getMaxMana()) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        // Draw mana
        x = (gp.getTileSize() / 2) - 5;
        y = (int) (gp.getTileSize() * 1.5);
        i = 0;
        while (i < gp.getPlayer().getMana()) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawMessage() {
        int messageX = gp.getTileSize();
        int messageY = gp.getTileSize() * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // Increase messageCounter by 1
                messageCounter.set(i, counter); // Set the counter to the array
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {
        // Background color
        g2.setColor(new Color(52, 73, 94));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        // Title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Adventure Boy";
        int x = getXforCenteredText(text);
        int y = gp.getTileSize() * 3;

        // Text shadow
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        // Main text
        g2.setColor(Color.yellow);
        g2.drawString(text, x, y);

        // Character image
        x = gp.getScreenWidth() / 2 - (gp.getTileSize() * 2) / 2;
        y += gp.getTileSize() * 2;
        g2.drawImage(gp.getPlayer().down1, x, y, gp.getTileSize() * 2, gp.getTileSize() * 2, null);

        // Menu
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.getTileSize() * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.getTileSize(), y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.getTileSize();
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.getTileSize(), y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "QUIT GAME";
        x = getXforCenteredText(text);
        y += gp.getTileSize();
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.getTileSize(), y);
        }
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        int x = getXforCenteredText(text);
        int y = gp.getScreenHeight() / 2;

        // Shadow
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        x = getXforCenteredText(text);
        y = gp.getScreenHeight() / 2;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // Dialogue window
        int x = gp.getTileSize() * 3;
        int y = gp.getTileSize() / 2;
        int width = gp.getScreenWidth() - (gp.getTileSize() * 6);
        int height = gp.getTileSize() * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));
        x += gp.getTileSize();
        y += gp.getTileSize();

        if (npc.getDialogues()[npc.getDialogueSet()][npc.getDialogueIndex()] != null) {
//            currentDialogue = npc.getDialogues()[npc.getDialogueSet()][npc.getDialogueIndex()]; // Instant text

            // Text letter-by-letter
            char characters[] = npc.getDialogues()[npc.getDialogueSet()][npc.getDialogueIndex()].toCharArray();
            if (charIndex < characters.length) {
                gp.playSFX(17);
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;
            }

            if (gp.getKeyH().isEnterPressed() == true) {
                charIndex = 0;
                combinedText = "";
                if (gp.getGameState() == gp.getDialogueState()) {
                    npc.setDialogueIndex(npc.getDialogueIndex() + 1);
                    gp.getKeyH().setEnterPressed(false);
                }
            }
        } else { // If no text is in the array
            npc.setDialogueIndex(0);

            if (gp.getGameState() == gp.getDialogueState()) {
                gp.setGameState(gp.getPlayState());
            }
        }

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {
        // Create a frame
        final int frameX = gp.getTileSize() * 2;
        final int frameY = gp.getTileSize();
        final int frameWidth = gp.getTileSize() * 5;
        final int frameHeight = gp.getTileSize() * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Display text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.getTileSize();
        final int lineHeight = 35;

        // Parameters
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);

        // Values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.getTileSize();
        String value;

        value = String.valueOf(gp.getPlayer().getLevel());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getLife() + "/" + gp.getPlayer().getMaxLife());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getMana() + "/" + gp.getPlayer().getMaxMana());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getStrength());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getDexterity());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getPlayerAttack());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getPlayerDefense());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getExp());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getNextLevelExp());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.getPlayer().getCoin());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.getPlayer().getCurrentWeapon().down1, tailX - gp.getTileSize(), textY - 24, null);
        textY += gp.getTileSize();

        g2.drawImage(gp.getPlayer().getCurrentShield().down1, tailX - gp.getTileSize(), textY - 24, null);
    }

    public void drawInventory(Entity entity, boolean cursor) {
        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;

        if (entity == gp.getPlayer()) {
            frameX = gp.getTileSize() * 12;
            frameY = gp.getTileSize();
            frameWidth = gp.getTileSize() * 6;
            frameHeight = gp.getTileSize() * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = gp.getTileSize() * 2;
            frameY = gp.getTileSize();
            frameWidth = gp.getTileSize() * 6;
            frameHeight = gp.getTileSize() * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // Frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.getTileSize() + 3;

        // Draw player's items
        for (int i = 0; i < entity.getInventory().size(); i++) {
            // Equip cursor
            if (entity.getInventory().get(i) == entity.getCurrentWeapon()
                    || entity.getInventory().get(i) == entity.getCurrentShield()
                    || entity.getInventory().get(i) == entity.getCurrentLight()) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.getTileSize(), gp.getTileSize(), 10, 10);
            }

            // Draw items
            g2.drawImage(entity.getInventory().get(i).down1, slotX, slotY, null);

            // Display item's amount
            if (entity == gp.getPlayer() && entity.getInventory().get(i).getAmount() > 1) {
                g2.setFont(g2.getFont().deriveFont(32F));
                int amountX;
                int amountY;

                String s = "" + entity.getInventory().get(i).getAmount();
                amountX = getXforAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.getTileSize();

                // Shadow
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);

                // Amount number
                g2.setColor(Color.white);
                g2.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // Cursor
        if (cursor == true) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.getTileSize();
            int cursorHeight = gp.getTileSize();
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.getTileSize() * 3;

            // Description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.getTileSize();
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            g2.setFont(g2.getFont().deriveFont(28F));
            if (itemIndex < entity.getInventory().size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight); // Does not draw description frame if cursor is on a blank slot
                for (String line : entity.getInventory().get(itemIndex).getDescription().split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        int x;
        int y;
        String text = "Game Over";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        // Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.getTileSize() * 4;
        g2.drawString(text, x, y);

        // Main
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.getTileSize() * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        // Back to title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
    }

    public void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        // Sub window
        int frameX = gp.getTileSize() * 6;
        int frameY = gp.getTileSize();
        int frameWidth = gp.getTileSize() * 8;
        int frameHeight = gp.getTileSize() * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0 -> options_top(frameX, frameY);
            case 1 -> options_fullScreenNotification(frameX, frameY);
            case 2 -> options_control(frameX, frameY);
            case 3 -> options_endGameConfirmation(frameX, frameY);
        }

        gp.getKeyH().setEnterPressed(false);
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.getTileSize();
        g2.drawString(text, textX, textY);

        // Full screen on/off
        textX = frameX + gp.getTileSize();
        textY += gp.getTileSize() * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed() == true) {
                if (gp.isFullScreenOn() == false) {
                    gp.setFullScreenOn(true);
                } else if (gp.isFullScreenOn() == true) {
                    gp.setFullScreenOn(false);
                }
                subState = 1;
            }
        }

        // BGM
        textY += gp.getTileSize();
        g2.drawString("BGM", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // SFX
        textY += gp.getTileSize();
        g2.drawString("SFX", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        // Control
        textY += gp.getTileSize();
        g2.drawString("Control", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed() == true) {
                subState = 2;
                commandNum = 0;
            }
        }

        // Quit game
        textY += gp.getTileSize();
        g2.drawString("Quit Game", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed() == true) {
                subState = 3;
                commandNum = 0;
            }
        }

        // Resume
        textY += gp.getTileSize() * 2;
        g2.drawString("Resume", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed() == true) {
                gp.setGameState(gp.getPlayState());
                commandNum = 0;
            }
        }

        // Full screen check box
        textX = frameX + (int) (gp.getTileSize() * 4.5);
        textY = frameY + gp.getTileSize() * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.isFullScreenOn() == true) {
            g2.fillRect(textX + 4, textY + 4, 17, 17);
        }

        // BGM volume
        textY += gp.getTileSize();
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.getBGM().volumeScale;
        g2.fillRect(textX + 4, textY + 4, volumeWidth - 7, 17);

        // SFX volume
        textY += gp.getTileSize();
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.getSFX().volumeScale;
        g2.fillRect(textX + 4, textY + 4, volumeWidth - 7, 17);

        gp.getConfig().saveConfig();
    }

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.getTileSize();
        int textY = frameY + gp.getTileSize() * 3;

        currentDialogue = "The change will take \neffect after restarting \nthe game";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Resume
        textY = frameY + gp.getTileSize() * 9;
        g2.drawString("Resume", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed() == true) {
                subState = 0;
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.getTileSize();
        g2.drawString(text, textX, textY);

        textX = frameX + gp.getTileSize();
        textY += gp.getTileSize();
        g2.drawString("Move", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Character Screen", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Pause", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Options", textX, textY);

        textX = frameX + gp.getTileSize() * 6;
        textY = frameY + gp.getTileSize() * 2;
        g2.drawString("WASD", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("ENTER", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("F", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("C", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("P", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("ESC", textX, textY);

        // Resume
        textX = frameX + gp.getTileSize();
        textY = frameY + gp.getTileSize() * 9;
        g2.drawString("Resume", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed() == true) {
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.getTileSize();
        int textY = frameY + gp.getTileSize() * 3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.getTileSize() * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed() == true) {
                subState = 0;
                gp.setGameState(gp.getTitleState());
                gp.stopBGM();
                gp.resetGame(true);
            }
        }

        // No
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.getTileSize();
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed() == true) {
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        if (counter == 50) {
            counter = 0;
            gp.setGameState(gp.getPlayState());
            gp.setCurrentMap(gp.geteHandler().tempMap);
            gp.getPlayer().setWorldX(gp.getTileSize() * gp.geteHandler().tempCol);
            gp.getPlayer().setWorldY(gp.getTileSize() * gp.geteHandler().tempRow);
            gp.geteHandler().previousEventX = gp.getPlayer().getWorldX();
            gp.geteHandler().previousEventY = gp.getPlayer().getWorldY();
            gp.changeArea();
        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }

        gp.getKeyH().setEnterPressed(false);
    }

    public void trade_select() {

        npc.setDialogueSet(0);
        drawDialogueScreen();

        // Draw sub window
        int x = gp.getTileSize() * 15;
        int y = gp.getTileSize() * 4;
        int width = gp.getTileSize() * 3;
        int height = (int) (gp.getTileSize() * 3.5);
        drawSubWindow(x, y, width, height);

        // Draw text + cursor
        x += gp.getTileSize();
        y += gp.getTileSize();
        g2.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);
            if (gp.getKeyH().isEnterPressed() == true) {
                subState = 1;
            }
        }

        y += gp.getTileSize();
        g2.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.getKeyH().isEnterPressed() == true) {
                subState = 2;
            }
        }

        y += gp.getTileSize();
        g2.drawString("Leave", x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 24, y);
            if (gp.getKeyH().isEnterPressed() == true) {
                commandNum = 0;
                npc.startDialogue(npc, 1);
            }
        }
    }

    public void trade_buy() {

        // Draw player's inventory
        drawInventory(gp.getPlayer(), false);

        // Draw NPC's inventory
        drawInventory(npc, true);

        // Draw hint window
        int x = gp.getTileSize() * 2;
        int y = gp.getTileSize() * 9;
        int width = gp.getTileSize() * 6;
        int height = gp.getTileSize() * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // Draw player coin window
        x = gp.getTileSize() * 12;
        y = gp.getTileSize() * 9;
        width = gp.getTileSize() * 6;
        height = gp.getTileSize() * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Coins: " + gp.getPlayer().getCoin(), x + 24, y + 60);

        // Draw price window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.getInventory().size()) {
            x = (int) (gp.getTileSize() * 5.5);
            y = (int) (gp.getTileSize() * 5.5);
            width = (int) (gp.getTileSize() * 2.5);
            height = gp.getTileSize();
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.getInventory().get(itemIndex).getPrice();
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.getTileSize() * 8);
            g2.drawString(text, x - 20, y + 34);

            // Buy an item
            if (gp.getKeyH().isEnterPressed() == true) {
                if (npc.getInventory().get(itemIndex).getPrice() > gp.getPlayer().getCoin()) { // CASE: player haven't enough money
                    subState = 0;
                    npc.startDialogue(npc, 2);
                } else {
                    if (gp.getPlayer().canObtainItem(npc.getInventory().get(itemIndex)) == true) {
                        gp.getPlayer().setCoin(gp.getPlayer().getCoin() - npc.getInventory().get(itemIndex).getPrice());
                    } else {
                        subState = 0;
                        npc.startDialogue(npc, 3);
                    }
                }
            }
        }
    }

    public void trade_sell() {
        // Draw player's inventory
        drawInventory(gp.getPlayer(), true);

        // Draw hint window
        int x = gp.getTileSize() * 2;
        int y = gp.getTileSize() * 9;
        int width = gp.getTileSize() * 6;
        int height = gp.getTileSize() * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // Draw player coin window
        x = gp.getTileSize() * 12;
        y = gp.getTileSize() * 9;
        width = gp.getTileSize() * 6;
        height = gp.getTileSize() * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Coins: " + gp.getPlayer().getCoin(), x + 24, y + 60);

        // Draw price window
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.getPlayer().getInventory().size()) {
            x = (int) (gp.getTileSize() * 15.5);
            y = (int) (gp.getTileSize() * 5.5);
            width = (int) (gp.getTileSize() * 2.5);
            height = gp.getTileSize();
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.getPlayer().getInventory().get(itemIndex).getPrice() / 2;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.getTileSize() * 18);
            g2.drawString(text, x - 20, y + 34);

            // Sell an item
            if (gp.getKeyH().isEnterPressed() == true) {
                if (gp.getPlayer().getInventory().get(itemIndex) == gp.getPlayer().getCurrentWeapon()
                        || gp.getPlayer().getInventory().get(itemIndex) == gp.getPlayer().getCurrentShield()) { // Prevent selling equipped items
                    commandNum = 0;
                    subState = 0;
                    npc.startDialogue(npc, 4);
                } else {
                    if (gp.getPlayer().getInventory().get(itemIndex).getAmount() > 1) {
                        gp.getPlayer().getInventory().get(itemIndex).setAmount(gp.getPlayer().getInventory().get(itemIndex).getAmount() - 1);
                    } else {
                        gp.getPlayer().getInventory().remove(itemIndex);
                    }
                    gp.getPlayer().setCoin(gp.getPlayer().getCoin() + price);
                }
            }
        }
    }

    public void drawSleepScreen() {
        counter++;

        if (counter < 120) {
            gp.geteManager().getLighting().filterAlpha = gp.geteManager().getLighting().filterAlpha + 0.01f;
            if (gp.geteManager().getLighting().filterAlpha > 1f) {
                gp.geteManager().getLighting().filterAlpha = 1f;
            }
        }

        if (counter >= 120) {
            gp.geteManager().getLighting().filterAlpha = gp.geteManager().getLighting().filterAlpha - 0.01f;
            if (gp.geteManager().getLighting().filterAlpha <= 0f) {
                gp.geteManager().getLighting().filterAlpha = 0f;
                counter = 0;
                gp.geteManager().getLighting().dayState = gp.geteManager().getLighting().day;
                gp.geteManager().getLighting().dayCounter = 0;
                gp.setGameState(gp.getPlayState());
                gp.getPlayer().getSprite();
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);

        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.getScreenWidth() / 2 - length / 2;

        return x;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;

        return x;
    }
}
