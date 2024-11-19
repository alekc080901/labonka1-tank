package ru.mipt.bit.platformer.game.controls.input;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultInputSettings {
    /*
    Возвращает команды по умолчанию для различных устройств ввода.
     */

    // Клавиши, считывания из которых происходит непрерывно при зажатии клавиш
    private static final Set<Integer> holdableKeys = new HashSet<>(Set.of(UP, W, RIGHT, D, DOWN, S, LEFT, A));

    public static HashMap<Integer, InputInstruction> forKeyboard() {
        var map = new HashMap<Integer, InputInstruction>();
        map.put(UP, InputInstruction.UP);
        map.put(W, InputInstruction.UP);
        map.put(RIGHT, InputInstruction.RIGHT);
        map.put(D, InputInstruction.RIGHT);
        map.put(DOWN, InputInstruction.DOWN);
        map.put(S, InputInstruction.DOWN);
        map.put(LEFT, InputInstruction.LEFT);
        map.put(A, InputInstruction.LEFT);
        map.put(L, InputInstruction.HEALTH_BAR);
        return map;
    }

    public static boolean isHoldable(int key) {
        return holdableKeys.contains(key);
    }
}