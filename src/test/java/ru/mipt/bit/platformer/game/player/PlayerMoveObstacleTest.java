package ru.mipt.bit.platformer.game.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.core.entity.Obstacle;
import ru.mipt.bit.platformer.game.core.entity.Tank;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMoveObstacleTest {
    private static final int MAP_SIZE_WIDTH = 10;
    private static final int MAP_SIZE_HEIGHT = 6;

    static Tank tank;
    static BaseLevel level;

    @BeforeAll
    static void beforeAll() {
        Obstacle obstacle1 = new Obstacle(new Coordinates(1, 3));
        Obstacle obstacle2 = new Obstacle(new Coordinates(3, 3));

        tank = new Tank(new Coordinates(1, 4), PlayerTypes.PLAYER);
        Set<Obstacle> obstacles = Set.of(obstacle1, obstacle2);
        Set<Tank> players = Set.of(tank);
        level = new BaseLevel(players, obstacles, new Coordinates(MAP_SIZE_WIDTH, MAP_SIZE_HEIGHT));
    }

    @Test
    void moveVertical() {
        tank.setCoordinates(new Coordinates(1, 2));
        tank.move(MoveCommand.UP, level);
        assertEquals(new Coordinates(1, 3), tank.getCoordinates());
        assertEquals(90f, tank.getRotation());
    }

    @Test
    void moveHorizontal() {
        tank.setCoordinates(new Coordinates(0, 3));
        tank.move(MoveCommand.RIGHT, level);
        assertEquals(new Coordinates(1, 3), tank.getCoordinates());
        assertEquals(0f, tank.getRotation());
    }

    @Test
    void moveObstacleAround() {
        tank.setCoordinates(new Coordinates(2, 3));
        tank.move(MoveCommand.DOWN, level);
        assertEquals(new Coordinates(2, 2), tank.getCoordinates());
        assertEquals(-90f, tank.getRotation());
    }

    @Test
    void moveOutOfBoundsZero() {
        var initCoords = new Coordinates(0, 3);
        tank.setCoordinates(initCoords);
        tank.move(MoveCommand.LEFT, level);
        assertEquals(initCoords, tank.getCoordinates());
        assertEquals(-180f, tank.getRotation());
    }

    @Test
    void moveOutOfBoundsWidth() {
        var initCoords = new Coordinates(MAP_SIZE_WIDTH - 1, 3);
        tank.setCoordinates(initCoords);
        tank.move(MoveCommand.RIGHT, level);
        assertEquals(initCoords, tank.getCoordinates());
        assertEquals(0f, tank.getRotation());
    }

    @Test
    void moveOutOfBoundsHeight() {
        var initCoords = new Coordinates(2, MAP_SIZE_HEIGHT - 1);
        tank.setCoordinates(initCoords);
        tank.move(MoveCommand.UP, level);
        assertEquals(initCoords, tank.getCoordinates());
        assertEquals(90f, tank.getRotation());
    }
}