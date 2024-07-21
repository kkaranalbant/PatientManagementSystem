/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab.service;

import java.awt.Image;
import java.io.IOException;
import java.util.Random;
import prolab.model.GameObject;
import prolab.model.ImmobileObstacle;
import prolab.model.Location;
import prolab.model.ObjectDimension;
import prolab.model.Treasure;
import prolab.model.TreasureType;
import prolab.model.Character;
import prolab.model.Direction;
import prolab.model.MobileObstacle;
import prolab.model.ObjectImage;

/**
 *
 * @author kaan
 */
public class GameObjectFactory {

    private static GameObjectFactory gameObjectFactory;

    private static final Long CHARACTER_ID_ORIGIN;
    private static final Long CHARACTER_ID_BOUND;

    static {
        gameObjectFactory = null;
        CHARACTER_ID_ORIGIN = (long) 10000000;
        CHARACTER_ID_BOUND = (long) 20000000;
    }

    private Random idGen;

    private GameObjectFactory() {
        idGen = new Random();
    }

    public static GameObjectFactory getInstance() {
        if (gameObjectFactory == null) {
            gameObjectFactory = new GameObjectFactory();
        }
        return gameObjectFactory;
    }

    public GameObject createCharacter(String name, Location location) throws IOException {
        Image image =  ObjectImage.getCHARACTER() ;
        ObjectDimension dimension = ObjectDimension.CHARACTER;
        Long charaterId = createCharaterId();
        byte characterRange = 3;
        return new Character(name, charaterId, characterRange, location, image, dimension);
    }

    public GameObject createWinterTree(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.SINGLE_TREE, ObjectImage.getWINTER_TREE());
    }

    public GameObject createSummerTree(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.SINGLE_TREE,  ObjectImage.getSUMMER_TREE());
    }

    public GameObject createSummerJungle(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.JUNGLE,  ObjectImage.getSUMMER_JUNGLE());
    }

    public GameObject createWinterJungle(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.JUNGLE,  ObjectImage.getWINTER_JUNGLE());
    }

    public GameObject createWinterRock(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.ROCK,  ObjectImage.getWINTER_ROCK());
    }

    public GameObject createSummerRock(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.ROCK,  ObjectImage.getSUMMER_ROCK());
    }

    public GameObject createWinterCobbleStone(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.SMALL_STONE,  ObjectImage.getWINTER_COBBLE_STONE());
    }

    public GameObject createSummerCobbleStone(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.SMALL_STONE,  ObjectImage.getSUMMER_COBBLE_STONE());
    }

    public GameObject createSummerMountain(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.MOUNTAIN,  ObjectImage.getSUMMER_MOUNTAIN());
    }

    public GameObject createWinterMountain(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.MOUNTAIN,  ObjectImage.getWINTER_MOUNTAIN());
    }

    public GameObject createSummerRowMountain(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.ROW_MOUNTAINS,  ObjectImage.getSUMMER_ROW_MOUNTAIN());
    }

    public GameObject createWinterRowMountain(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.ROW_MOUNTAINS,  ObjectImage.getWINTER_ROW_MOUNTAIN());
    }

    public GameObject createWinterWall(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.WALL,  ObjectImage.getWINTER_WALL());
    }

    public GameObject createSummerWall(Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.WALL,  ObjectImage.getSUMMER_WALL());
    }
    
    public GameObject createSummerLake (Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.LAKE,  ObjectImage.getSUMMER_LAKE());
    }
    
    public GameObject createWinterLake (Location location) throws IOException {
        return createImmobileObstacle(location, ObjectDimension.LAKE,  ObjectImage.getWINTER_LAKE());
    }

    public GameObject createDiamondTreasure(Location location) throws IOException {
        return createTreasure(location, ObjectDimension.TREASURE,  ObjectImage.getDIAMOND_CHESS(), TreasureType.Diamond);
    }

    public GameObject createGoldTreasure(Location location) throws IOException {
        return createTreasure(location, ObjectDimension.TREASURE,  ObjectImage.getGOLD_CHESS(), TreasureType.Gold);
    }

    public GameObject createSilverTreasure(Location location) throws IOException {
        return createTreasure(location, ObjectDimension.TREASURE,  ObjectImage.getSILVER_CHESS(), TreasureType.Silver);
    }

    public GameObject createCopperTreasure(Location location) throws IOException {
        return createTreasure(location, ObjectDimension.TREASURE,  ObjectImage.getCOPPER_CHESS(), TreasureType.Copper);
    }

    public GameObject createBird(Location location) throws IOException {
        byte birdRange = 5;
        return createMobileObstacle(location, birdRange, ObjectDimension.BIRD,  ObjectImage.getBIRD(), Direction.UP_DOWN);
    }

    public GameObject createBee(Location location) throws IOException {
        byte beeRange = 3;
        return createMobileObstacle(location, beeRange, ObjectDimension.BEE,  ObjectImage.getBEE(), Direction.LEFT_RIGHT);
    }

    private GameObject createMobileObstacle(Location location, byte range, ObjectDimension dimension, Image image, Direction direction) throws IOException {
        return new MobileObstacle(direction, range, true, location, image, dimension);
    }

    private GameObject createImmobileObstacle(Location location, ObjectDimension dimension, Image image) throws IOException {
        return new ImmobileObstacle(false, location, image, dimension);
    }

    private GameObject createTreasure(Location location, ObjectDimension dimension, Image image, TreasureType type) throws IOException {
        return new Treasure(type, location, image, dimension);
    }

    private Long createCharaterId() {
        return idGen.nextLong(CHARACTER_ID_ORIGIN, CHARACTER_ID_BOUND);
    }

}
