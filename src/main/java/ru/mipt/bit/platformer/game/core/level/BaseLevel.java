package ru.mipt.bit.platformer.game.core.level;

import ru.mipt.bit.platformer.exceptions.IncorrectLevelSize;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.*;
import ru.mipt.bit.platformer.game.core.entity.pubsub.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseLevel {

    private final Set<GameEntity> entities = new HashSet<>();
    private final EntityPublisher graphicsLevelPublisher = new EntityPublisherImpl();
    private Coordinates upperRightSize;

    public BaseLevel(Coordinates upperRightSize) {
        setUpperRightSize(upperRightSize);
    }

    public void bindWithGraphics(EntitySubscriber graphicsSubscriber) {
        graphicsLevelPublisher.register(graphicsSubscriber);
    }

    public boolean hasHitObstacle(MovableEntity entity) {
        for (GameEntity obstacle : getObstacles()) {
            if (obstacle.getCoordinates().equals(entity.getDestination())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasHitTank(MovableEntity entity) {
        for (MovableEntity tank : getTanks()) {
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
        entities.remove(entity);
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
