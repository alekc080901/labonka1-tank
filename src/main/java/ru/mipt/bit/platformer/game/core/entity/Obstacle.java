package ru.mipt.bit.platformer.game.core.entity;

import org.springframework.beans.factory.annotation.Value;
import ru.mipt.bit.platformer.game.core.Coordinates;

public class Obstacle implements GameEntity{

    private Coordinates coordinates;
    @Value("${game.entity.explosion.z-index}")
    private int zIndex;

    public Obstacle(Coordinates coordinates) {
        this.coordinates = coordinates;
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
