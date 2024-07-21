/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab.model;

/**
 *
 * @author kaan
 */
public enum TreasureType {
    Diamond(1, "Diamond"),
    Gold(2, "Gold"),
    Silver(3, "Silver"),
    Copper(4, "Copper");

    private int priority;
    private String name;

    private TreasureType(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

}
