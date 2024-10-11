package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.input.InputReceiver;
import ru.mipt.bit.platformer.game.controls.input.KeyboardReceiver;

import java.util.List;

public class PlayerCommandDistributor implements CommandDistributor {
    /*
    Реализация контракта генерации команд, производимых с подключенных устройств пользователем.
     */
    private final List<InputReceiver> devices = List.of(
            new KeyboardReceiver()
    );

    @Override
    public Command getCurrentCommand() {
        for (InputReceiver device : devices) {
            Command pressedKey = device.receiveInput();
            if (pressedKey != null) return pressedKey;
        }
        return null;

    }
}
