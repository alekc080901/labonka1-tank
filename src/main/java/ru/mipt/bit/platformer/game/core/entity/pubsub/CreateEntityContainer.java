package ru.mipt.bit.platformer.game.core.entity.pubsub;

import ru.mipt.bit.platformer.game.core.entity.EntityMovePattern;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

public class CreateEntityContainer implements EntityContainer{
    private final GameEntity entity;
    private final String entityImagePath;

    public CreateEntityContainer(GameEntity entity, String entityImagePath) {
        this.entity = entity;
        this.entityImagePath = entityImagePath;
    }

    public GameEntity getEntity() {
        return entity;
    }

    public String getImagePath() {
        return entityImagePath;
    }


}
