package ru.mipt.bit.platformer.game.core.entity.pubsub;

import ru.mipt.bit.platformer.game.core.entity.GameEntity;

public interface EntitySubscriber {
    void update(EntityContainer container);
}
