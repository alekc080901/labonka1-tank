package ru.mipt.bit.platformer.game.graphic_contracts;

public interface LevelLoader {
    Renderers loadFromFile(String path);
    Renderers loadByRandom();
}
