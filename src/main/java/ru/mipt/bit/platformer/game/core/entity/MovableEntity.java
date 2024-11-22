package ru.mipt.bit.platformer.game.core.entity;

import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;

public interface MovableEntity extends GameEntity {
    /*
    Некий объект игры, способный к перемещению.
     */
    void move(float rotation, BaseLevel level);
    boolean isMoving();
    void updateProgress(float deltaTime);
    float getProgress();
    Coordinates getDestination();
    EntityMovePattern getMovePattern();
}
