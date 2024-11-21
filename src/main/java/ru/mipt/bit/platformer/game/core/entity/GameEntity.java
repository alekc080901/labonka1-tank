package ru.mipt.bit.platformer.game.core.entity;

import ru.mipt.bit.platformer.game.core.Coordinates;

public interface GameEntity {
    /*
    Класс сущности игры без привязки к графике.
     */

    Coordinates getCoordinates();

    void setCoordinates(Coordinates coordinates);

    float getRotation();
}
