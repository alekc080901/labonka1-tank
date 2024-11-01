package ru.mipt.bit.platformer.game.core;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;

public interface MovableEntity extends GameEntity {
    /*
    Некий объект игры, способный к перемещению.
     */
    void turn(float direction);
    void move(MoveCommand command, BaseLevel level);
    void updateProgress(float deltaTime);
    float getProgress();
    Coordinates getDestination();
}