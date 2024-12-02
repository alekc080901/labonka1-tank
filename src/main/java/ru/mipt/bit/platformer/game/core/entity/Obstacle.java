package ru.mipt.bit.platformer.game.core.entity;

import ru.mipt.bit.platformer.game.core.Coordinates;

public class Obstacle implements GameEntity{

    private Coordinates coordinates;
    private final int zIndex;

    public Obstacle(Coordinates coordinates, int zIndex) {
        this.coordinates = coordinates;
        this.zIndex = zIndex;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates.x, coordinates.y);
    }

    @Override
    public float getRotation() {
        return 0f;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }
}
