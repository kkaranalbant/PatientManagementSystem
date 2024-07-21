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
public class Treasure extends GameObject {

    private TreasureType treasureType;

    public Treasure(TreasureType treasureType, Location originLocaiton, Image image, ObjectDimension dimension) {
        super(originLocaiton, image, dimension);
        this.treasureType = treasureType;
    }

    public TreasureType getTreasureType() {
        return treasureType;
    }

    
    
}
