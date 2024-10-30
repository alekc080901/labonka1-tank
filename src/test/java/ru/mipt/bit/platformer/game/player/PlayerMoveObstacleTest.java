package ru.mipt.bit.platformer.game.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMoveObstacleTest {
    private static final int MAP_SIZE_WIDTH = 10;
    private static final int MAP_SIZE_HEIGHT = 6;

    static Tank player;
    static TankMoveLogic moveLogic;

    @BeforeAll
    static void beforeAll() {
        Obstacle obstacle1 = new Obstacle(new Coordinates(1, 3));
        Obstacle obstacle2 = new Obstacle(new Coordinates(3, 3));

        player = new Tank(new Coordinates(1, 4));
        Set<Obstacle> obstacles = Set.of(obstacle1, obstacle2);
        Set<Tank> players = Set.of(player);
        BaseLevel level = new BaseLevel(players, obstacles, new Coordinates(MAP_SIZE_WIDTH, MAP_SIZE_HEIGHT));

        moveLogic = new TankMoveLogic(player, level);
    }

    @Test
    void moveVertical() {
        moveLogic.setPlayerCoordinates(new Coordinates(1, 2));
        assertFalse(moveLogic.startMove(MoveCommand.UP));
        moveLogic.finishMove();
        assertEquals(new Coordinates(1, 2), player.getCoordinates());
        assertEquals(90f, player.getRotation());
    }

    @Test
    void moveHorizontal() {
        moveLogic.setPlayerCoordinates(new Coordinates(0, 3));
        assertFalse(moveLogic.startMove(MoveCommand.RIGHT));
        moveLogic.finishMove();
        assertEquals(new Coordinates(0, 3), player.getCoordinates());
        assertEquals(0f, player.getRotation());
    }

    @Test
    void moveObstacleAround() {
        moveLogic.setPlayerCoordinates(new Coordinates(2, 3));
        assertTrue(moveLogic.startMove(MoveCommand.DOWN));
        moveLogic.finishMove();
        assertEquals(new Coordinates(2, 2), player.getCoordinates());
        assertEquals(-90f, player.getRotation());
    }

    @Test
    void moveOutOfBoundsZero() {
        var initCoords = new Coordinates(0, 3);
        moveLogic.setPlayerCoordinates(initCoords);
        assertFalse(moveLogic.startMove(MoveCommand.LEFT));
        moveLogic.finishMove();
        assertEquals(initCoords, player.getCoordinates());
        assertEquals(-180f, player.getRotation());
    }

    @Test
    void moveOutOfBoundsWidth() {
        var initCoords = new Coordinates(MAP_SIZE_WIDTH - 1, 3);
        moveLogic.setPlayerCoordinates(initCoords);
        assertFalse(moveLogic.startMove(MoveCommand.RIGHT));
        moveLogic.finishMove();
        assertEquals(initCoords, player.getCoordinates());
        assertEquals(0f, player.getRotation());
    }

    @Test
    void moveOutOfBoundsHeight() {
        var initCoords = new Coordinates(2, MAP_SIZE_HEIGHT - 1);
        moveLogic.setPlayerCoordinates(initCoords);
        assertFalse(moveLogic.startMove(MoveCommand.UP));
        moveLogic.finishMove();
        assertEquals(initCoords, player.getCoordinates());
        assertEquals(90f, player.getRotation());
    }
}