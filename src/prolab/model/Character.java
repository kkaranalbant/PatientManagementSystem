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
public class Character extends GameObject {

    private String name;
    private Long id;
    private byte range;

    public Character(String name, Long id, byte range, Location originLocation, Image image, ObjectDimension dimension) {
        super(originLocation, image, dimension);
        this.name = name;
        this.id = id;
        this.range = range;
    }

    public byte getRange() {
        return range;
    }

}
