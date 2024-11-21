package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

public class GdxMoveEntity extends GdxEntity {
    private final Interpolation interpolationMethod;

    public GdxMoveEntity(GameEntity entity, String texturePath, Interpolation interpolationMethod) {
        super(entity, texturePath);
        this.interpolationMethod = interpolationMethod;
    }

    public Interpolation getInterpolationMethod() {
        return interpolationMethod;
    }
}
