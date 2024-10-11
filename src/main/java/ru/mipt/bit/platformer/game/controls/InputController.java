package ru.mipt.bit.platformer.game.controls;

import ru.mipt.bit.platformer.game.controls.command_processing.PlayerCommandHandler;

import java.util.List;

public class InputController {
    /*
    Класс, обрабатывающий ввод всех игроков в игре.
     */

    private final List<PlayerCommandHandler> handlers;

    public InputController(List<PlayerCommandHandler> handlers) {
        this.handlers = handlers;
    }

    public void handleAllPlayers(float deltaTime) {
        for (PlayerCommandHandler handler : handlers) {
            handler.handleSinglePlayer(deltaTime);
        }
    }
}
