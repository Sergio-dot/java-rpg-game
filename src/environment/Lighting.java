package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;

    // Day state
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    public Lighting(GamePanel gp) {
        this.gp = gp;
        setLightSource();
    }

    private void setLightSource() {
        // Create a buffered image
        darknessFilter = new BufferedImage(gp.getScreenWidth(), gp.getScreenHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if (gp.getPlayer().getCurrentLight() == null) {
            g2.setColor(new Color(0, 0, 0.1f, 0.85f));
        } else {
            // Get the center x and y of the light circle
            int centerX = gp.getPlayer().getScreenX() + (gp.getTileSize()) / 2;
            int centerY = gp.getPlayer().getScreenY() + (gp.getTileSize()) / 2;

            // Create a gradation effect within the light circle
            Color color[] = new Color[12];
            float fraction[] = new float[12];

            color[0] = new Color(0, 0, 0.1f, 0.1f);
            color[1] = new Color(0, 0, 0.1f, 0.42f);
            color[2] = new Color(0, 0, 0.1f, 0.52f);
            color[3] = new Color(0, 0, 0.1f, 0.61f);
            color[4] = new Color(0, 0, 0.1f, 0.69f);
            color[5] = new Color(0, 0, 0.1f, 0.76f);
            color[6] = new Color(0, 0, 0.1f, 0.82f);
            color[7] = new Color(0, 0, 0.1f, 0.87f);
            color[8] = new Color(0, 0, 0.1f, 0.91f);
            color[9] = new Color(0, 0, 0.1f, 0.92f);
            color[10] = new Color(0, 0, 0.1f, 0.93f);
            color[11] = new Color(0, 0, 0.1f, 0.94f);

            fraction[0] = 0f;
            fraction[1] = 0.4f;
            fraction[2] = 0.5f;
            fraction[3] = 0.6f;
            fraction[4] = 0.65f;
            fraction[5] = 0.7f;
            fraction[6] = 0.75f;
            fraction[7] = 0.8f;
            fraction[8] = 0.85f;
            fraction[9] = 0.9f;
            fraction[10] = 0.95f;
            fraction[11] = 1f;

            // Create a gradation paint setting
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.getPlayer().getCurrentLight().getLightRadius(), fraction, color);

            // Set the gradient data on g2
            g2.setPaint(gPaint);
        }

        // Draw the black rectangle on the screen
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        g2.dispose();
    }

    public void resetDay() {
        dayState = day;
        filterAlpha = 0f;
    }

    public void update() {
        if (gp.getPlayer().isLightUpdated() == true) {
            setLightSource();
            gp.getPlayer().setLightUpdated(false);
        }

        // Check the state of the day
        if (dayState == day) { // Day
            dayCounter++;

            if (dayCounter > 600) { // 36000 = 10 minutes
                dayState = dusk;
                dayCounter = 0;
            }
        }

        if (dayState == dusk) { // Dusk
            filterAlpha += 0.001f;

            if (filterAlpha > 1f) {
                filterAlpha = 1f;
                dayState = night;
            }
        }

        if (dayState == night) { // Night
            dayCounter++;

            if (dayCounter > 600) {
                dayState = dawn;
                dayCounter = 0;
            }
        }

        if (dayState == dawn) { // Dawn
            filterAlpha -= 0.001f;

            if (filterAlpha < 0f) {
                filterAlpha = 0f;
                dayState = day;
            }
        }
    }

    public void draw(Graphics2D g2) {

        // If currentArea is World Map, set the current filterAlpha to g2
        if (gp.getCurrentArea() == gp.getOutside()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        }

        // If currentArea is not World Map, do not apply filterAlpha and alpha is 1f 
        if (gp.getCurrentArea() == gp.getOutside() || gp.getCurrentArea() == gp.getDungeon()) {
            g2.drawImage(darknessFilter, 0, 0, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Debug
        String debugText = "";

        switch (dayState) {
            case day ->
                debugText = "Day";
            case dusk ->
                debugText = "Dusk";
            case night ->
                debugText = "Night";
            case dawn ->
                debugText = "Dawn";
        }

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50F));
        g2.drawString(debugText, 800, 500);
    }
}
