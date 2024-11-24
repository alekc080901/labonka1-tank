package ru.mipt.bit.platformer.game.core.entity.pubsub;

import ru.mipt.bit.platformer.game.core.entity.EntityMovePattern;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

public class CreateMovableEntityContainer extends CreateEntityContainer {
    private final EntityMovePattern movePattern;

    public CreateMovableEntityContainer(GameEntity entity, String entityImagePath, EntityMovePattern movePattern) {
        super(entity, entityImagePath);
        this.movePattern = movePattern;
    }

    public EntityMovePattern getMovePattern() {
        return movePattern;
    }

}
