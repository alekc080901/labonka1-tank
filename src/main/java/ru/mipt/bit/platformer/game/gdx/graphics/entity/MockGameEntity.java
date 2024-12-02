package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

public class MockGameEntity implements GameEntity {
    private final Coordinates coordinates;
    private final float rotation;
    private final int zIndex;

    public MockGameEntity(Coordinates coordinates, float rotation, int zIndex) {
        this.coordinates = coordinates;
        this.rotation = rotation;
        this.zIndex = zIndex;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {}

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }
}
