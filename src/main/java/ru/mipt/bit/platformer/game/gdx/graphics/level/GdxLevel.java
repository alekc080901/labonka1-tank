package ru.mipt.bit.platformer.game.gdx.graphics.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GraphicEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GraphicEntityFactory;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GdxSinglePlayAnimation;

import java.util.*;

import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.moveRectangleAtTileCenter;

public class GdxLevel {
    /*
    Класс с файлом уровня (карты).
     */
    private final TiledMap map;
    private final TreeMap<GameEntity, GraphicEntity> levelEntities = new TreeMap<>(
            Comparator.comparingInt(e -> ((GameEntity) e).getZIndex()).thenComparingInt(Object::hashCode)
);

    public GdxLevel(String path) {
        this.map = new TmxMapLoader().load(path);
    }

    public TiledMap getMapObject() {
        return map;
    }

    public static Coordinates getLevelSizeFromFile(String path) {
        TiledMap map = new TmxMapLoader().load(path);
        int width = (int) map.getProperties().get("width");
        int height = (int) map.getProperties().get("height");
        return new Coordinates(width, height);
    }

    public TiledMapTileLayer getGroundLayer() {
        return getSingleLayer(map);
    }

    public void drawEntities(Batch batch, float deltaTime) {
        for (GameEntity entity : levelEntities.descendingKeySet()) {
            GraphicEntity updatedEntity = GraphicEntityFactory.getUpdatedEntity(levelEntities.get(entity));
            updatedEntity.draw(batch, deltaTime);
        }
        removeSingleUseEntities();
    }

    private void removeSingleUseEntities() {
        Set<GameEntity> forRemoval = new HashSet<>();
        for (Map.Entry<GameEntity, GraphicEntity> entry : levelEntities.entrySet()) {
            if (entry.getValue() instanceof GdxSinglePlayAnimation && ((GdxSinglePlayAnimation) entry.getValue()).finishedPlaying()) {
                forRemoval.add(entry.getKey());
            }
        }
        forRemoval.forEach(levelEntities::remove);
    }

    public GraphicEntity getGraphicFromGame(GameEntity entity) {
        return levelEntities.get(entity);
    }

    public void addEntity(GameEntity gameEntity, GraphicEntity graphicsEntity) {
        levelEntities.put(gameEntity, graphicsEntity);
        Coordinates coords = gameEntity.getCoordinates();
        moveRectangleAtTileCenter(getGroundLayer(), graphicsEntity.getTexture().getRectangle(), new GridPoint2(coords.x, coords.y));
    }

    public void removeEntity(GameEntity gameEntity) {
        GraphicEntity gdxEntity = levelEntities.get(gameEntity);
        if (gdxEntity != null) {
            removeFromLevelEntities(gameEntity, gdxEntity);
        }
    }

    private void removeFromLevelEntities(GameEntity gameEntity, GraphicEntity gdxEntity) {
        levelEntities.remove(gameEntity);
        gdxEntity.dispose();
    }

    public void dispose() {
        for (GraphicEntity entity : levelEntities.values()) {
            entity.dispose();
        }
        map.dispose();
    }
}
