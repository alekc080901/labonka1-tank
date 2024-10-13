package ru.mipt.bit.platformer.game.core;

import ru.mipt.bit.platformer.exceptions.IncorrectLevelSize;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseLevel {

    private final Set<Tank> tanks;
    private final Set<Obstacle> obstacles;
    private final Set<GameEntity> entities;
    private Coordinates upperRightSize;

    public BaseLevel(Set<Tank> tanks, Set<Obstacle> obstacles, Coordinates upperRightSize) {
        this.tanks = tanks;
        this.obstacles = obstacles;
        this.entities = Stream.of(tanks, obstacles).flatMap(Set::stream).collect(Collectors.toSet());
        setUpperRightSize(upperRightSize);
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

        for (GameEntity entity : entities) {
            if (entity.getCoordinates().x > upperRightSize.x || entity.getCoordinates().y > upperRightSize.y) {
                throw new IncorrectLevelSize("Level object is out of level bounds!");
            }
        }
    }
}
