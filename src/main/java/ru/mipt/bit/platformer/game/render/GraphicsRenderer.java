package ru.mipt.bit.platformer.game.render;

public interface GraphicsRenderer {
    void render(float deltaTime);
    void clear();
    void dispose();
}
