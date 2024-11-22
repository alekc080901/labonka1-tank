package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import ru.mipt.bit.platformer.game.core.entity.MovableEntity;
import ru.mipt.bit.platformer.game.core.entity.pubsub.EntityContainer;
import ru.mipt.bit.platformer.game.core.entity.pubsub.EntitySubscriber;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;

public class GdxEntitySubscriber implements EntitySubscriber {

    private final GdxLevel level;

    public GdxEntitySubscriber(GdxLevel level) {
        this.level = level;
    }

    @Override
    public void update(EntityContainer container) {
        switch (container.getOperation()) {
            case CREATE:
                level.addEntity(container.getEntity(), makeGdxEntity(container));
                break;
            case DELETE:
                level.removeEntity(container.getEntity());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported EntityPublisher operation!");
        }
    }

    private GdxEntity makeGdxEntity(EntityContainer container) {
        if (container.getEntity() instanceof MovableEntity) {
            return new GdxMoveEntity(container.getEntity(), container.getImagePath(), container.getMovePattern());
        }
        return new GdxEntity(container.getEntity(), container.getImagePath());
    }
}
