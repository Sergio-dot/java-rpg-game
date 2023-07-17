package environment;

import java.awt.Graphics2D;

import main.GamePanel;

public class EnvironmentManager {

    GamePanel gp;
    private Lighting lighting;

    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {
        setLighting(new Lighting(gp));
    }

    public void update() {
        getLighting().update();
    }

    public void draw(Graphics2D g2) {
        getLighting().draw(g2);
    }

    public Lighting getLighting() {
        return lighting;
    }

    public void setLighting(Lighting lighting) {
        this.lighting = lighting;
    }

}
