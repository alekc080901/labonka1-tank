package ru.mipt.bit.platformer.game.core.pubsub;

import ru.mipt.bit.platformer.game.core.entity.EntityMovePattern;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.AbstractSound;

public class CreateMovableEntityContainer extends CreateEntityContainer {
    private final EntityMovePattern movePattern;

    public CreateMovableEntityContainer(GameEntity entity, String entityImagePath, AbstractSound sound,
                                        EntityMovePattern movePattern) {
        super(entity, entityImagePath, sound);
        this.movePattern = movePattern;
    }

    public EntityMovePattern getMovePattern() {
        return movePattern;
    }

}
