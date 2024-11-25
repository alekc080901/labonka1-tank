package ru.mipt.bit.platformer.game.core.entity.pubsub;

import org.springframework.stereotype.Component;

public interface EntitySubscriber {
    void update(EntityContainer container);
}
