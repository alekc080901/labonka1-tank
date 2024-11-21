package ru.mipt.bit.platformer.game.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.core.entity.Obstacle;
import ru.mipt.bit.platformer.game.core.entity.Tank;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerMovePositiveTest {

    static Tank tank;
    static BaseLevel level;

    @BeforeAll
    static void beforeAll() {
        Obstacle obstacle1 = new Obstacle(new Coordinates(1, 3));
        Obstacle obstacle2 = new Obstacle(new Coordinates(3, 3));

        tank = new Tank(new Coordinates(1, 4), PlayerTypes.PLAYER);
        Set<Obstacle> obstacles = Set.of(obstacle1, obstacle2);
        Set<Tank> players = Set.of(tank);
        level = new BaseLevel(players, obstacles, new Coordinates(10, 10));
    }

    @Test
    void movePositiveUp() {
        tank.setCoordinates(new Coordinates(2, 2));
        tank.move(MoveCommand.UP, level);
        assertEquals(new Coordinates(2, 3), tank.getCoordinates());
        assertEquals(90f, tank.getRotation());
    }

    @Test
    void movePositiveDown() {
        tank.setCoordinates(new Coordinates(2, 2));
        tank.move(MoveCommand.DOWN, level);
        assertEquals(new Coordinates(0, 0), tank.getCoordinates());
        assertEquals(-90f, tank.getRotation());
    }

    @Test
    void movePositiveLeft() {
        tank.setCoordinates(new Coordinates(2, 2));
        tank.move(MoveCommand.LEFT, level);
        assertEquals(new Coordinates(0, 4), tank.getCoordinates());
        assertEquals(-180f, tank.getRotation());
    }

    @Test
    void movePositiveRight() {
        tank.setCoordinates(new Coordinates(2, 2));
        tank.move(MoveCommand.RIGHT, level);
        assertEquals(new Coordinates(6, 4), tank.getCoordinates());
        assertEquals(0f, tank.getRotation());
    }
}