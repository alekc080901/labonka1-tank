package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

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
        GdxEntity object = new GdxMoveEntity(gameEntity, "images/tank_blue.png", Interpolation.smooth);
        createdObjects.add(object);
        return object;
    }

    public static GdxEntity getGreenTree(GameEntity gameEntity) {
        GdxEntity object = new GdxEntity(gameEntity, "images/greenTree.png");
        createdObjects.add(object);
        return object;
    }
}
