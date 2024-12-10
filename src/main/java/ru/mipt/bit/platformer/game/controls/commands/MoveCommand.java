package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.Direction;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;

public class MoveCommand implements Command {
    private final Direction direction;
    private final MovableEntity entity;
    private final BaseLevel level;

    public MoveCommand(BaseLevel level, MovableEntity entity, InputInstruction instruction) {
        this.direction = new Direction(instruction);
        this.entity = entity;
        this.level = level;
    }

    @Override
    public void execute() {
        entity.move(direction.getRotation(), level);
    }

    public float getRotation() {
        return direction.getRotation();
    }
}
