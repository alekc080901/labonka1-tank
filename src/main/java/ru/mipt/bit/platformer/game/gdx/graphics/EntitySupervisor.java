package ru.mipt.bit.platformer.game.gdx.graphics;

import ru.mipt.bit.platformer.data.GameSettings;

public class EntitySupervisor {
    
    public static Entity getUpdatedEntity(Entity entity) {
        if (GameSettings.showHealthBar()) {
            entity = getEntityWithHealthBar(entity);
        }
        return entity;
    }
    
    private static Entity getEntityWithHealthBar(Entity entity) {
        return new HealthDecorator(entity);
    }
}
