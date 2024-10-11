package ru.mipt.bit.platformer.game.controls.input;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;

import java.util.HashMap;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultInputSettings {
    /*
    Возвращает команды по умолчанию для различных устройств ввода.
     */

    public static HashMap<Integer, Command> forKeyboard() {
        var map = new HashMap<Integer, Command>();
        map.put(UP, MoveCommand.UP);
        map.put(W, MoveCommand.UP);
        map.put(RIGHT, MoveCommand.RIGHT);
        map.put(D, MoveCommand.RIGHT);
        map.put(DOWN, MoveCommand.DOWN);
        map.put(S, MoveCommand.DOWN);
        map.put(LEFT, MoveCommand.LEFT);
        map.put(A, MoveCommand.LEFT);
        return map;
    }
}
