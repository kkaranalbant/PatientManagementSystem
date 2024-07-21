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
public abstract class GameObject {

    private Location originLocation;
    private Image image;
    private ObjectDimension dimension;

    public GameObject(Location originLocaiton, Image image, ObjectDimension dimension) {
        this.originLocation = originLocaiton;
        this.image = image;
        this.dimension = dimension;
    }

    public Location getOriginLocation() {
        return originLocation;
    }

    public Image getImage() {
        return image;
    }

    public ObjectDimension getDimension() {
        return dimension;
    }

}
