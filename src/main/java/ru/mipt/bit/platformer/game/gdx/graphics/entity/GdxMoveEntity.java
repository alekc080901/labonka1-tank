package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.core.entity.EntityMovePattern;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.texture.TextureDrawer;

import java.util.HashMap;
import java.util.Map;

public class GdxMoveEntity implements GraphicEntity {
    private final GdxEntity wrappedEntity;
    private final Interpolation interpolationMethod;
    private static final Map<EntityMovePattern, Interpolation> interpolationMethods = new HashMap<>();

    static {
        interpolationMethods.put(EntityMovePattern.SIMPLE, Interpolation.linear);
        interpolationMethods.put(EntityMovePattern.EASE, Interpolation.smooth);
    }

    public GdxMoveEntity(GameEntity entity, String texturePath, EntityMovePattern movePattern) {
        this.wrappedEntity = new GdxEntity(entity, texturePath);
        this.interpolationMethod = interpolationMethods.get(movePattern);
    }

    public Interpolation getInterpolationMethod() {
        return interpolationMethod;
    }

    @Override
    public void dispose() {
        wrappedEntity.dispose();
    }

    @Override
    public void draw(Batch batch, float deltaTime) {
        wrappedEntity.draw(batch, deltaTime);
    }

    @Override
    public GameEntity getGameEntity() {
        return wrappedEntity.gameEntity;
    }

    @Override
    public TextureDrawer getTexture() {
        return wrappedEntity.textureDrawer;
    }
}
