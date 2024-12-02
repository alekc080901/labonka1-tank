package ru.mipt.bit.platformer.game.core.entity.pubsub;

import ru.mipt.bit.platformer.game.core.entity.GameEntity;

public class DeleteEntityContainer implements EntityContainer {
    private final GameEntity entity;

    public DeleteEntityContainer(GameEntity entity) {
        this.entity = entity;
    }

    public GameEntity getEntity() {
        return entity;
    }

}
