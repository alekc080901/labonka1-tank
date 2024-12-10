package ru.mipt.bit.platformer.game.core;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;

import java.util.HashMap;

public class Direction {

    private final static HashMap<InputInstruction, Float> rotationFromInstruction = new HashMap<>();
    private final static HashMap<Float, Coordinates> directionFromRotation = new HashMap<>();

    static {
        fillRotationMap();
        fillDirectionMap();
    }

    private final InputInstruction instruction;

    public Direction(InputInstruction instruction) {
        this.instruction = instruction;
    }

    public static Coordinates getChangeFromRotation(float rotation) {
        return directionFromRotation.get(rotation);
    }

    public float getRotation() {
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
