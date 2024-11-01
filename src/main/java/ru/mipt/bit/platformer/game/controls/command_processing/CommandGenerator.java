package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;

public interface CommandGenerator {
    /*
    Контракт на генерацию команд (от пользователя или кого-либо еще).
     */
    Command getCurrentCommand();
}
