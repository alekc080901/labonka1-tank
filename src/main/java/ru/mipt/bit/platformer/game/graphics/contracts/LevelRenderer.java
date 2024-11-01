package ru.mipt.bit.platformer.game.graphics.contracts;

import ru.mipt.bit.platformer.game.core.BaseLevel;

public interface LevelRenderer {
    void render();
    void clear();
    void dispose();
    BaseLevel getSource();
}
