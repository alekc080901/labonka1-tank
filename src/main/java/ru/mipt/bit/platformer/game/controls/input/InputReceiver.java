package ru.mipt.bit.platformer.game.controls.input;

import ru.mipt.bit.platformer.game.controls.commands.Command;

import java.util.Collection;

public interface InputReceiver {
    /*
    Интерфейс считывания нажатия кнопок с устройства.
     */

    Collection<Command> receiveInput();
}
