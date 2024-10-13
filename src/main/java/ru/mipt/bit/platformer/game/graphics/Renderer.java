package ru.mipt.bit.platformer.game.graphics;

import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.GameEntity;

public interface Renderer<T extends Entity> {
    void render();
    void clear();
    void dispose();
    void shiftEntity(T entity, Coordinates coordinates, float currentProgress);
    T getRenderedEntity(GameEntity entity);
    BaseLevel getLevel();
}
