package ru.mipt.bit.platformer.game.core.entity.pubsub;

public interface EntityPublisher {
    void register(EntitySubscriber subscriber);
    void remove(EntitySubscriber subscriber);
    void notify(EntityContainer container);
}
