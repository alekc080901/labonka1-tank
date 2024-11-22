package ru.mipt.bit.platformer.game.core.entity.pubsub;

import ru.mipt.bit.platformer.game.core.entity.EntityMovePattern;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

public class EntityContainer {
    private final GameEntity entity;
    private final String entityImagePath;
    private final EntityMovePattern movePattern;
    private final SupportedOperation operation;

    public EntityContainer(GameEntity entity, SupportedOperation operation, String entityImagePath, EntityMovePattern movePattern) {
        this.entity = entity;
        this.entityImagePath = entityImagePath;
        this.movePattern = movePattern;
        this.operation = operation;
    }

    public GameEntity getEntity() {
        return entity;
    }

    public String getImagePath() {
        return entityImagePath;
    }

    public EntityMovePattern getMovePattern() {
        return movePattern;
    }

    public SupportedOperation getOperation() {
        return operation;
    }
}
