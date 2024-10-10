package ru.mipt.bit.platformer.game.controls;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

public class KeyboardMouseHandler implements InputHandler {
    /*
    Класс, считывающий нажатие игроком клавиш клавиатуры.
     */

    private static final HashMap<Integer, Command> keyboardMouseInput = DefaultInputSettings.forKeyboardMouse();

    public Command handleUserInput() {
        for (Map.Entry<Integer, Command> entry : keyboardMouseInput.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

}
