package ru.mipt.bit.platformer.game.renderers;

public interface GraphicsRenderer {
    void render(float deltaTime);
    void clear();
    void dispose();
}
