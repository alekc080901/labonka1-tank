package ru.mipt.bit.platformer.game.controls.input;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.game.controls.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class KeyboardReceiver implements InputReceiver {
    /*
    Класс, считывающий нажатие игроком клавиш клавиатуры.
     */

    private static final HashMap<Integer, Command> keyboardInput = DefaultInputSettings.forKeyboard();

    @Override
    public Command receiveInput() {
        for (Map.Entry<Integer, Command> entry : keyboardInput.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

}
