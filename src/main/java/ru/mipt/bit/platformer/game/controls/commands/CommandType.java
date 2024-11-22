package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;

import java.util.HashMap;
import java.util.Map;

public enum CommandType {
    /*
    Команды из одного типа игрок свободен вводить лишь один раз за игровой тик.
     */
    MOVE,
    TOGGLE_HEALTH,
    SHOOT;
    private static final Map<InputInstruction, CommandType> accordingType = new HashMap<>();

    static {
        fillInstructionCommandMap();
    }

    public static CommandType get(InputInstruction instruction) {
        return accordingType.get(instruction);
    }

    private static void fillInstructionCommandMap() {
        accordingType.put(InputInstruction.UP, CommandType.MOVE);
        accordingType.put(InputInstruction.DOWN, CommandType.MOVE);
        accordingType.put(InputInstruction.LEFT, CommandType.MOVE);
        accordingType.put(InputInstruction.RIGHT, CommandType.MOVE);
        accordingType.put(InputInstruction.HEALTH_BAR, CommandType.TOGGLE_HEALTH);
        accordingType.put(InputInstruction.SHOOT, CommandType.SHOOT);
    }
}
