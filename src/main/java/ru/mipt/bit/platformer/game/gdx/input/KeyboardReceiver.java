package ru.mipt.bit.platformer.game.gdx.input;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.game.controls.input.DefaultInputSettings;
import ru.mipt.bit.platformer.game.controls.input.InputInstruction;

import java.util.*;

public class KeyboardReceiver implements InputReceiver {
    /*
    Класс, считывающий нажатие игроком клавиш клавиатуры.
     */

    private static final HashMap<Integer, InputInstruction> keyboardInput = DefaultInputSettings.forKeyboard();

    @Override
    public Collection<InputInstruction> receiveInput() {
        Set<InputInstruction> inputtedCommands = new HashSet<>();
        for (Map.Entry<Integer, InputInstruction> entry : keyboardInput.entrySet()) {
            int keyCode = entry.getKey();
            if (isPressed(keyCode) && isPressedOnlyOnceIfNeeded(keyCode)) {
                inputtedCommands.add(entry.getValue());
            }
        }
        return inputtedCommands;
    }

    private boolean isPressedOnlyOnceIfNeeded(int keyCode) {
        return DefaultInputSettings.isHoldable(keyCode)
                || !DefaultInputSettings.isHoldable(keyCode) && Gdx.input.isKeyJustPressed(keyCode);
    }

    private boolean isPressed(int keyCode) {
        return Gdx.input.isKeyPressed(keyCode);
    }

}
