package ru.mipt.bit.platformer.game.graphics.gdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.graphics.Level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class GdxLevel implements Level {
    /*
    Класс с файлом уровня (карты).
     */
    private final TiledMap map;
    private final BaseLevel level;
    private final Set<GdxEntity> tanks = new HashSet<>();
    private final Set<GdxEntity> obstacles = new HashSet<>();
    private final Map<GameEntity, GdxEntity> allEntities = new HashMap<>();

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

    @Override
    public BaseLevel getSourceLevel() {
        return level;
    }

    public Coordinates getLevelSize() {
        int width = (int) map.getProperties().get("width");
        int height = (int) map.getProperties().get("height");
        return new Coordinates(width, height);
    }

    public TiledMapTileLayer getGroundLayer() {
        return getSingleLayer(map);
    }

    public Set<GdxEntity> getTanks() {
        return tanks;
    }

    public Set<GdxEntity> getObstacles() {
        return obstacles;
    }

    public void drawEntities(Batch batch) {
        for (GdxEntity entity : allEntities.values()) {
            entity.draw(batch);
        }
    }

    public GdxEntity getGdxObjectFromEntity(GameEntity entity) {
        return allEntities.get(entity);
    }

    public void dispose() {
        for (GdxEntity entity : allEntities.values()) {
            entity.dispose();
        }
        map.dispose();
    }

    private void registerEntities() {
        for (GdxEntity entity : allEntities.values()) {
            Coordinates coords = entity.getCoordinates();
            moveRectangleAtTileCenter(getGroundLayer(), entity.getRectangle(), new GridPoint2(coords.x, coords.y));
        }
    }

    private void loadObstaclesFromLevel(BaseLevel level) {
        for (GameEntity obstacle : level.getObstacles()) {
            GdxEntity entity = GdxEntityDatabase.getGreenTree(obstacle);
            obstacles.add(entity);
            allEntities.put(obstacle, entity);
        }
    }

    private void loadTanksFromLevel(BaseLevel level) {
        for (Tank tank : level.getTanks()) {
            GdxEntity entity = GdxEntityDatabase.getBlueTank(tank);
            tanks.add(entity);
            allEntities.put(tank, entity);
        }
    }
}
