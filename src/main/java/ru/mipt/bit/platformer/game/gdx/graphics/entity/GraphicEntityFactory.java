package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.data.GameSettings;

@Component
public class GraphicEntityFactory {

    private final GameSettings settings;

    @Autowired
    public GraphicEntityFactory(GameSettings settings) {
        this.settings = settings;
    }

    public GraphicEntity getUpdatedEntity(GraphicEntity entity) {
        if (settings.showHealthBar()) {
            entity = getEntityWithHealthBar(entity);
        }
        return entity;
    }
    
    private GraphicEntity getEntityWithHealthBar(GraphicEntity entity) {
        return new HealthDecorator(entity);
    }
}
