package ru.mipt.bit.platformer.game.gdx;

import ru.mipt.bit.platformer.game.core.Level;

public interface LevelLoader {
    Level loadFromFile(String path);
    Level loadByRandom();
}
