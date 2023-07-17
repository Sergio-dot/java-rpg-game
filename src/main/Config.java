package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            // Full screen
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"))) {
                // Full screen
                if (gp.isFullScreenOn() == true) {
                    bw.write("Full screen = 1");
                }
                if (gp.isFullScreenOn() == false) {
                    bw.write("Full screen = 0");
                }
                
                bw.newLine();
                
                // BGM volume
                bw.write(String.valueOf(gp.getBGM().volumeScale));
                bw.newLine();
                
                // SFX volume
                bw.write(String.valueOf(gp.getSFX().volumeScale));
                bw.newLine();
            }
        } catch (IOException e) {
        }
    }

    public void loadConfig() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
                String s = br.readLine();
                
                // Full screen
                if (s.equals("Full screen = 1")) {
                    gp.setFullScreenOn(true);
                }
                if (s.equals("Full screen = 0")) {
                    gp.setFullScreenOn(false);
                }
                
                // BGM volume
                s = br.readLine();
                gp.getBGM().volumeScale = Integer.parseInt(s);
                
                // SFX volume
                s = br.readLine();
                gp.getSFX().volumeScale = Integer.parseInt(s);
            }
        } catch (IOException | NumberFormatException e) {
        }
    }
}
