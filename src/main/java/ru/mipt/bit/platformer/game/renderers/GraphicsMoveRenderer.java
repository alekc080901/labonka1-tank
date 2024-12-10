package ru.mipt.bit.platformer.game.renderers;

import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;
import ru.mipt.bit.platformer.game.core.entity.RotatableEntity;

public interface GraphicsMoveRenderer {
    void shiftEntity(MovableEntity entity);
    void turnEntity(RotatableEntity entity);
}
