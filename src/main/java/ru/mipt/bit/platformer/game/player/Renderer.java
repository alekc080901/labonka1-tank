package ru.mipt.bit.platformer.game.player;

import ru.mipt.bit.platformer.game.controls.Command;

public interface Renderer {
    void handleCommand(Command command, float deltaTime);
}
