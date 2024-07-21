/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab.model;

import java.awt.Image;

/**
 *
 * @author kaan
 */
public class MobileObstacle extends Obstacle {

    private Direction direction;
    private byte range;
    private boolean isPositiveBound;
    private boolean isInCenter ;
    private final Location centerLocation ;

    public MobileObstacle(Direction direction, byte range, boolean isMovable, Location originLocation, Image image, ObjectDimension dimension) {
        super(isMovable, originLocation, image, dimension);
        this.direction = direction;
        this.range = range;
        centerLocation = new Location (originLocation.getX() , originLocation.getY()) ;
        isPositiveBound = false;
        isInCenter = true ;
    }

    public Direction getDirection() {
        return direction;
    }

    public byte getRange() {
        return range;
    }

    public boolean isPositiveBound() {
        return isPositiveBound;
    }

    public boolean isInCenter() {
        return isInCenter;
    }

    public void setIsPositiveBound(boolean isPositiveBound) {
        this.isPositiveBound = isPositiveBound;
    }

    public void setIsInCenter(boolean isInCenter) {
        this.isInCenter = isInCenter;
    }

    public Location getCenterLocation() {
        return centerLocation;
    }
    
    
    
    

}
