package ru.mipt.bit.platformer.data;

import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.core.entity.*;
import ru.mipt.bit.platformer.game.core.pubsub.Subscriber;
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
    private final Set<Subscriber> subscribers;
    private final GameEntityFactory entityFactory;

    public LevelRandomLoader(Coordinates upperBorder, Set<Subscriber> subscribers, GameEntityFactory entityFactory) {
        this.upperBorder = upperBorder;
        this.vacantCoords = this.generateCoords(upperBorder);
        this.subscribers = subscribers;
        this.entityFactory = entityFactory;
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
        BaseLevel level = new BaseLevel(upperBorder, entityFactory);
        for (Subscriber subscriber : subscribers) {
            level.bindWithGraphics(subscriber);
        }
        registerTanks(level, random);
        registerObstacles(level, random);
        return level;
    }

    private void registerObstacles(BaseLevel level, Random random) {
        for (int i = 0; i < vacantCoords.size(); i++) {
            if (random.nextDouble() < TREE_PROBABILITY) {
                var obstacle = entityFactory.getObstacle(getRandomCoordinates(random));
                level.registerEntity(obstacle, entityFactory.getGraphicPath(obstacle), AbstractSound.EMPTY);
            }
        }
    }

    private void registerTanks(BaseLevel level, Random random) {
        entityFactory.registerTank(level, PlayerType.PLAYER, getRandomCoordinates(random));
        for (int i = 0; i < AI_NUMBER; i++) {
            entityFactory.registerTank(level, PlayerType.SIMPLE_AI, getRandomCoordinates(random));
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
