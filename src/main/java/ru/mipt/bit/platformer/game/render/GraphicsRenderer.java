package ru.mipt.bit.platformer.game.render;

import ru.mipt.bit.platformer.game.core.BaseLevel;

public interface GraphicsRenderer {
    void render();
    void clear();
    void dispose();
    BaseLevel getSource();
}
