package ru.mipt.bit.platformer.game.core;

import ru.mipt.bit.platformer.exceptions.IncorrectLevelSize;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;
import ru.mipt.bit.platformer.game.core.entity.Obstacle;
import ru.mipt.bit.platformer.game.core.entity.Tank;

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

    public boolean hasHitObstacle(MovableEntity entity) {
        for (GameEntity obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(entity.getDestination())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasHitTank(MovableEntity entity) {
        for (MovableEntity tank : tanks) {
            if ((tank.getCoordinates().equals(entity.getDestination()) && !entity.equals(tank)) ||
                    (tank.getDestination().equals(entity.getDestination())) && !entity.equals(tank)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTrespassedMap(MovableEntity entity) {
        Coordinates coords = entity.getDestination();
        return coords.x >= upperRightSize.x || coords.x < 0 || coords.y >= upperRightSize.y || coords.y < 0;
    }

    public Set<GameEntity> getAllEntities() {
        return entities;
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

    public void setUpperRightSize(Coordinates upperRightSize) {
        this.upperRightSize = upperRightSize;

        for (GameEntity entity : entities) {
            if (entity.getCoordinates().x > upperRightSize.x || entity.getCoordinates().y > upperRightSize.y) {
                throw new IncorrectLevelSize("Level object is out of level bounds!");
            }
        }
    }
}
