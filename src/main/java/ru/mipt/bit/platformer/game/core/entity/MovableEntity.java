package ru.mipt.bit.platformer.game.core.entity;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;

public interface MovableEntity extends GameEntity {
    /*
    Некий объект игры, способный к перемещению.
     */
    void move(MoveCommand command, BaseLevel level);
    boolean isMoving();
    void updateProgress(float deltaTime);
    float getProgress();
    Coordinates getDestination();
}
