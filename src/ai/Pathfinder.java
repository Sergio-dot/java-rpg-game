package ai;

import java.util.ArrayList;

import main.GamePanel;

public class Pathfinder {

    GamePanel gp;
    private Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;
    private int step = 0;

    public Pathfinder(GamePanel gp) {

        this.gp = gp;

        instantiateNodes();
    }

    private void instantiateNodes() {

        node = new Node[gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        int col = 0;
        int row = 0;

        while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {

            node[col][row] = new Node(col, row);

            col++;
            if (col == gp.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {

        int col = 0;
        int row = 0;

        while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {

            // Reset open, checked and solid state
            node[col][row].setOpen(false);
            node[col][row].setChecked(false);
            node[col][row].setSolid(false);

            col++;
            if (col == gp.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }

        // Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {

        resetNodes();

        // Set start and goal node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {

            // Set solid node
            // Check tiles
            int tileNum = gp.getTileM().mapTileNum[gp.getCurrentMap()][col][row];

            if (gp.getTileM().tile[tileNum].collision == true) {
                node[col][row].setSolid(true);
            }

            // Check interactive tiles
            for (int i = 0; i < gp.getiTile()[1].length; i++) {

                if (gp.getiTile()[gp.getCurrentMap()][i] != null && gp.getiTile()[gp.getCurrentMap()][i].destructible == true) {
                    int itCol = gp.getiTile()[gp.getCurrentMap()][i].getWorldX() / gp.getTileSize();
                    int itRow = gp.getiTile()[gp.getCurrentMap()][i].getWorldY() / gp.getTileSize();
                    node[itCol][itRow].setSolid(true);
                }
            }

            // Set cost
            getCost(node[col][row]);

            col++;
            if (col == gp.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node) {

        // Get gCost
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());
        node.setgCost(xDistance + yDistance);

        // Get hCost
        xDistance = Math.abs(node.getCol() - goalNode.getCol());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());
        node.sethCost(xDistance + yDistance);

        // Get fCost
        node.setfCost(node.getgCost() + node.gethCost());
    }

    public boolean search() {

        while (goalReached == false && step < 500) {

            int col = currentNode.getCol();
            int row = currentNode.getRow();

            // Check the current node
            currentNode.setChecked(true);
            openList.remove(currentNode);

            // Open the up node
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }

            // Open the left node
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }

            // Open the down node
            if (row + 1 < gp.getMaxWorldRow()) {
                openNode(node[col][row + 1]);
            }

            // Open the right node
            if (col + 1 < gp.getMaxWorldCol()) {
                openNode(node[col + 1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {

                // Check if this node's F cost is better
                if (openList.get(i).getfCost() < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).getfCost();
                } // If F cost is equal, check the G cost
                else if (openList.get(i).getfCost() == bestNodefCost) {
                    if (openList.get(i).getgCost() < openList.get(bestNodeIndex).getgCost()) {
                        bestNodeIndex = i;
                    }
                }
            }

            // If there's no node in the openList, end the loop
            if (openList.isEmpty()) {
                break;
            }

            // After the loop, openList[bestNodeIndex] is the next step (= currentNode)
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackPath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node) {

        if (node.isOpen() == false && node.isChecked() == false && node.isSolid() == false) {
            node.setOpen(true);
            node.setParent(currentNode);
            openList.add(node);
        }
    }

    public void trackPath() {

        Node current = goalNode;

        while (current != startNode) {

            pathList.add(0, current);
            current = current.getParent();
        }
    }

    public void clearPath() {

        pathList.clear();
    }
}
