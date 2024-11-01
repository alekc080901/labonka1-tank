package ru.mipt.bit.platformer.game.graphics.contracts;

import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.GameEntity;
import ru.mipt.bit.platformer.game.core.MovableEntity;

public interface MoveRenderer extends ActionRenderer {
    void shiftEntity(MovableEntity entity);
    void turnEntity(GameEntity entity);
}
