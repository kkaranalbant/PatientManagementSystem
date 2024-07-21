/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab.model;

import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import prolab.grid.Grid;
import prolab.media.ImagePath;

/**
 *
 * @author kaan
 */
public class ObjectImage {
    
    private static Grid grid ;
    
    private static Image CHARACTER ;
    private static Image SUMMER_TREE;
    private static Image WINTER_TREE;
    private static Image SUMMER_JUNGLE;
    private static Image WINTER_JUNGLE;
    private static Image SUMMER_COBBLE_STONE;
    private static Image WINTER_COBBLE_STONE;
    private static Image SUMMER_MOUNTAIN;
    private static Image WINTER_MOUNTAIN;
    private static Image SUMMER_ROW_MOUNTAIN;
    private static Image WINTER_ROW_MOUNTAIN;
    private static Image SUMMER_ROCK;
    private static Image WINTER_ROCK;
    private static Image SUMMER_WALL;
    private static Image WINTER_WALL;
    private static Image SUMMER_LAKE;
    private static Image WINTER_LAKE;
    private static Image DIAMOND_CHESS;
    private static Image GOLD_CHESS;
    private static Image SILVER_CHESS;
    private static Image COPPER_CHESS;
    private static Image BEE;
    private static Image BIRD;
    private static Image FOG ;
    
    
    static {
        grid = Grid.getGrid() ;
        try {
            CHARACTER =  ImageIO.read(new File(ImagePath.CHARACTER.getPath())).getScaledInstance(ObjectDimension.CHARACTER.getWidth() * grid.getSquareSize(), ObjectDimension.CHARACTER.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            SUMMER_TREE= ImageIO.read(new File(ImagePath.SUMMER_TREE.getPath())).getScaledInstance(ObjectDimension.SINGLE_TREE.getWidth() * grid.getSquareSize(), ObjectDimension.SINGLE_TREE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            WINTER_TREE= ImageIO.read(new File(ImagePath.WINTER_TREE.getPath())).getScaledInstance(ObjectDimension.SINGLE_TREE.getWidth() * grid.getSquareSize(), ObjectDimension.SINGLE_TREE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            SUMMER_JUNGLE= ImageIO.read(new File(ImagePath.SUMMER_JUNGLE.getPath())).getScaledInstance(ObjectDimension.JUNGLE.getWidth() * grid.getSquareSize(), ObjectDimension.JUNGLE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            WINTER_JUNGLE= ImageIO.read(new File(ImagePath.WINTER_JUNGLE.getPath())).getScaledInstance(ObjectDimension.JUNGLE.getWidth() * grid.getSquareSize(), ObjectDimension.JUNGLE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            SUMMER_COBBLE_STONE= ImageIO.read(new File(ImagePath.SUMMER_COBBEL_STONE.getPath())).getScaledInstance(ObjectDimension.SMALL_STONE.getWidth() * grid.getSquareSize(), ObjectDimension.SMALL_STONE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            WINTER_COBBLE_STONE= ImageIO.read(new File(ImagePath.WINTER_COBBEL_STONE.getPath())).getScaledInstance(ObjectDimension.SMALL_STONE.getWidth() * grid.getSquareSize(), ObjectDimension.SMALL_STONE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            SUMMER_MOUNTAIN= ImageIO.read(new File(ImagePath.SUMMER_MOUNTAIN.getPath())).getScaledInstance(ObjectDimension.MOUNTAIN.getWidth() * grid.getSquareSize(), ObjectDimension.MOUNTAIN.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            WINTER_MOUNTAIN= ImageIO.read(new File(ImagePath.WINTER_MOUNTAIN.getPath())).getScaledInstance(ObjectDimension.MOUNTAIN.getWidth() * grid.getSquareSize(), ObjectDimension.MOUNTAIN.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            SUMMER_ROW_MOUNTAIN= ImageIO.read(new File(ImagePath.SUMMER_ROW_MOUNTAIN.getPath())).getScaledInstance(ObjectDimension.ROW_MOUNTAINS.getWidth() * grid.getSquareSize(), ObjectDimension.ROW_MOUNTAINS.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            WINTER_ROW_MOUNTAIN= ImageIO.read(new File(ImagePath.WINTER_ROW_MOUNTAIN.getPath())).getScaledInstance(ObjectDimension.ROW_MOUNTAINS.getWidth() * grid.getSquareSize(), ObjectDimension.ROW_MOUNTAINS.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            SUMMER_ROCK= ImageIO.read(new File(ImagePath.SUMMER_ROCK.getPath())).getScaledInstance(ObjectDimension.ROCK.getWidth() * grid.getSquareSize(), ObjectDimension.ROCK.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            WINTER_ROCK= ImageIO.read(new File(ImagePath.WINTER_ROCK.getPath())).getScaledInstance(ObjectDimension.ROCK.getWidth() * grid.getSquareSize(), ObjectDimension.ROCK.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            SUMMER_WALL= ImageIO.read(new File(ImagePath.SUMMER_WALL.getPath())).getScaledInstance(ObjectDimension.WALL.getWidth() * grid.getSquareSize(), ObjectDimension.WALL.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            WINTER_WALL= ImageIO.read(new File(ImagePath.WINTER_WALL.getPath())).getScaledInstance(ObjectDimension.WALL.getWidth() * grid.getSquareSize(), ObjectDimension.WALL.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            SUMMER_LAKE= ImageIO.read(new File(ImagePath.SUMMER_LAKE.getPath())).getScaledInstance(ObjectDimension.LAKE.getWidth() * grid.getSquareSize(), ObjectDimension.LAKE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            WINTER_LAKE= ImageIO.read(new File(ImagePath.WINTER_LAKE.getPath())).getScaledInstance(ObjectDimension.LAKE.getWidth() * grid.getSquareSize(), ObjectDimension.LAKE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            DIAMOND_CHESS= ImageIO.read(new File(ImagePath.DIAMOND_CHESS.getPath())).getScaledInstance(ObjectDimension.TREASURE.getWidth() * grid.getSquareSize(), ObjectDimension.TREASURE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            GOLD_CHESS= ImageIO.read(new File(ImagePath.GOLD_CHESS.getPath())).getScaledInstance(ObjectDimension.TREASURE.getWidth() * grid.getSquareSize(), ObjectDimension.TREASURE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            SILVER_CHESS= ImageIO.read(new File(ImagePath.SILVER_CHESS.getPath())).getScaledInstance(ObjectDimension.TREASURE.getWidth() * grid.getSquareSize(), ObjectDimension.TREASURE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            COPPER_CHESS= ImageIO.read(new File(ImagePath.COPPER_CHESS.getPath())).getScaledInstance(ObjectDimension.TREASURE.getWidth() * grid.getSquareSize(), ObjectDimension.TREASURE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            BEE= ImageIO.read(new File(ImagePath.BEE.getPath())).getScaledInstance(ObjectDimension.BEE.getWidth() * grid.getSquareSize(), ObjectDimension.BEE.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            BIRD= ImageIO.read(new File(ImagePath.BIRD.getPath())).getScaledInstance(ObjectDimension.BIRD.getWidth() * grid.getSquareSize(), ObjectDimension.BIRD.getHeight()* grid.getSquareSize(), Image.SCALE_SMOOTH);
            FOG= ImageIO.read(new File(ImagePath.FOG.getPath())).getScaledInstance(grid.getSquareSize(),grid.getSquareSize(), Image.SCALE_SMOOTH);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Image getCHARACTER() {
        return CHARACTER;
    }

    public static Image getSUMMER_TREE() {
        return SUMMER_TREE;
    }

    public static Image getWINTER_TREE() {
        return WINTER_TREE;
    }

    public static Image getSUMMER_JUNGLE() {
        return SUMMER_JUNGLE;
    }

    public static Image getWINTER_JUNGLE() {
        return WINTER_JUNGLE;
    }

    public static Image getSUMMER_COBBLE_STONE() {
        return SUMMER_COBBLE_STONE;
    }

    public static Image getWINTER_COBBLE_STONE() {
        return WINTER_COBBLE_STONE;
    }

    public static Image getSUMMER_MOUNTAIN() {
        return SUMMER_MOUNTAIN;
    }

    public static Image getWINTER_MOUNTAIN() {
        return WINTER_MOUNTAIN;
    }

    public static Image getSUMMER_ROW_MOUNTAIN() {
        return SUMMER_ROW_MOUNTAIN;
    }

    public static Image getWINTER_ROW_MOUNTAIN() {
        return WINTER_ROW_MOUNTAIN;
    }

    public static Image getSUMMER_ROCK() {
        return SUMMER_ROCK;
    }

    public static Image getWINTER_ROCK() {
        return WINTER_ROCK;
    }

    public static Image getSUMMER_WALL() {
        return SUMMER_WALL;
    }

    public static Image getWINTER_WALL() {
        return WINTER_WALL;
    }

    public static Image getSUMMER_LAKE() {
        return SUMMER_LAKE;
    }

    public static Image getWINTER_LAKE() {
        return WINTER_LAKE;
    }

    public static Image getDIAMOND_CHESS() {
        return DIAMOND_CHESS;
    }

    public static Image getGOLD_CHESS() {
        return GOLD_CHESS;
    }

    public static Image getSILVER_CHESS() {
        return SILVER_CHESS;
    }

    public static Image getCOPPER_CHESS() {
        return COPPER_CHESS;
    }

    public static Image getBEE() {
        return BEE;
    }

    public static Image getBIRD() {
        return BIRD;
    }

    public static Image getFOG() {
        return FOG;
    }
    
    
    
}
