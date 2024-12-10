package ru.mipt.bit.platformer.game.core.pubsub;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EntityPublisherImpl implements EntityPublisher {
    Set<Subscriber> subscribers = new HashSet<>();

    @Override
    public void register(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void remove(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notify(EntityContainer container) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(container);
        }
    }
}
