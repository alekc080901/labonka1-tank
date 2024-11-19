package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import ru.mipt.bit.platformer.game.core.GameEntity;

import java.util.ArrayList;
import java.util.List;

public class GdxEntityDatabase {
    /*
    Класс, хранящий методы создания готовых объектов и коллекцию всех созданных объектов (в данном случае танка и дерева).
    Является связывающим хвеном между логикой и графикой.
     */

    // Созданные объекты передаются и удаляются в LevelRenderer при остановке приложения
    public static List<GdxEntity> createdObjects = new ArrayList<>();

    public static GdxEntity getBlueTank(GameEntity gameEntity) {
        return getLevelObject("images/tank_blue.png", gameEntity);
    }

    public static GdxEntity getGreenTree(GameEntity gameEntity) {
        return getLevelObject("images/greenTree.png", gameEntity);
    }

    private static GdxEntity getLevelObject(String path, GameEntity gameEntity) {
        GdxEntity object = new GdxEntity(gameEntity, path);
        createdObjects.add(object);
        return object;
    }
}
