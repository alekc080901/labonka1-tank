package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.MovableEntity;

import java.util.HashMap;

public class MoveCommand implements Command {
    /*
    Enum с командой перемещения от пользователя и направлением перемещения.
     */

    private final Coordinates directionChange;
    private final MovableEntity entity;
    private final BaseLevel level;
    private final InputInstruction instruction;
    private final static HashMap<InputInstruction, Float> rotationFromInstruction = new HashMap<>();
    private final static HashMap<InputInstruction, Coordinates> directionFromInstruction = new HashMap<>();

    static {
        fillRotationMap();
        fillDirectionMap();
    }

    public MoveCommand(BaseLevel level, MovableEntity entity, InputInstruction instruction) {
        this.directionChange = directionFromInstruction.get(instruction);
        this.entity = entity;
        this.level = level;
        this.instruction = instruction;
    }

    public float getRotation() {
        return rotationFromInstruction.get(instruction);
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

    private static void fillRotationMap() {
        rotationFromInstruction.put(InputInstruction.LEFT, 180f);
        rotationFromInstruction.put(InputInstruction.UP, 90f);
        rotationFromInstruction.put(InputInstruction.RIGHT, 0f);
        rotationFromInstruction.put(InputInstruction.DOWN, 270f);
    }

    private static void fillDirectionMap() {
        directionFromInstruction.put(InputInstruction.LEFT, new Coordinates(-1, 0));
        directionFromInstruction.put(InputInstruction.UP, new Coordinates(0, 1));
        directionFromInstruction.put(InputInstruction.RIGHT, new Coordinates(1, 0));
        directionFromInstruction.put(InputInstruction.DOWN, new Coordinates(0, -1));
    }

    @Override
    public String toString() {
        return instruction.toString();
    }
}
