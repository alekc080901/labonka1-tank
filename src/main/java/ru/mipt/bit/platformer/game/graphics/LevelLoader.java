package ru.mipt.bit.platformer.game.graphics;

import ru.mipt.bit.platformer.game.controls.command_processing.PlayerCommandHandler;

import java.util.List;

public interface LevelLoader {
    Renderer generateRendererFromFile(String path);
    Renderer generateRandomRenderer();
    List<PlayerCommandHandler> getCommandHandlersFromLevelRenderer(Renderer renderer);
}
