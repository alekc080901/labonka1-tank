package ru.mipt.bit.platformer.game.graphic_contracts;

public interface Renderers {
    void render(float deltaTime);
    LevelRenderer levelRenderer();
    MoveRenderer moveRenderer();
}
