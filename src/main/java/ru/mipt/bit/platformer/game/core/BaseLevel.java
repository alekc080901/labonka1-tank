package ru.mipt.bit.platformer.game.core;

import java.util.Set;

public class BaseLevel {

    private final Set<Tank> tanks;
    private final Set<Obstacle> obstacles;
    private Coordinates upperRightSize;

    public BaseLevel(Set<Tank> tanks, Set<Obstacle> obstacles) {
        this.tanks = tanks;
        this.obstacles = obstacles;
    }

    public BaseLevel(Set<Tank> tanks, Set<Obstacle> obstacles, Coordinates upperRightSize) {
        this.tanks = tanks;
        this.obstacles = obstacles;
        this.upperRightSize = upperRightSize;
    }

    public Set<Tank> getTanks() {
        return tanks;
    }

    public Set<Obstacle> getObstacles() {
        return obstacles;
    }

    public void removeTank(Tank tank) {
        tanks.remove(tank);
    }

    public void removeObstacle(Obstacle obstacle) {
        obstacles.remove(obstacle);
    }

    public Coordinates getUpperRightSize() {
        return upperRightSize;
    }

    public void setUpperRightSize(Coordinates upperRightSize) {
        this.upperRightSize = upperRightSize;
    }
}
