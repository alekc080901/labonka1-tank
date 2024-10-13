package ru.mipt.bit.platformer.data;

import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.Obstacle;
import ru.mipt.bit.platformer.game.core.Tank;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class RandomGeneratorLoader implements MapLoader {
    private static final double TREE_PROBABILITY = 0.3;
    private static final int MAP_WIDTH = 10;
    private static final int MAP_HEIGHT = 8;
    private final Set<Tank> tanks = new HashSet<>();
    private final Set<Obstacle> obstacles = new HashSet<>();
    private final Set<Coordinates> vacantCoords;

    public RandomGeneratorLoader(Coordinates upperBorder) {
        this.vacantCoords = this.generateCoords(upperBorder);
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
    public BaseLevel load(String seed) {
        Random random = new Random(seed.hashCode());
        addPlayers(random);
        addObstacles(random);
        return new BaseLevel(tanks, obstacles, new Coordinates(MAP_WIDTH, MAP_HEIGHT));
    }

    public BaseLevel load() {
        Random random = new Random();
        addPlayers(random);
        addObstacles(random);
        return new BaseLevel(tanks, obstacles, new Coordinates(MAP_WIDTH, MAP_HEIGHT));
    }

    private void addObstacles(Random random) {
        for (int i = 0; i < vacantCoords.size(); i++) {
            if (random.nextDouble() < TREE_PROBABILITY) {
                obstacles.add(new Obstacle(getRandomCoordinates(random)));
            }
        }
    }

    private void addPlayers(Random random) {
        tanks.add(new Tank(getRandomCoordinates(random)));
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
