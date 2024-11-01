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
            int keyCode = entry.getKey();
            if (isPressed(keyCode) && isPressedOnlyOnceIfNeeded(keyCode)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private boolean isPressedOnlyOnceIfNeeded(int keyCode) {
        return DefaultInputSettings.isHoldable(keyCode)
                || !DefaultInputSettings.isHoldable(keyCode) && Gdx.input.isKeyJustPressed(keyCode);
    }

    private boolean isPressed(int keyCode) {
        return Gdx.input.isKeyPressed(keyCode);
    }

}
