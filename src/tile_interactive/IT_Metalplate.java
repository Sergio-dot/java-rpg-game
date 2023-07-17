package tile_interactive;

import main.GamePanel;

public class IT_Metalplate extends InteractiveTile {

    GamePanel gp;
    public static final String itName = "Metal Plate";

    public IT_Metalplate(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.setWorldX(gp.getTileSize() * col);
        this.setWorldY(gp.getTileSize() * row);

        setName(itName);
        down1 = setup("/tiles_interactive/metalplate", gp.getTileSize(), gp.getTileSize());

        getSolidArea().x = 0;
        getSolidArea().y = 0;
        getSolidArea().width = 0;
        getSolidArea().height = 0;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
    }

}
