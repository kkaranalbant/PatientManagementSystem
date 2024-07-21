/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prolab.grid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import prolab.media.ImagePath;
import prolab.model.Direction;
import prolab.model.GameObject;
import prolab.model.Location;
import prolab.model.MobileObstacle;
import prolab.model.ObjectDimension;
import prolab.model.Obstacle;
import prolab.model.Character;
import prolab.model.ObjectImage;
import prolab.service.GameObjectManager;
import prolab.service.PathManager;
import prolab.service.PathManager.Node;

public class Grid extends JPanel implements ActionListener {

    private static Grid grid;

    private Timer timer;

    private GameObjectManager gameObjectManager;

    private PathManager pathManager;

    private List<Location> path;


    private byte squareSize;
    private int height;
    private int width;


    
    private byte[][] gridMatrix;

    private Grid(byte squareSize, int height, int width) {
        gameObjectManager = GameObjectManager.getInstance();
        this.squareSize = squareSize;
        this.height = height;
        this.width = width;
        timer = new Timer(500, this);
        timer.start();
    }

    public static Grid getInstance(byte squareSize, int height, int width) {
        if (grid == null) {
            grid = new Grid(squareSize, height, width);
        }
        return grid;
    }

    public static Grid getGrid() {
        return grid;
    }

    public void setPath(List<Location> path) {
        this.path = path;
    }

    public void setGridMatrixDimension() {
        gridMatrix = new byte[height][width];
    }


    public byte getSquareSize() {
        return squareSize;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public byte[][] getGridMatrix() {
        return gridMatrix;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        /*
            Hareketli nesnelerin konumlari ayarlanir .
            daha sonra yeniden cizilmesi icin addGameObjectsToGrid metodu cagrilir .
         */
        for (MobileObstacle mobileObstacle : gameObjectManager.getMobileObstacles()) {
            if (mobileObstacle.getDirection().equals(Direction.LEFT_RIGHT)) {
                int originX = mobileObstacle.getOriginLocation().getX();
                if (mobileObstacle.isInCenter()) {
                    mobileObstacle.getOriginLocation().setX(originX + squareSize * mobileObstacle.getRange());
                    mobileObstacle.setIsPositiveBound(true);
                    mobileObstacle.setIsInCenter(false);
                } else if (mobileObstacle.isPositiveBound()) {
                    mobileObstacle.getOriginLocation().setX(originX - 2 * squareSize * mobileObstacle.getRange());
                    mobileObstacle.setIsPositiveBound(false);
                } else {
                    mobileObstacle.getOriginLocation().setX(originX + 2 * squareSize * mobileObstacle.getRange());
                    mobileObstacle.setIsPositiveBound(true);
                }

            } else {
                int originY = mobileObstacle.getOriginLocation().getY();
                if (mobileObstacle.isInCenter()) {
                    mobileObstacle.getOriginLocation().setY(originY + squareSize * mobileObstacle.getRange());
                    mobileObstacle.setIsPositiveBound(true);
                    mobileObstacle.setIsInCenter(false);
                } else if (mobileObstacle.isPositiveBound()) {
                    mobileObstacle.getOriginLocation().setY(originY - 2 * squareSize * mobileObstacle.getRange());
                    mobileObstacle.setIsPositiveBound(false);
                } else {
                    mobileObstacle.getOriginLocation().setY(originY + 2 * squareSize * mobileObstacle.getRange());
                    mobileObstacle.setIsPositiveBound(true);
                }
            }
        }
        System.gc();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRectangles(g);
        drawGameObjects(g);
        drawLines(g);
        paintPath(g);
        drawFogForEmptyCells(g);
    }

    private void drawRectangles(Graphics g) {
        for (int i = 0; i < width; i += squareSize) {
            for (int j = 0; j < height; j += squareSize) {
                g.drawRect(i, j, squareSize, squareSize);
            }
        }
    }

    private void drawGameObjects(Graphics g) {
        pathManager = PathManager.getInstance();
        List<GameObject> objects = gameObjectManager.getGameObject();
        for (GameObject gameObject : objects) {
            int originLocationX = gameObject.getOriginLocation().getX();
            int originLocationY = gameObject.getOriginLocation().getY();
            if (path != null) {
                if (gameObject instanceof Obstacle) {
                    if (((Obstacle) gameObject).isVisible()) {
                        g.drawImage(gameObject.getImage(), originLocationX, originLocationY, null);
                    } else {
                        try {
                            Image scaledImage = reScaleImageByDimension(ImageIO.read(new File(ImagePath.FOG.getPath())), gameObject.getDimension());
                            g.drawImage(scaledImage, originLocationX, originLocationY, null);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    g.drawImage(gameObject.getImage(), originLocationX, originLocationY, null);
                }

            } else {
                g.drawImage(gameObject.getImage(), originLocationX, originLocationY, null);
            }
        }
    }

    private void drawLines(Graphics g) {
        List<MobileObstacle> mobileObstacles = gameObjectManager.getMobileObstacles();
        for (MobileObstacle mobileObstacle : mobileObstacles) {
            int startX, endX, startY, endY;

            if (mobileObstacle.getDirection().equals(Direction.LEFT_RIGHT)) {
                startX = mobileObstacle.getCenterLocation().getX() - mobileObstacle.getRange() * squareSize;
                endX = mobileObstacle.getCenterLocation().getX() + mobileObstacle.getRange() * squareSize + mobileObstacle.getDimension().getWidth() * squareSize;
                startY = mobileObstacle.getCenterLocation().getY() + mobileObstacle.getDimension().getHeight() * squareSize / 2;
                endY = startY;
            } else {
                startX = mobileObstacle.getCenterLocation().getX() + mobileObstacle.getDimension().getWidth() * squareSize / 2;
                endX = startX;
                startY = mobileObstacle.getCenterLocation().getY() - mobileObstacle.getRange() * squareSize;
                endY = mobileObstacle.getCenterLocation().getY() + mobileObstacle.getRange() * squareSize + mobileObstacle.getDimension().getHeight() * squareSize;
            }

            g.setColor(Color.RED);
            g.drawLine(startX, startY, endX, endY);
        }
    }

    private void paintPath(Graphics g) {
        Character character = gameObjectManager.getCharacter();
        if (path != null) {
            g.setColor(Color.GREEN);
            for (int i = 0; i < path.size() - 1; i++) {
                boolean isVertical = path.get(i).getX() - path.get(i + 1).getX() == 0;
                boolean isHorizontal = path.get(i).getY() - path.get(i + 1).getY() == 0;
                int startX = 0;
                int endX = 0;
                int startY = 0;
                int endY = 0;
                if (isVertical) {
                    startX = path.get(i).getX() + character.getDimension().getWidth() * squareSize / 2 ;
                    endX = startX;
                    startY = path.get(i).getY();
                    endY = path.get(i).getY() + character.getDimension().getHeight() * squareSize;
                }
                if (isHorizontal) {
                    startX = path.get(i).getX();
                    endX = path.get(i).getX() + character.getDimension().getWidth() * squareSize;
                    startY = path.get(i).getY() + character.getDimension().getHeight() * squareSize / 2;
                    endY = startY;
                }
                g.drawLine(startX, startY, endX, endY);
            }
        }
    }

    private void drawFogForEmptyCells(Graphics g) {
        if (path != null) {
            for (Node[] nodeArray : pathManager.getNodeMatrix()) {
                for (Node node : nodeArray) {
                    if (node.isNotVisibleEmptyCell()) {
                        g.drawImage(ObjectImage.getFOG(), node.getColumn() * squareSize, node.getRow() * squareSize, null);
                    }
                }
            }
        }
    }

    private Image reScaleImageByDimension(Image originalImage, ObjectDimension dimension) {
        int newImageWidth = dimension.getWidth() * squareSize; // boyutu kac x kac ise ona gore resmin boyutunu ayarliyoruz . (Orjinal foto degismiyor)
        int newImageHeight = dimension.getHeight() * squareSize;
        Image newImage = originalImage.getScaledInstance(newImageWidth, newImageHeight, Image.SCALE_SMOOTH);
        return newImage;
    }

}
