package ru.mipt.bit.platformer.game.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerMovePositiveTest {

    static Tank player;
    static PlayerMoveLogic moveLogic;

    @BeforeAll
    static void beforeAll() {
        Obstacle obstacle1 = new Obstacle(new Coordinates(1, 3));
        Obstacle obstacle2 = new Obstacle(new Coordinates(3, 3));

        player = new Tank(new Coordinates(1, 4));
        Set<Obstacle> obstacles = Set.of(obstacle1, obstacle2);
        Set<Tank> players = Set.of(player);
        BaseLevel level = new BaseLevel(players, obstacles, new Coordinates(10, 10));

        moveLogic = new PlayerMoveLogic(player, level);
    }

    @Test
    void movePositiveUp() {
        moveLogic.setPlayerCoordinates(new Coordinates(2, 2));
        assertTrue(moveLogic.startMove(MoveCommand.UP));
        moveLogic.finishMove();
        assertEquals(new Coordinates(2, 3), player.getCoordinates());
        assertEquals(90f, player.getRotation());
    }

    @Test
    void movePositiveDown() {
        moveLogic.setPlayerCoordinates(new Coordinates(0, 1));
        assertTrue(moveLogic.startMove(MoveCommand.DOWN));
        moveLogic.finishMove();
        assertEquals(new Coordinates(0, 0), player.getCoordinates());
        assertEquals(-90f, player.getRotation());
    }

    @Test
    void movePositiveLeft() {
        moveLogic.setPlayerCoordinates(new Coordinates(1, 4));
        assertTrue(moveLogic.startMove(MoveCommand.LEFT));
        moveLogic.finishMove();
        assertEquals(new Coordinates(0, 4), player.getCoordinates());
        assertEquals(-180f, player.getRotation());
    }

    @Test
    void movePositiveRight() {
        moveLogic.setPlayerCoordinates(new Coordinates(5, 4));
        assertTrue(moveLogic.startMove(MoveCommand.RIGHT));
        moveLogic.finishMove();
        assertEquals(new Coordinates(6, 4), player.getCoordinates());
        assertEquals(0f, player.getRotation());
    }
}