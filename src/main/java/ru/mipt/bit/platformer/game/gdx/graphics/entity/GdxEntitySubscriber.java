package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import ru.mipt.bit.platformer.game.core.entity.Bullet;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;
import ru.mipt.bit.platformer.game.core.entity.pubsub.EntityContainer;
import ru.mipt.bit.platformer.game.core.entity.pubsub.EntitySubscriber;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;
import ru.mipt.bit.platformer.game.gdx.sound.SFXPlayer;

public class GdxEntitySubscriber implements EntitySubscriber {

    private final GdxLevel level;
    private final SFXPlayer sfxPlayer;

    public GdxEntitySubscriber(GdxLevel level, SFXPlayer sfxPlayer) {
        this.level = level;
        this.sfxPlayer = sfxPlayer;
    }

    @Override
    public void update(EntityContainer container) {
        switch (container.getOperation()) {
            case CREATE:
                level.addEntity(container.getEntity(), makeGdxEntity(container));
                if (container.getEntity() instanceof Bullet) {
                    sfxPlayer.playRandomMeow();
                }
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
