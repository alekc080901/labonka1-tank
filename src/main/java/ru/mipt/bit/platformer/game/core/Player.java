package ru.mipt.bit.platformer.game.core;

import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.GameEntity;

public interface Player extends GameEntity {
    /*
    Игрок (не обязательно непосредственно пользователь) нашей игры.
     */
    void setRotation(float rotation);
    Coordinates getDestination();
    void setDestination(Coordinates coords);
}
