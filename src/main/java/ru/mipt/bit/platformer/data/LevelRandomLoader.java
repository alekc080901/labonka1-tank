package ru.mipt.bit.platformer.data;

import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.core.entity.EntityConfig;
import ru.mipt.bit.platformer.game.core.entity.Obstacle;
import ru.mipt.bit.platformer.game.core.entity.Tank;
import ru.mipt.bit.platformer.game.core.entity.pubsub.EntitySubscriber;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class LevelRandomLoader implements LevelLoader {
    private static final double TREE_PROBABILITY = 0.3;
    private static final int AI_NUMBER = 2;
    private final Coordinates upperBorder;
    private final Set<Coordinates> vacantCoords;
    private final Set<EntitySubscriber> subscribers;

    public LevelRandomLoader(Set<EntitySubscriber> subscribers, Coordinates upperBorder) {
        this.upperBorder = upperBorder;
        this.vacantCoords = this.generateCoords(upperBorder);
         this.subscribers = subscribers;
    }

    public LevelRandomLoader(Coordinates upperBorder) {
        this.upperBorder = upperBorder;
        this.vacantCoords = this.generateCoords(upperBorder);
        this.subscribers = new HashSet<>();
    }

    private Set<Coordinates> generateCoords(Coordinates upperBorder) {
        Set<Coordinates> coords = new HashSet<>();
        for (int i = 0; i < upperBorder.x; i++) {
            for (int j = 0; j < upperBorder.y; j++) {
                coords.add(new Coordinates(i, j));
            }
        }
        return coords;
    }

    @Override
    public BaseLevel load() {
        Random random = new Random();
        BaseLevel level = new BaseLevel(upperBorder);
        for (EntitySubscriber subscriber : subscribers) {
            level.bindWithGraphics(subscriber);
        }
        registerTanks(level, random);
        registerObstacles(level, random);
        return level;
    }

    private void registerObstacles(BaseLevel level, Random random) {
        for (int i = 0; i < vacantCoords.size(); i++) {
            if (random.nextDouble() < TREE_PROBABILITY) {
                level.registerEntity(new Obstacle(getRandomCoordinates(random)), EntityConfig.GREEN_TREE_IMAGE_PATH);
            }
        }
    }

    private void registerTanks(BaseLevel level, Random random) {
        level.registerEntity(new Tank(getRandomCoordinates(random), PlayerTypes.PLAYER), EntityConfig.BLUE_TANK_IMAGE_PATH);
        for (int i = 0; i < AI_NUMBER; i++) {
            level.registerEntity(new Tank(getRandomCoordinates(random), PlayerTypes.SIMPLE_AI), EntityConfig.BLUE_TANK_IMAGE_PATH);
        }
    }

    private Coordinates getRandomCoordinates(Random random) {
        if (vacantCoords.isEmpty()) return null;

        Iterator<Coordinates> iterator = vacantCoords.iterator();
        int randomIndex = random.nextInt(vacantCoords.size());
        for (int i = 0; i < randomIndex; i++) {
            iterator.next();
        }
        Coordinates randomCoords = iterator.next();
        iterator.remove();
        return randomCoords;
    }

}
