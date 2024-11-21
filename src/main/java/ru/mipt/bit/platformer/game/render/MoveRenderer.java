package ru.mipt.bit.platformer.game.render;

import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;

public interface MoveRenderer extends ActionRenderer {
    void shiftEntity(MovableEntity entity);
    void turnEntity(GameEntity entity);
}
