package ru.mipt.bit.platformer.game.gdx.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.AbstractSound;
import ru.mipt.bit.platformer.game.core.entity.pubsub.*;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.*;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;
import ru.mipt.bit.platformer.game.gdx.sound.SFXPlayer;

@Component
public class GdxEntitySubscriber implements EntitySubscriber {

    private final GdxLevel level;
    private final SFXPlayer sfxPlayer;

    @Autowired
    public GdxEntitySubscriber(GdxLevel level, SFXPlayer sfxPlayer) {
        this.level = level;
        this.sfxPlayer = sfxPlayer;
    }

    @Override
    public void update(EntityContainer container) {
        if (container instanceof CreateMovableEntityContainer) {
            registerEntity((CreateMovableEntityContainer) container);
        } else if (container instanceof ImageEntityContainer) {
            registerOverlapEntity((ImageEntityContainer) container);
        } else if (container instanceof CreateEntityContainer) {
            registerEntity((CreateEntityContainer) container);
        }  else if (container instanceof DeleteEntityContainer) {
            deleteEntity((DeleteEntityContainer) container);
        } else {
            throw new UnsupportedOperationException("Unsupported EntityPublisher operation!");
        }
    }

    private void deleteEntity(DeleteEntityContainer container) {
        level.removeEntity(container.getEntity());
    }

    private void registerOverlapEntity(ImageEntityContainer imageEntityContainer) {
        GameEntity overlapEntity = new MockGameEntity(imageEntityContainer.getCoordinates(), 0f,
                imageEntityContainer.getzIndex());
        level.addEntity(overlapEntity, makeGdxEntityFromImageContainer(overlapEntity, imageEntityContainer));
    }

    private void registerEntity(CreateEntityContainer container) {
        level.addEntity(container.getEntity(), makeGdxEntityFromCreateContainer(container));
        if (container.getSound() != AbstractSound.EMPTY) {
            sfxPlayer.play(container.getSound());
        }
    }

    private GraphicEntity makeGdxEntityFromCreateContainer(CreateEntityContainer container) {
        if (container instanceof CreateMovableEntityContainer) {
            var createMovableContainer = (CreateMovableEntityContainer) container;
            return new GdxMoveEntity(createMovableContainer.getEntity(), createMovableContainer.getImagePath(),
                    createMovableContainer.getMovePattern());
        }
        return new GdxEntity(container.getEntity(), container.getImagePath());
    }

    private GraphicEntity makeGdxEntityFromImageContainer(GameEntity entity, ImageEntityContainer container) {
        GdxEntity gdxEntity = new GdxEntity(entity, container.getImagePath());
        return new GdxSinglePlayAnimation(gdxEntity);
    }
}
