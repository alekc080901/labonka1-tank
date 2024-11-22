package ru.mipt.bit.platformer.game.core.level;

import ru.mipt.bit.platformer.exceptions.IncorrectLevelSize;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.*;
import ru.mipt.bit.platformer.game.core.entity.pubsub.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseLevel {

    private final ArrayList<GameEntity> entities = new ArrayList<>();  // ArrayList, чтобы модифицировать на лету
    private final EntityPublisher graphicsLevelPublisher = new EntityPublisherImpl();
    private Coordinates upperRightSize;

    public BaseLevel(Coordinates upperRightSize) {
        setUpperRightSize(upperRightSize);
    }

    public void bindWithGraphics(EntitySubscriber graphicsSubscriber) {
        graphicsLevelPublisher.register(graphicsSubscriber);
    }

    public GameEntity getAt(Coordinates coordinates) {
        for (GameEntity entity : entities) {
            if (entity.getCoordinates().equals(coordinates)) {
                return entity;
            }
        }
        return null;
    }

    public Tank getTankAt(Coordinates coordinates) {
        for (GameEntity entity : entities) {
            if (entity.getCoordinates().equals(coordinates) && entity instanceof Tank) {
                return (Tank) entity;
            }
        }
        return null;
    }

    public boolean hasObstacle(Coordinates coordinates) {
        for (GameEntity obstacle : getObstacles()) {
            if (obstacle.getCoordinates().equals(coordinates)) {
                return true;
            }
        }
        return false;
    }

    public boolean occupiedWithEnemyTank(GameEntity caller, Coordinates coordinates) {
        for (MovableEntity tank : getTanks()) {
            if ((tank.getCoordinates().equals(coordinates) && !caller.equals(tank)) ||
                    (tank.getDestination().equals(coordinates)) && !caller.equals(tank)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTank(GameEntity caller, Coordinates coordinates) {
        for (MovableEntity tank : getTanks()) {
            if (tank.getCoordinates().equals(coordinates) && !caller.equals(tank)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOutOfBounds(Coordinates coords) {
        return coords.x >= upperRightSize.x || coords.x < 0 || coords.y >= upperRightSize.y || coords.y < 0;
    }

    public void removeZeroHealth() {
        for (int i = 0; i < entities.size(); i++) {
            GameEntity entity = entities.get(i);
            if (entity instanceof KillableEntity && ((KillableEntity) entity).getCurrentHealth() <= 0) {
                deleteEntity(entity);
            }
        }
    }

    public ArrayList<GameEntity> getAllEntities() {
        return entities;
    }

    public Set<Tank> getTanks() {
        return entities.stream()
                .filter(Tank.class::isInstance)
                .map(Tank.class::cast)
                .collect(Collectors.toSet());
    }

    public Set<Obstacle> getObstacles() {
        return entities.stream()
                .filter(Obstacle.class::isInstance)
                .map(Obstacle.class::cast)
                .collect(Collectors.toSet());
    }

    public void registerEntity(GameEntity entity, String imagePath) {
        entities.add(entity);
        EntityMovePattern movePattern = entity instanceof MovableEntity ? ((MovableEntity) entity).getMovePattern() : null;
        notifyOnCreate(entity, imagePath, movePattern);
    }

    private void notifyOnCreate(GameEntity entity, String imagePath, EntityMovePattern movePattern) {
        graphicsLevelPublisher.notify(new EntityContainer(entity, SupportedOperation.CREATE, imagePath, movePattern));
    }

    public void deleteEntity(GameEntity entity) {
        boolean isRemoved = entities.remove(entity);
        if (!isRemoved) {
            throw new IllegalStateException("Try to delete not registered entity");
        }
        notifyOnDelete(entity);
    }

    private void notifyOnDelete(GameEntity entity) {
        graphicsLevelPublisher.notify(new EntityContainer(entity, SupportedOperation.DELETE, null, null));
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