package ru.mipt.bit.platformer.game.render;

import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class GameRenderer implements Renderer {

    private final BaseLevel level;
    private final MoveRenderer moveRenderer;

    public GameRenderer(BaseLevel level, MoveRenderer moveRenderer) {
        this.level = level;
        this.moveRenderer = moveRenderer;
    }

    public void render(float deltaTime) {
        ArrayList<GameEntity> gameEntities = level.getAllEntities();
        for (int i = 0; i < gameEntities.size(); i++) {
            GameEntity gameEntity = gameEntities.get(i);
            if (gameEntity instanceof MovableEntity) {
                renderMovable((MovableEntity) gameEntity, deltaTime);
            }
        }
    }

    private void renderMovable(MovableEntity movableEntity, float deltaTime) {
        if (movableEntity.isMoving()) {
            movableEntity.updateProgress(deltaTime);
        }
        moveRenderer.shiftEntity(movableEntity);
        moveRenderer.turnEntity(movableEntity);
        level.removeZeroHealth();
    }
}
