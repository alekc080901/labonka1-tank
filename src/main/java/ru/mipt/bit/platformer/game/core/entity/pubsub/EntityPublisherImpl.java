package ru.mipt.bit.platformer.game.core.entity.pubsub;

import java.util.HashSet;
import java.util.Set;

public class EntityPublisherImpl implements EntityPublisher {
    Set<EntitySubscriber> subscribers = new HashSet<>();

    @Override
    public void register(EntitySubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void remove(EntitySubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notify(EntityContainer container) {
        for (EntitySubscriber subscriber : subscribers) {
            subscriber.update(container);
        }
    }
}
