package ru.mipt.bit.platformer.game.renderers;

import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.core.entity.RotatableEntity;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;

import java.util.ArrayList;

@Component
public class LogicRenderer implements Renderer {

    private final BaseLevel level;
    private final GraphicsMoveRenderer moveRenderer;

    public LogicRenderer(BaseLevel level, GraphicsMoveRenderer moveRenderer) {
        this.level = level;
        this.moveRenderer = moveRenderer;
    }

    @Override
    public void render(float deltaTime) {
        ArrayList<GameEntity> gameEntities = level.getAllEntities();
        // fori не трогать! Нужно для возможности динамического удаления
        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity gameEntity = gameEntities.get(i);
            if (gameEntity instanceof MovableEntity) {
                renderMovable((MovableEntity) gameEntity, deltaTime);
            }
            if (gameEntity instanceof RotatableEntity) {
                renderRotatable((RotatableEntity) gameEntity);
            }
        }
        level.removeZeroHealth();
    }

    private void renderMovable(MovableEntity movableEntity, float deltaTime) {
        if (movableEntity.isMoving()) {
            movableEntity.updateProgress(deltaTime);
        }
        moveRenderer.shiftEntity(movableEntity);
    }

    private void renderRotatable(RotatableEntity gameEntity) {
        moveRenderer.turnEntity(gameEntity);
    }
}
