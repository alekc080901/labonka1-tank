package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import ru.mipt.bit.platformer.data.GameSettings;

public class GraphicEntityFactory {
    
    public static GraphicEntity getUpdatedEntity(GraphicEntity entity) {
        if (GameSettings.showHealthBar()) {
            entity = getEntityWithHealthBar(entity);
        }
        return entity;
    }
    
    private static GraphicEntity getEntityWithHealthBar(GraphicEntity entity) {
        return new HealthDecorator(entity);
    }
}
