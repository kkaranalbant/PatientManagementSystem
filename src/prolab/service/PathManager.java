/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import prolab.grid.Grid;
import prolab.model.Treasure;
import prolab.model.Location;
import prolab.model.GameObject;
import prolab.model.Obstacle;
import prolab.model.Character;

public class PathManager {

    private static PathManager pathManager;

    private InfoManager infoManager;

    private GameObjectManager gameObjectManager;

    private Grid grid;

    private Node[][] nodeMatrix;

    private List<Node> openList;

    private List<Node> checkedList;

    private Node startNode;

    private Node treasureNode;

    private Node currentNode;

    private boolean goalReached;

    private List<Location> allPath;

    private PathManager() {
        grid = Grid.getGrid();
        gameObjectManager = GameObjectManager.getInstance();
        openList = new ArrayList();
        checkedList = new ArrayList();
        nodeMatrix = new Node[grid.getGridMatrix().length][grid.getGridMatrix().length];
        allPath = new ArrayList();
        infoManager = InfoManager.getInstance();
    }

    public static PathManager getInstance() {
        if (pathManager == null) {
            pathManager = new PathManager();
        }
        return pathManager;
    }

    public List<Location> search(List<Treasure> targets) {
        while (!targets.isEmpty()) {
            Treasure target = findTreasureWithMinimumCost(targets);
            configureNodeMatrix(target);
            while (!goalReached) {
                int row = currentNode.getRow();
                int column = currentNode.getColumn();
                currentNode.setAsChecked();
                checkedList.add(currentNode);
                openList.remove(currentNode);
                if (isValidLocation(row - 1, column)) {
                    openNode(nodeMatrix[row - 1][column]);
                }
                if (isValidLocation(row, column - 1)) {
                    openNode(nodeMatrix[row][column - 1]);
                }
                if (isValidLocation(row + 1, column)) {
                    openNode(nodeMatrix[row + 1][column]);
                }
                if (isValidLocation(row, column + 1)) {
                    openNode(nodeMatrix[row][column + 1]);
                }
                int bestNodeIndex = 0;
                int bestNodeFCost = Integer.MAX_VALUE;

                for (int i = 0; i < openList.size(); i++) {
                    if (openList.get(i).getFCost() < bestNodeFCost) {
                        bestNodeIndex = i;
                        bestNodeFCost = openList.get(i).getFCost();
                    } else if (openList.get(i).getFCost() == bestNodeFCost && openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }

                if (openList.isEmpty()) {
                    continue;
                }

                currentNode = openList.get(bestNodeIndex);

                if (currentNode == treasureNode) {
                    goalReached = true;
                    List<Location> onePath = writePath();
                    allPath.addAll(onePath);
                    changeStartNode();
                    Treasure treasure = gameObjectManager.getTreasureByLocation(treasureNode.getColumn() * grid.getSquareSize(), treasureNode.getRow() * grid.getSquareSize());
                    infoManager.addTreasure(treasure);
                }
            }
            openList.clear();
            checkedList.clear();
            goalReached = false;
        }
        setVisibleObstacles();
        setVisibleEmptyCells();
        return allPath;
    }

    public Node[][] getNodeMatrix() {
        return nodeMatrix;
    }

    private Treasure findTreasureWithMinimumCost(List<Treasure> targets) {
        Treasure treasure = null;
        int minCost = Integer.MAX_VALUE ;
        int characterRow = gameObjectManager.getCharacter().getOriginLocation().getY() / grid.getSquareSize();
        int characterColumn = gameObjectManager.getCharacter().getOriginLocation().getX() / grid.getSquareSize();
        for (Treasure target : targets) {
            int targetRow = target.getOriginLocation().getY() / grid.getSquareSize();
            int targetColumn = target.getOriginLocation().getX() / grid.getSquareSize();
            int currentCost = Math.abs(characterRow - targetRow) + Math.abs(characterColumn - targetColumn) ;
            if (currentCost < minCost) {
                minCost = currentCost ;
                treasure = target ;
            }
        }
        targets.remove(treasure);
        return treasure ;
    }

    /*
    satir ve sutun vererek ilgili node'lari sonradan kullanabilmek icin node matrisi olusturuldu . 
     */ private void configureNodeMatrix(Treasure target) {

        byte[][] gridMatrix = grid.getGridMatrix();
        int targetRow = target.getOriginLocation().getY() / grid.getSquareSize();
        int targetColumn = target.getOriginLocation().getX() / grid.getSquareSize();
        int characterRow = gameObjectManager.getCharacter().getOriginLocation().getY() / grid.getSquareSize();
        int characterColumn = gameObjectManager.getCharacter().getOriginLocation().getX() / grid.getSquareSize();

        for (int i = 0; i < gridMatrix.length; i++) {
            for (int j = 0; j < gridMatrix.length; j++) {
                Node node = new Node(i, j);
                nodeMatrix[i][j] = node;
                if (gridMatrix[i][j] == 1) {
                    nodeMatrix[i][j].obstacle = true;
                    nodeMatrix[i][j].setHCost(Integer.MAX_VALUE);
                    nodeMatrix[i][j].setFCost();
                } else if (gridMatrix[i][j] == 2 && i == targetRow && j == targetColumn) {
                    nodeMatrix[i][j].treasure = true;
                    nodeMatrix[i][j].setGCost(Math.abs(i - characterRow) + Math.abs(j - characterColumn));
                    nodeMatrix[i][j].setFCost();
                    treasureNode = nodeMatrix[i][j];
                } else if (gridMatrix[i][j] == 2) {
                    nodeMatrix[i][j].treasure = false;
                    nodeMatrix[i][j].setGCost(Math.abs(i - characterRow) + Math.abs(j - characterColumn));
                    nodeMatrix[i][j].setHCost(Math.abs(i - targetRow) + Math.abs(j - targetColumn));
                    nodeMatrix[i][j].setFCost();
                } else if (gridMatrix[i][j] == 3) {
                    currentNode = nodeMatrix[i][j];
                    startNode = nodeMatrix[i][j];
                    currentNode.start = true;
                    currentNode.setHCost(Math.abs(targetRow - i) + Math.abs(targetColumn - j));
                    currentNode.setFCost();
                } else {
                    nodeMatrix[i][j].setGCost(Math.abs(i - characterRow) + Math.abs(j - characterColumn));
                    nodeMatrix[i][j].setHCost(Math.abs(i - targetRow) + Math.abs(j - targetColumn));
                    nodeMatrix[i][j].setFCost();
                    nodeMatrix[i][j].emptyCell = true;
                }
            }
        }
    }

    private void changeStartNode() {
        byte[][] gridMatrix = grid.getGridMatrix();
        makeCharacterCellsVisible();
        boolean notFound = true;
        for (int i = 0; i < grid.getGridMatrix().length && notFound; i++) {
            for (int j = 0; j < grid.getGridMatrix().length; j++) {
                if (gridMatrix[i][j] == 3) {
                    gridMatrix[i][j] = 0;
                    Node oldStartingNode = nodeMatrix[i][j];
                    oldStartingNode.start = false;
                    notFound = false;
                    break;
                }
            }
        }
        gridMatrix[currentNode.getRow()][currentNode.getColumn()] = 3;
        gameObjectManager.getCharacter().getOriginLocation().setX(currentNode.getColumn() * grid.getSquareSize());
        gameObjectManager.getCharacter().getOriginLocation().setY(currentNode.getRow() * grid.getSquareSize());

    }

    private boolean isValidLocation(int row, int column) {
        return !isOutOfBoundArea(row, column) && nodeMatrix[row][column].getFCost() != Integer.MAX_VALUE;
    }

    private boolean isOutOfBoundArea(int row, int column) {
        return !(row < grid.getHeight() / grid.getSquareSize() && column < grid.getWidth() / grid.getSquareSize() && row >= 0 && column >= 0);
    }

    private void openNode(Node node) {

        if (node.open == false && node.obstacle == false && node.checked == false) {
            node.setOpen(true);
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private List<Location> writePath() {
        List<Location> path = new ArrayList();
        Node currentNodeCopy = currentNode;
        while (currentNodeCopy != startNode) {
            Location location = new Location(currentNodeCopy.getColumn() * grid.getSquareSize(), currentNodeCopy.getRow() * grid.getSquareSize());
            path.add(location);
            currentNodeCopy = currentNodeCopy.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    private void setVisibleObstacles() {
        for (GameObject object : gameObjectManager.getGameObject()) {
            if (object instanceof Obstacle) {
                for (Location pathUnit : allPath) {
                    double leftTopCornerDistance = 0;
                    double leftTopCornerDistance1 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() - pathUnit.getX()) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() - object.getOriginLocation().getY()) / grid.getSquareSize()), 2));
                    double leftTopCornerDistance2 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() - (pathUnit.getX() + 2 * grid.getSquareSize())) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() - object.getOriginLocation().getY()) / grid.getSquareSize()), 2));
                    double leftTopCornerDistance3 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() - pathUnit.getX()) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() + 2 * grid.getSquareSize() - object.getOriginLocation().getY()) / grid.getSquareSize()), 2));
                    double leftTopCornerDistance4 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() - (pathUnit.getX() + 2 * grid.getSquareSize())) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() + 2 * grid.getSquareSize() - object.getOriginLocation().getY()) / grid.getSquareSize()), 2));
                    leftTopCornerDistance1 = Math.min(leftTopCornerDistance1, leftTopCornerDistance2);
                    leftTopCornerDistance3 = Math.min(leftTopCornerDistance3, leftTopCornerDistance4);
                    leftTopCornerDistance = Math.min(leftTopCornerDistance1, leftTopCornerDistance3);
                    if (leftTopCornerDistance <= 3 * Math.sqrt(2)) {
                        ((Obstacle) object).setVisible(true);
                        infoManager.addObstacle((Obstacle) (object));
                        break;
                    }
                    double rightTopCornerDistance = 0;
                    double rightTopCornerDistance1 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() + object.getDimension().getWidth() * grid.getSquareSize() - (pathUnit.getX() + 2 * grid.getSquareSize())) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() - object.getOriginLocation().getY()) / grid.getSquareSize()), 2));
                    double rightTopCornerDistance2 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() + object.getDimension().getWidth() * grid.getSquareSize() - pathUnit.getX()) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() + 2 * grid.getSquareSize() - object.getOriginLocation().getY()) / grid.getSquareSize()), 2));
                    double rightTopCornerDistance3 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() + object.getDimension().getWidth() * grid.getSquareSize() - (pathUnit.getX() + 2 * grid.getSquareSize())) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() + 2 * grid.getSquareSize() - object.getOriginLocation().getY()) / grid.getSquareSize()), 2));
                    double rightTopCornerDistance4 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() + object.getDimension().getWidth() * grid.getSquareSize() - pathUnit.getX()) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() - object.getOriginLocation().getY()) / grid.getSquareSize()), 2));
                    rightTopCornerDistance1 = Math.min(rightTopCornerDistance1, rightTopCornerDistance2);
                    rightTopCornerDistance3 = Math.min(rightTopCornerDistance3, rightTopCornerDistance4);
                    rightTopCornerDistance = Math.min(rightTopCornerDistance1, rightTopCornerDistance3);
                    if (rightTopCornerDistance <= 3 * Math.sqrt(2)) {
                        ((Obstacle) object).setVisible(true);
                        infoManager.addObstacle((Obstacle) (object));
                        break;
                    }
                    double leftBottomCornerDistance = 0;
                    double leftBottomCornerDistance1 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() - (pathUnit.getX() + 2 * grid.getSquareSize())) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() - (object.getOriginLocation().getY() + object.getDimension().getHeight() * grid.getSquareSize())) / grid.getSquareSize()), 2));
                    double leftBottomCornerDistance2 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() - (pathUnit.getX() + 2 * grid.getSquareSize())) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() + 2 * grid.getSquareSize() - (object.getOriginLocation().getY() + object.getDimension().getHeight() * grid.getSquareSize())) / grid.getSquareSize()), 2));
                    double leftBottomCornerDistance3 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() - pathUnit.getX()) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() - (object.getOriginLocation().getY() + object.getDimension().getHeight() * grid.getSquareSize())) / grid.getSquareSize()), 2));
                    double leftBottomCornerDistance4 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() - pathUnit.getX()) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() + 2 * grid.getSquareSize() - (object.getOriginLocation().getY() + object.getDimension().getHeight() * grid.getSquareSize())) / grid.getSquareSize()), 2));
                    leftBottomCornerDistance1 = Math.min(leftBottomCornerDistance1, leftBottomCornerDistance2);
                    leftBottomCornerDistance3 = Math.min(leftBottomCornerDistance3, leftBottomCornerDistance4);
                    leftBottomCornerDistance = Math.min(leftBottomCornerDistance1, leftBottomCornerDistance3);
                    if (leftBottomCornerDistance <= 3 * Math.sqrt(2)) {
                        ((Obstacle) object).setVisible(true);
                        infoManager.addObstacle((Obstacle) (object));
                        break;
                    }
                    double rightBottomCornerDistance = 0;
                    double rightBottomCornerDistance1 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() + object.getDimension().getWidth() * grid.getSquareSize() - (pathUnit.getX() + 2 * grid.getSquareSize())) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() - (object.getOriginLocation().getY() + object.getDimension().getHeight() * grid.getSquareSize())) / grid.getSquareSize()), 2));
                    double rightBottomCornerDistance2 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() + object.getDimension().getWidth() * grid.getSquareSize() - (pathUnit.getX() + 2 * grid.getSquareSize())) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() + 2 * grid.getSquareSize() - (object.getOriginLocation().getY() + object.getDimension().getHeight() * grid.getSquareSize())) / grid.getSquareSize()), 2));
                    double rightBottomCornerDistance3 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() + object.getDimension().getWidth() * grid.getSquareSize() - pathUnit.getX()) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() - (object.getOriginLocation().getY() + 2 * grid.getSquareSize() + object.getDimension().getHeight() * grid.getSquareSize())) / grid.getSquareSize()), 2));
                    double rightBottomCornerDistance4 = Math.sqrt((Math.pow(Math.abs((object.getOriginLocation().getX() + object.getDimension().getWidth() * grid.getSquareSize() - pathUnit.getX()) / grid.getSquareSize()), 2.0)) + Math.pow(Math.abs((pathUnit.getY() - (object.getOriginLocation().getY() + object.getDimension().getHeight() * grid.getSquareSize())) / grid.getSquareSize()), 2));
                    rightBottomCornerDistance1 = Math.min(rightBottomCornerDistance1, rightBottomCornerDistance2);
                    rightBottomCornerDistance3 = Math.min(rightBottomCornerDistance3, rightBottomCornerDistance4);
                    rightBottomCornerDistance = Math.min(rightBottomCornerDistance1, rightBottomCornerDistance3);
                    if (rightBottomCornerDistance <= 3 * Math.sqrt(2)) {
                        ((Obstacle) object).setVisible(true);
                        infoManager.addObstacle((Obstacle) (object));
                        break;
                    }
                }

            }
        }
    }

    private void setVisibleEmptyCells() {
        Character character = gameObjectManager.getCharacter();
        for (Location path : allPath) {
            int row = path.getY() / grid.getSquareSize();
            int column = path.getX() / grid.getSquareSize();
            for (int i = 1; i <= character.getRange(); i++) {
                if (!isOutOfBoundArea(row - i, column) && nodeMatrix[row - i][column].emptyCell) {
                    nodeMatrix[row - i][column].visible = true;
                }
                if (!isOutOfBoundArea(row, column - i) && nodeMatrix[row][column - i].emptyCell) {
                    nodeMatrix[row][column - i].visible = true;
                }
                if (!isOutOfBoundArea(row + i, column) && nodeMatrix[row + i][column].emptyCell) {
                    nodeMatrix[row + i][column].visible = true;
                }
                if (!isOutOfBoundArea(row, column + i) && nodeMatrix[row][column + i].emptyCell) {
                    nodeMatrix[row][column + i].visible = true;
                }
                if (!isOutOfBoundArea(row + i, column + i) && nodeMatrix[row + i][column + i].emptyCell) {
                    nodeMatrix[row + i][column + i].visible = true;
                }
                if (!isOutOfBoundArea(row - i, column - i) && nodeMatrix[row - i][column - i].emptyCell) {
                    nodeMatrix[row - i][column - i].visible = true;
                }
            }
        }
    }

    private void makeCharacterCellsVisible() {
        Character character = gameObjectManager.getCharacter();
        int originRow = character.getOriginLocation().getY() / grid.getSquareSize();
        int originColumn = character.getOriginLocation().getX() / grid.getSquareSize();
        for (int i = 0; i < character.getDimension().getHeight(); i++) {
            for (int j = 0; j < character.getDimension().getWidth(); j++) {
                nodeMatrix[originRow + i][originColumn + j].visible = true;
            }
        }
    }

    public static class Node {

        private int row;
        private int column;

        private int hCost;
        private int gCost;
        private int fCost;

        private boolean open;
        private boolean checked;
        private boolean obstacle;
        private boolean treasure;
        private boolean start;

        private boolean emptyCell;
        private boolean visible;

        private Node parent;

        public Node(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public int getHCost() {
            return hCost;
        }

        public void setHCost(int hCost) {
            this.hCost = hCost;
        }

        public int getGCost() {
            return gCost;
        }

        public void setGCost(int gCost) {
            this.gCost = gCost;
        }

        public int getFCost() {
            return fCost;
        }

        public void setFCost() {
            fCost = gCost + hCost;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setAsChecked() {
            if (start == false && treasure == false) {
                checked = true;
            }
        }

        public boolean isObstacle() {
            return obstacle;
        }

        public void setObstacle(boolean obstacle) {
            this.obstacle = obstacle;
        }

        public boolean isTreasure() {
            return treasure;
        }

        public void setTreasure(boolean treasure) {
            this.treasure = treasure;
        }

        public boolean isStart() {
            return start;
        }

        public void setStart(boolean start) {
            this.start = start;
        }

        public boolean isNotVisibleEmptyCell() {
            return !visible && emptyCell;
        }

    }

}
