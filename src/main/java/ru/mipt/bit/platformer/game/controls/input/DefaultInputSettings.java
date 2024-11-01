package ru.mipt.bit.platformer.game.controls.input;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.controls.commands.RechargeDecorator;
import ru.mipt.bit.platformer.game.controls.commands.ToggleHealthCommand;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultInputSettings {
    /*
    Возвращает команды по умолчанию для различных устройств ввода.
     */

    private static final Set<Integer> holdableKeys = new HashSet<>(Set.of(UP, W, RIGHT, D, DOWN, S, LEFT, A));

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
        map.put(L, new RechargeDecorator(new ToggleHealthCommand(), 100));
        return map;
    }

    public static boolean isHoldable(int key) {
        return holdableKeys.contains(key);
    }
}