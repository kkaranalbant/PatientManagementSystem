/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab.service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import prolab.model.Obstacle;
import prolab.model.Treasure;

/**
 *
 * @author kaan
 */
public class InfoManager {

    private static InfoManager infoManager;

    private List<Treasure> treasures;

    private PriorityQueue<Treasure> priorityTreasureQueue;

    private List<Obstacle> obstacles;

    private InfoManager() {
        treasures = new ArrayList();
        priorityTreasureQueue = new PriorityQueue<>((treasure1, treasure2) -> treasure1.getTreasureType().getPriority() - treasure2.getTreasureType().getPriority());
        obstacles = new ArrayList();
    }

    public static InfoManager getInstance() {
        if (infoManager == null) {
            infoManager = new InfoManager();
        }
        return infoManager;
    }

    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
        priorityTreasureQueue.add(treasure);
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public String createInfoMessageForPriorityQueue() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Treasures were found in order of priority  ");
        while (!priorityTreasureQueue.isEmpty()) {
            Treasure priorityTreasure = priorityTreasureQueue.poll();
            messageBuilder.append(priorityTreasure.getTreasureType().getName()).append("(").append(priorityTreasure.getOriginLocation().getX()).append(",").append(priorityTreasure.getOriginLocation().getY()).append(")  ");
        }
        return messageBuilder.toString();

    }

    public String createInfoMessageForTreasureList() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Treasures found chronologically  ");
        for (Treasure treasure : treasures) {
            messageBuilder.append(treasure.getTreasureType().getName()).append("(").append(treasure.getOriginLocation().getX()).append(",").append(treasure.getOriginLocation().getY()).append(")  ");
        }
        return messageBuilder.toString();
    }

    public String createInfoMessageForObstacleList() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Obstacles in Sequential Order  ");
        for (Obstacle obstacle : obstacles) {
            messageBuilder.append("(").append(obstacle.getOriginLocation().getX()).append(",").append(obstacle.getOriginLocation().getY()).append(")  ");
        }
        return messageBuilder.toString();
    }

}
