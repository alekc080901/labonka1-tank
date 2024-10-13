package ru.mipt.bit.platformer.game.graphics;

import ru.mipt.bit.platformer.game.core.GameEntity;

public interface Entity {
    void dispose();
    GameEntity getGameEntity();
    void setRotation(float rotation);
}
