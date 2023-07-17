package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile {

    GamePanel gp;
    public static final String itName = "Trunk";

    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.setWorldX(gp.getTileSize() * col);
        this.setWorldY(gp.getTileSize() * row);

        setName(itName);
        down1 = setup("/tiles_interactive/trunk", gp.getTileSize(), gp.getTileSize());

        getSolidArea().x = 0;
        getSolidArea().y = 0;
        getSolidArea().width = 0;
        getSolidArea().height = 0;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
    }

}
