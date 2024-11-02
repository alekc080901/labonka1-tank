package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.MovableEntity;
import ru.mipt.bit.platformer.game.graphics.contracts.MoveRenderer;

public enum MoveCommand implements Command {
    /*
    Enum с командой перемещения от пользователя и направлением перемещения.
     */

    UP(new Coordinates(0, 1), 90f),
    DOWN(new Coordinates(0, -1), -90f),
    LEFT(new Coordinates(-1, 0), -180f),
    RIGHT(new Coordinates(1, 0), 0f);

    private final Coordinates directionChange;
    private final float rotation;
    private MovableEntity entity;
    private BaseLevel level;

    MoveCommand(Coordinates direction, float rotation) {
        this.directionChange = direction;
        this.rotation = rotation;
    }

    public void bind(MovableEntity entity, BaseLevel level) {
        this.entity = entity;
        this.level = level;
    }

    public float getRotation() {
        return rotation;
    }

    public int getShiftX() {
        return directionChange.x;
    }

    public int getShiftY() {
        return directionChange.y;
    }

    @Override
    public void execute() {
        entity.move(this, level);
    }


}
