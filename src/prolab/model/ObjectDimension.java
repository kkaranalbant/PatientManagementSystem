/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab.model;

/**
 *
 * @author kaan
 */
public enum ObjectDimension {

    CHARACTER(2,2),
    SINGLE_TREE(2, 2),
    JUNGLE(4, 4),
    SMALL_STONE(2, 2),
    ROCK(3, 3),
    WALL(10, 1),
    MOUNTAIN(15, 15),
    ROW_MOUNTAINS(20, 15),
    BIRD(2, 2),
    BEE(2, 2),
    LAKE(2,2),
    TREASURE(3,3);

    private int width;
    private int height;

    private ObjectDimension(int height, int width) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
