package ru.mipt.bit.platformer.game.core.pubsub;

public interface EntityPublisher {
    void register(Subscriber subscriber);
    void remove(Subscriber subscriber);
    void notify(EntityContainer container);
}
