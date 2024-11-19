package ru.mipt.bit.platformer.game.gdx.input;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;

import java.util.Collection;

public interface InputReceiver {
    /*
    Интерфейс считывания нажатия кнопок с устройства.
     */

    Collection<InputInstruction> receiveInput();
}
