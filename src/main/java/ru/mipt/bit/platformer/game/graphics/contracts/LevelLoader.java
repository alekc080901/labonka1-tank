package ru.mipt.bit.platformer.game.graphics.contracts;

import ru.mipt.bit.platformer.game.controls.command_processing.CommandHandler;

public interface LevelLoader {
    Renderers loadFromFile(String path);
    Renderers loadByRandom();
}
