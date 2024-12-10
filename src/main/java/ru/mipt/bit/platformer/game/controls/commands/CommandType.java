package ru.mipt.bit.platformer.game.controls.commands;

import org.springframework.stereotype.Component;
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
}
