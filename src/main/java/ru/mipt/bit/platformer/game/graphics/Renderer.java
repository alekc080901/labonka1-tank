package ru.mipt.bit.platformer.game.graphics;

import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.GameEntity;

public interface Renderer {
    void render();
    void clear();
    void dispose();
    void shiftEntity(Entity entity, Coordinates coordinates, float currentProgress);
    Entity getRenderedEntity(GameEntity entity);
    BaseLevel getLevel();
}
