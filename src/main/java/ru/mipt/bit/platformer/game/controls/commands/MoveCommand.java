package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;

import java.util.HashMap;

public class MoveCommand implements Command {
    private final Coordinates directionChange;
    private final float rotation;
    private final MovableEntity entity;
    private final BaseLevel level;
    private final static HashMap<InputInstruction, Float> rotationFromInstruction = new HashMap<>();
    private final static HashMap<Float, Coordinates> directionFromRotation = new HashMap<>();

    static {
        fillRotationMap();
        fillDirectionMap();
    }

    public MoveCommand(BaseLevel level, MovableEntity entity, InputInstruction instruction) {
        this.rotation = getRotationFromInstruction(instruction);
        this.directionChange = getChangeFromRotation(getRotationFromInstruction(instruction));
        this.entity = entity;
        this.level = level;
    }

    @Override
    public void execute() {
        entity.move(rotation, level);
    }

    public static Coordinates getChangeFromRotation(float rotation) {
        return directionFromRotation.get(rotation);
    }

    public float getRotation() {
        return rotation;
    }

    private static float getRotationFromInstruction(InputInstruction instruction) {
        return rotationFromInstruction.get(instruction);
    }


    private static void fillRotationMap() {
        rotationFromInstruction.put(InputInstruction.LEFT, 180f);
        rotationFromInstruction.put(InputInstruction.UP, 90f);
        rotationFromInstruction.put(InputInstruction.RIGHT, 0f);
        rotationFromInstruction.put(InputInstruction.DOWN, 270f);
    }

    private static void fillDirectionMap() {
        directionFromRotation.put(180f, new Coordinates(-1, 0));
        directionFromRotation.put(90f, new Coordinates(0, 1));
        directionFromRotation.put(0f, new Coordinates(1, 0));
        directionFromRotation.put(270f, new Coordinates(0, -1));
    }
}
