package ru.mipt.bit.platformer.game.core.entity.pubsub;

import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.AbstractSound;

public class CreateEntityContainer implements EntityContainer{
    private final GameEntity entity;
    private final String imagePath;
    private final AbstractSound soundOnCreate;

    public CreateEntityContainer(GameEntity entity, String imagePath, AbstractSound soundOnCreate) {
        this.entity = entity;
        this.imagePath = imagePath;
        this.soundOnCreate = soundOnCreate;
    }

    public GameEntity getEntity() {
        return entity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public AbstractSound getSound() {
        return soundOnCreate;
    }
}
