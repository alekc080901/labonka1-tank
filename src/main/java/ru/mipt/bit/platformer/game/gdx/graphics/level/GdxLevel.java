package ru.mipt.bit.platformer.game.gdx.graphics.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.Entity;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.EntityFactory;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GdxEntity;

import java.util.HashMap;
import java.util.Map;

import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.moveRectangleAtTileCenter;

public class GdxLevel {
    /*
    Класс с файлом уровня (карты).
     */
    private final TiledMap map;
    private final Map<GameEntity, GdxEntity> levelEntities = new HashMap<>();

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

    public void drawEntities(Batch batch) {
        for (GdxEntity entity : levelEntities.values()) {
            Entity updatedEntity = EntityFactory.getUpdatedEntity(entity);
            updatedEntity.draw(batch);
        }
    }

    public GdxEntity getGdxObjectFromEntity(GameEntity entity) {
        return levelEntities.get(entity);
    }

    public void addEntity(GameEntity gameEntity, GdxEntity graphicsEntity) {
        levelEntities.put(gameEntity, graphicsEntity);
        Coordinates coords = gameEntity.getCoordinates();
        moveRectangleAtTileCenter(getGroundLayer(), graphicsEntity.getRectangle(), new GridPoint2(coords.x, coords.y));
    }

    public void removeEntity(GameEntity gameEntity) {
        levelEntities.remove(gameEntity);
    }

    public void dispose() {
        for (GdxEntity entity : levelEntities.values()) {
            entity.dispose();
        }
        map.dispose();
    }
}
