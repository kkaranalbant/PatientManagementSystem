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
public abstract class Obstacle extends GameObject {

    private boolean isMovable;
    
    private boolean visible ;

    public Obstacle(boolean isMovable, Location originLocaiton, Image image, ObjectDimension dimension) {
        super(originLocaiton, image, dimension);
        this.isMovable = isMovable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    

}
