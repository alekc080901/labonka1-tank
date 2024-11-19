package ru.mipt.bit.platformer.game.gdx.graphics.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.Entity;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.EntityFactory;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GdxEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GdxEntityDatabase;
import ru.mipt.bit.platformer.game.core.Level;

import java.util.HashMap;
import java.util.Map;

import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.moveRectangleAtTileCenter;

public class GdxLevel implements Level {
    /*
    Класс с файлом уровня (карты).
     */
    private final TiledMap map;
    private final BaseLevel level;
    private final Map<GameEntity, GdxEntity> levelEntities = new HashMap<>();

    public GdxLevel(String path, BaseLevel level) {
        this.map = new TmxMapLoader().load(path);
        this.level = level;

        loadTanksFromLevel(level);
        loadObstaclesFromLevel(level);
        registerEntities();
    }

    public TiledMap getMapObject() {
        return map;
    }

    public BaseLevel getSourceLevel() {
        return level;
    }

    public Coordinates getLevelSize() {
        int width = (int) map.getProperties().get("width");
        int height = (int) map.getProperties().get("height");
        return new Coordinates(width, height);
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

    public void dispose() {
        for (GdxEntity entity : levelEntities.values()) {
            entity.dispose();
        }
        map.dispose();
    }

    private void registerEntities() {
        for (GdxEntity entity : levelEntities.values()) {
            Coordinates coords = entity.getCoordinates();
            moveRectangleAtTileCenter(getGroundLayer(), entity.getRectangle(), new GridPoint2(coords.x, coords.y));
        }
    }

    private void loadObstaclesFromLevel(BaseLevel level) {
        for (GameEntity obstacle : level.getObstacles()) {
            GdxEntity entity = GdxEntityDatabase.getGreenTree(obstacle);
            levelEntities.put(obstacle, entity);
        }
    }

    private void loadTanksFromLevel(BaseLevel level) {
        for (Tank tank : level.getTanks()) {
            GdxEntity entity = GdxEntityDatabase.getBlueTank(tank);
            levelEntities.put(tank, entity);
        }
    }
}
