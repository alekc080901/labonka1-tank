package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.core.entity.EntityMovePattern;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

import java.util.HashMap;
import java.util.Map;

public class GdxMoveEntity extends GdxEntity {
    private final Interpolation interpolationMethod;
    private static final Map<EntityMovePattern, Interpolation> interpolationMethods = new HashMap<>();

    static {
        interpolationMethods.put(EntityMovePattern.SIMPLE, Interpolation.linear);
        interpolationMethods.put(EntityMovePattern.EASE, Interpolation.smooth);
    }

    public GdxMoveEntity(GameEntity entity, String texturePath, EntityMovePattern movePattern) {
        super(entity, texturePath);
        this.interpolationMethod = interpolationMethods.get(movePattern);
    }

    public Interpolation getInterpolationMethod() {
        return interpolationMethod;
    }
}
