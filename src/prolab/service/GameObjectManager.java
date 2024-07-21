/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import prolab.grid.Grid;
import prolab.model.GameObject;
import prolab.model.ImmobileObstacle;
import prolab.model.Location;
import prolab.model.Treasure;
import prolab.model.Character;
import prolab.model.Direction;
import prolab.model.MobileObstacle;
import prolab.model.ObjectDimension;

/**
 *
 * @author kaan
 */
public class GameObjectManager {

    private static GameObjectManager gameObjectManager;

    private static final int OBSTACLE_ORIGIN;

    private static final int OBSTACLE_BOUND;

    private static final int TREASURE_ORIGIN;

    private static final int TREASURE_BOUND;

    private final GameObjectFactory gameObjectFactory;

    private final List<GameObject> gameObjects;


    private Character character;

    private Random random;

    static {
        OBSTACLE_ORIGIN = 2;
        OBSTACLE_BOUND = 4;
        TREASURE_ORIGIN = 5;
        TREASURE_BOUND = 7;
    }

    private GameObjectManager() {
        gameObjectFactory = GameObjectFactory.getInstance();
        random = new Random();
        gameObjects = new LinkedList();
    }

    public static GameObjectManager getInstance() {
        if (gameObjectManager == null) {
            gameObjectManager = new GameObjectManager();
        }
        return gameObjectManager;
    }

    public List<GameObject> createGameObjectsRandomly(String characterName, Grid grid) throws IOException {
        createCharacter(characterName, grid);
        createTreasures(grid);
        createImmobileObjects(grid);
        createMobileObjects(grid);
        return gameObjects;
    }

    public List<GameObject> getGameObject() {
        return gameObjects;
    }

    public Character getCharacter() {
        if (character == null) {
            for (GameObject object : gameObjects) {
                if (object instanceof Character) {
                    character = (Character) object;
                }
            }
        }
        return character;
    }


    public List<Treasure> getTreasureList() {
        List<Treasure> treasures = new ArrayList();
        for (GameObject object : gameObjects) {
            if (object instanceof Treasure) {
                treasures.add((Treasure) object);
            }
        }
        return treasures;
    }

    public List<MobileObstacle> getMobileObstacles() {
        List<MobileObstacle> mobileObstacles = new ArrayList();
        for (GameObject object : gameObjects) {
            if (object instanceof MobileObstacle) {
                mobileObstacles.add((MobileObstacle) object);
            }
        }
        return mobileObstacles;
    }

    public void clearObjects(Grid grid) {
        gameObjects.clear();
        byte[][] gridMatrix = grid.getGridMatrix();
        for (int i = 0; i < gridMatrix.length; i++) {
            for (int j = 0; j < gridMatrix.length; j++) {
                gridMatrix[i][j] = 0;
            }
        }
    }

    public Treasure getTreasureByLocation(int x, int y) {
        Treasure treasure = null;
        for (GameObject object : gameObjects) {
            if (object instanceof Treasure && object.getOriginLocation().getX() == x && object.getOriginLocation().getY() == y) {
                treasure = (Treasure) object;
            }
        }
        return treasure;
    }

    private void createCharacter(String name, Grid grid) throws IOException {
        Location location = createRandomOriginLocation(grid, ObjectDimension.CHARACTER);
        Character character = (Character) gameObjectFactory.createCharacter(name, location);
        gameObjects.add(character);
        updateGridMatrixForCharacter(grid, location);
    }

    private void createTreasures(Grid grid) throws IOException {
        createDiamondTreasure(grid);
        createGoldTreasure(grid);
        createSilverTreasure(grid);
        createCopperTreasure(grid);
    }

    private void createImmobileObjects(Grid grid) throws IOException {
        createSummerSingleTree(grid);
        createWinterSingleTree(grid);
        createSummerJungle(grid);
        createWinterJungle(grid);
        createSummerSmallStone(grid);
        createWinterSmallStone(grid);
        createSummerWall(grid);
        createWinterWall(grid);
        createSummerLake(grid);
        createWinterLake(grid);
        createSummerRock(grid);
        createWinterRock(grid);
        createSummerRawMountains(grid);
        createWinterRawMountains(grid);
        createSummerMountain(grid);
        createWinterMountain(grid);
    }

    private void createMobileObjects(Grid grid) throws IOException {
        createBee(grid);
        createBird(grid);
    }

    private void createDiamondTreasure(Grid grid) throws IOException {
        int diamondTreasureNumber = random.nextInt(TREASURE_ORIGIN, TREASURE_BOUND);
        for (int i = 1; i <= diamondTreasureNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.TREASURE);
            Treasure treasure = (Treasure) gameObjectFactory.createDiamondTreasure(location);
            gameObjects.add(treasure);
            updateGridMatrixForTreasure(grid, location);
        }
    }

    private void createGoldTreasure(Grid grid) throws IOException {
        int goldTreasureNumber = random.nextInt(TREASURE_ORIGIN, TREASURE_BOUND);
        for (int i = 1; i <= goldTreasureNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.TREASURE);
            Treasure treasure = (Treasure) gameObjectFactory.createGoldTreasure(location);
            gameObjects.add(treasure);
            updateGridMatrixForTreasure(grid, location);
        }
    }

    private void createSilverTreasure(Grid grid) throws IOException {
        int silverTreasureNumber = random.nextInt(TREASURE_ORIGIN, TREASURE_BOUND);
        for (int i = 1; i <= silverTreasureNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.TREASURE);
            Treasure treasure = (Treasure) gameObjectFactory.createSilverTreasure(location);
            gameObjects.add(treasure);
            updateGridMatrixForTreasure(grid, location);
        }
    }

    private void createCopperTreasure(Grid grid) throws IOException {
        int copperTreasureNumber = random.nextInt(TREASURE_ORIGIN, TREASURE_BOUND);
        for (int i = 1; i <= copperTreasureNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.TREASURE);
            Treasure treasure = (Treasure) gameObjectFactory.createSilverTreasure(location);
            gameObjects.add(treasure);
            updateGridMatrixForTreasure(grid, location);
        }
    }

    private void createSummerSingleTree(Grid grid) throws IOException {
        int treeNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= treeNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.SINGLE_TREE);
            while (!isValidSummerLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.SINGLE_TREE);
            }
            ImmobileObstacle summerTree = (ImmobileObstacle) gameObjectFactory.createSummerTree(location);
            gameObjects.add(summerTree);
            updateGridMatrixForObstacle(grid, summerTree);
        }
    }

    private void createWinterSingleTree(Grid grid) throws IOException {
        int treeNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= treeNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.SINGLE_TREE);
            while (!isValidWinterLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.SINGLE_TREE);
            }
            ImmobileObstacle winterTree = (ImmobileObstacle) gameObjectFactory.createWinterTree(location);
            gameObjects.add(winterTree);
            updateGridMatrixForObstacle(grid, winterTree);
        }
    }

    private void createSummerJungle(Grid grid) throws IOException {
        int jungleNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= jungleNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.JUNGLE);
            while (!isValidSummerLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.JUNGLE);
            }
            ImmobileObstacle summerJungle = (ImmobileObstacle) gameObjectFactory.createSummerJungle(location);
            gameObjects.add(summerJungle);
            updateGridMatrixForObstacle(grid, summerJungle);
        }
    }

    private void createWinterJungle(Grid grid) throws IOException {
        int jungleNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= jungleNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.JUNGLE);
            while (!isValidWinterLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.JUNGLE);
            }
            ImmobileObstacle winterJungle = (ImmobileObstacle) gameObjectFactory.createWinterJungle(location);
            gameObjects.add(winterJungle);
            updateGridMatrixForObstacle(grid, winterJungle);
        }
    }

    private void createSummerSmallStone(Grid grid) throws IOException {
        int stoneNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= stoneNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.SMALL_STONE);
            while (!isValidSummerLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.SMALL_STONE);
            }
            ImmobileObstacle summerSmallStone = (ImmobileObstacle) gameObjectFactory.createSummerCobbleStone(location);
            gameObjects.add(summerSmallStone);
            updateGridMatrixForObstacle(grid, summerSmallStone);
        }
    }

    private void createWinterSmallStone(Grid grid) throws IOException {
        int stoneNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= stoneNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.SMALL_STONE);
            while (!isValidWinterLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.SMALL_STONE);
            }
            ImmobileObstacle winterCobbleStone = (ImmobileObstacle) gameObjectFactory.createWinterCobbleStone(location);
            gameObjects.add(winterCobbleStone);
            updateGridMatrixForObstacle(grid, winterCobbleStone);
        }
    }

    private void createSummerRock(Grid grid) throws IOException {
        int rockNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= rockNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.ROCK);
            while (!isValidSummerLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.ROCK);
            }
            ImmobileObstacle summerRock = (ImmobileObstacle) gameObjectFactory.createSummerRock(location);
            gameObjects.add(summerRock);
            updateGridMatrixForObstacle(grid, summerRock);
        }
    }

    private void createWinterRock(Grid grid) throws IOException {
        int rockNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= rockNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.ROCK);
            while (!isValidWinterLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.ROCK);
            }
            ImmobileObstacle winterRock = (ImmobileObstacle) gameObjectFactory.createWinterRock(location);
            gameObjects.add(winterRock);
            updateGridMatrixForObstacle(grid, winterRock);
        }
    }

    private void createSummerMountain(Grid grid) throws IOException {
        int mountain = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= mountain; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.MOUNTAIN);
            while (!isValidSummerLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.MOUNTAIN);
            }
            ImmobileObstacle summerMountain = (ImmobileObstacle) gameObjectFactory.createSummerMountain(location);
            gameObjects.add(summerMountain);
            updateGridMatrixForObstacle(grid, summerMountain);
        }
    }

    private void createWinterMountain(Grid grid) throws IOException {
        int mountain = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= mountain; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.MOUNTAIN);
            while (!isValidWinterLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.MOUNTAIN);
            }
            ImmobileObstacle winterMountain = (ImmobileObstacle) gameObjectFactory.createWinterMountain(location);
            gameObjects.add(winterMountain);
            updateGridMatrixForObstacle(grid, winterMountain);
        }
    }

    private void createSummerWall(Grid grid) throws IOException {
        int wall = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= wall; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.WALL);
            while (!isValidSummerLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.WALL);
            }
            ImmobileObstacle summerWall = (ImmobileObstacle) gameObjectFactory.createSummerWall(location);
            gameObjects.add(summerWall);
            updateGridMatrixForObstacle(grid, summerWall);
        }
    }

    private void createWinterWall(Grid grid) throws IOException {
        int wall = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= wall; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.WALL);
            while (!isValidWinterLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.WALL);
            }
            ImmobileObstacle winterWall = (ImmobileObstacle) gameObjectFactory.createWinterWall(location);
            gameObjects.add(winterWall);
            updateGridMatrixForObstacle(grid, winterWall);
        }
    }

    private void createSummerRawMountains(Grid grid) throws IOException {
        int rowMountainsNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= rowMountainsNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.ROW_MOUNTAINS);
            while (!isValidSummerLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.ROW_MOUNTAINS);
            }
            ImmobileObstacle summerRowMountain = (ImmobileObstacle) gameObjectFactory.createSummerRowMountain(location);
            gameObjects.add(summerRowMountain);
            updateGridMatrixForObstacle(grid, summerRowMountain);
        }
    }

    private void createWinterRawMountains(Grid grid) throws IOException {
        int rowMountainsNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= rowMountainsNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.ROW_MOUNTAINS);
            while (!isValidWinterLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.ROW_MOUNTAINS);
            }
            ImmobileObstacle winterRowMountain = (ImmobileObstacle) gameObjectFactory.createWinterRowMountain(location);
            gameObjects.add(winterRowMountain);
            updateGridMatrixForObstacle(grid, winterRowMountain);
        }
    }

    private void createSummerLake(Grid grid) throws IOException {
        int treeNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= treeNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.LAKE);
            while (!isValidSummerLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.LAKE);
            }
            ImmobileObstacle summerLake = (ImmobileObstacle) gameObjectFactory.createSummerLake(location);
            gameObjects.add(summerLake);
            updateGridMatrixForObstacle(grid, summerLake);
        }
    }

    private void createWinterLake(Grid grid) throws IOException {
        int treeNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= treeNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.LAKE);
            while (!isValidWinterLocation(grid, location)) {
                location = createRandomOriginLocation(grid, ObjectDimension.LAKE);
            }
            ImmobileObstacle winterLake = (ImmobileObstacle) gameObjectFactory.createWinterLake(location);
            gameObjects.add(winterLake);
            updateGridMatrixForObstacle(grid, winterLake);
        }
    }

    private void createBee(Grid grid) throws IOException {
        int beeNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= beeNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.BEE);
            while (!isValidLocationForMobileObstacles(location, ObjectDimension.BEE, Direction.LEFT_RIGHT, grid, (byte) 3)) {
                location = createRandomOriginLocation(grid, ObjectDimension.BEE);
            }
            MobileObstacle bee = (MobileObstacle) gameObjectFactory.createBee(location);
            gameObjects.add(bee);
            updateGridMatrixForMobileObstacle(grid, bee);
        }
    }

    private void createBird(Grid grid) throws IOException {
        int birdNumber = random.nextInt(OBSTACLE_ORIGIN, OBSTACLE_BOUND);
        for (int i = 1; i <= birdNumber; i++) {
            Location location = createRandomOriginLocation(grid, ObjectDimension.BIRD);
            while (!isValidLocationForMobileObstacles(location, ObjectDimension.BIRD, Direction.UP_DOWN, grid, (byte) 5)) {
                location = createRandomOriginLocation(grid, ObjectDimension.BIRD);
            }
            MobileObstacle bird = (MobileObstacle) gameObjectFactory.createBird(location);
            gameObjects.add(bird);
            updateGridMatrixForMobileObstacle(grid, bird);
        }
    }

    private Location createRandomOriginLocation(Grid grid, ObjectDimension objectDimension) {
        int x = random.nextInt(0, grid.getWidth());
        int y = random.nextInt(0, grid.getHeight());
        Location location = new Location(x, y);
        while (!isValidLocation(location, objectDimension, grid) || location.getX() % grid.getSquareSize() != 0 || location.getY() % grid.getSquareSize() != 0) {
            x = random.nextInt(0, grid.getWidth());
            y = random.nextInt(0, grid.getHeight());
            location.setX(x);
            location.setY(y);
        }
        return location;
    }

    private boolean isValidLocation(Location location, ObjectDimension objectDimension, Grid grid) {
        byte[][] gridMatrix = grid.getGridMatrix();
        int originColumn = location.getX() / grid.getSquareSize();
        int boundColumn = (location.getX() + objectDimension.getWidth() * grid.getSquareSize() + ObjectDimension.CHARACTER.getWidth() * grid.getSquareSize()) / grid.getSquareSize();
        int originRow = location.getY() / grid.getSquareSize();
        int boundRow = (location.getY() + (objectDimension.getHeight() * grid.getSquareSize() + ObjectDimension.CHARACTER.getHeight() * grid.getSquareSize())) / grid.getSquareSize();
        int boundX = boundColumn * grid.getSquareSize();
        int boundY = boundRow * grid.getSquareSize();
        if (isOutOfArea(location.getX(), location.getY(), boundX, boundY, grid)) {
            return false;
        }
        for (int i = originRow; i <= boundRow; i++) {
            for (int j = originColumn; j <= boundColumn; j++) {
                if (gridMatrix[i][j] != 0) { // Eğer bu konumda başka bir şey varsa
                    return false; // Geçersiz konum, false döndür
                }
            }
        }
        return true; // Geçerli konum, true döndür
    }

    private boolean isValidLocationForMobileObstacles(Location location, ObjectDimension objectDimension, Direction direction, Grid grid, byte range) {
        byte[][] gridMatrix = grid.getGridMatrix();
        int characterWidth = ObjectDimension.CHARACTER.getWidth();
        int characterHeight = ObjectDimension.CHARACTER.getHeight();

        int originColumn, boundColumn, originRow, boundRow;

        if (direction.equals(Direction.LEFT_RIGHT)) {
            originColumn = (location.getX() - (range * grid.getSquareSize() + objectDimension.getWidth() * grid.getSquareSize() + characterWidth * grid.getSquareSize())) / grid.getSquareSize();
            boundColumn = (location.getX() + (range * grid.getSquareSize() + objectDimension.getWidth() * grid.getSquareSize() + characterWidth * grid.getSquareSize())) / grid.getSquareSize();
            originRow = location.getY() / grid.getSquareSize();
            boundRow = (location.getY() + (objectDimension.getHeight() * grid.getSquareSize() + characterHeight * grid.getSquareSize())) / grid.getSquareSize();
        } else {
            originColumn = location.getX() / grid.getSquareSize();
            boundColumn = (location.getX() + objectDimension.getWidth() * grid.getSquareSize() + characterWidth * grid.getSquareSize()) / grid.getSquareSize();
            originRow = (location.getY() - (range * grid.getSquareSize() + objectDimension.getHeight() * grid.getSquareSize() + characterHeight * grid.getSquareSize())) / grid.getSquareSize();
            boundRow = (location.getY() + (range * grid.getSquareSize() + objectDimension.getHeight() * grid.getSquareSize() + characterHeight * grid.getSquareSize())) / grid.getSquareSize();
        }

        if (isOutOfArea(originColumn * grid.getSquareSize(), originRow * grid.getSquareSize(), boundColumn * grid.getSquareSize(), boundRow * grid.getSquareSize(), grid)) {
            return false;
        }

        for (int i = originRow; i <= boundRow; i++) {
            for (int j = originColumn; j <= boundColumn; j++) {
                if (gridMatrix[i][j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isOutOfArea(int originX, int originY, int boundX, int boundY, Grid grid) {
        boolean outOfWidth = boundX > grid.getWidth() || originX < 0;
        boolean outOfHeight = boundY > grid.getHeight() || originY < 0;
        return outOfHeight || outOfWidth;
    }

    private boolean isValidSummerLocation(Grid grid, Location location) {
        return location.getX() <= grid.getWidth() / 2;
    }

    private boolean isValidWinterLocation(Grid grid, Location location) {
        return location.getX() > grid.getWidth() / 2;
    }

    private void updateGridMatrixForTreasure(Grid grid, Location startLocation) {
        byte[][] gridMatrix = grid.getGridMatrix();
        gridMatrix[startLocation.getY() / grid.getSquareSize()][startLocation.getX() / grid.getSquareSize()] = 2;
    }

    private void updateGridMatrixForObstacle(Grid grid, ImmobileObstacle obstacle) {
        byte[][] gridMatrix = grid.getGridMatrix();
        int originColumn = (obstacle.getOriginLocation().getX()) / grid.getSquareSize();
        int boundColumn = (obstacle.getOriginLocation().getX() + obstacle.getDimension().getWidth() * grid.getSquareSize()) / grid.getSquareSize();
        int originRow = obstacle.getOriginLocation().getY() / grid.getSquareSize();
        int boundRow = (obstacle.getOriginLocation().getY() + obstacle.getDimension().getHeight() * grid.getSquareSize()) / grid.getSquareSize();
        for (int i = originRow; i < boundRow; i++) {
            for (int j = originColumn; j < boundColumn; j++) {
                gridMatrix[i][j] = 1;
            }
        }
    }

    private void updateGridMatrixForMobileObstacle(Grid grid, MobileObstacle mobileObstacle) {
        byte[][] gridMatrix = grid.getGridMatrix();
        if (mobileObstacle.getDirection().equals(Direction.LEFT_RIGHT)) {
            int originColumn = (mobileObstacle.getOriginLocation().getX() - (mobileObstacle.getRange() * grid.getSquareSize() + mobileObstacle.getDimension().getWidth() * grid.getSquareSize())) / grid.getSquareSize();
            int boundColumn = (mobileObstacle.getOriginLocation().getX() + (mobileObstacle.getRange() * grid.getSquareSize() + mobileObstacle.getDimension().getWidth() * grid.getSquareSize())) / grid.getSquareSize();
            int originRow = mobileObstacle.getOriginLocation().getX() / grid.getSquareSize();
            int boundRow = (mobileObstacle.getOriginLocation().getY() + mobileObstacle.getDimension().getHeight() * grid.getSquareSize()) / grid.getSquareSize();
            for (int i = originRow; i < boundRow; i++) {
                for (int j = originColumn; j < boundColumn; j++) {
                    gridMatrix[i][j] = 1;
                }
            }
        } else {
            int originRow = (mobileObstacle.getOriginLocation().getY() - (mobileObstacle.getRange() * grid.getSquareSize() + mobileObstacle.getDimension().getHeight() * grid.getSquareSize())) / grid.getSquareSize(); // yukari indis
            int boundRow = (mobileObstacle.getOriginLocation().getY() + mobileObstacle.getRange() * grid.getSquareSize() + mobileObstacle.getDimension().getHeight()) / grid.getSquareSize(); // asagi indis
            int originColumn = mobileObstacle.getOriginLocation().getX() / grid.getSquareSize(); // baslangic sutunu
            int boundColumn = (mobileObstacle.getOriginLocation().getX() + mobileObstacle.getDimension().getWidth() * grid.getSquareSize()) / grid.getSquareSize(); // bitis sutunu
            for (int i = originRow; i < boundRow; i++) {
                for (int j = originColumn; j < boundColumn; j++) {
                    gridMatrix[i][j] = 1;
                }
            }
        }
    }

    private void updateGridMatrixForCharacter(Grid grid, Location startLocation) {
        byte[][] gridMatrix = grid.getGridMatrix();
        gridMatrix[startLocation.getY() / grid.getSquareSize()][startLocation.getX() / grid.getSquareSize()] = 3;
    }

}
