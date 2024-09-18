package ru.mipt.bit.platformer.game.player;

import ru.mipt.bit.platformer.game.controls.MoveCommand;
import ru.mipt.bit.platformer.game.level.LevelEntity;
import ru.mipt.bit.platformer.game.level.Point;

import java.util.List;

public class PlayerMoveLogic {
    /*
    Класс, ответственный за перемещение игрока по полю (логическое). Также следит, чтобы игрок не врезался в препятствия.
     */

    private final Player player;
    private Point playerDestination;
    private final List<LevelEntity> obstacles;

    public PlayerMoveLogic(Player player, List<LevelEntity> obstacles) {
        this.player = player;
        this.obstacles = obstacles;

        this.playerDestination = player.getCoordinates();
    }

    public boolean makeMove(MoveCommand direction) {
        Point oldCoordinates = new Point(playerDestination.x, playerDestination.y);
        movePlayer(direction);
        if (!hasHitObstacle()) {
            return true;
        } else {
            playerDestination = oldCoordinates;
            return false;
        }
    }

    public void confirmMove() {
        player.setCoordinates(playerDestination);
    }

    private boolean hasHitObstacle() {
        for (LevelEntity obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(playerDestination)) {
                return true;
            }
        }
        return false;
    }

    private void movePlayer(MoveCommand direction) {
        player.setRotation(direction.getRotation());
        playerDestination.x += direction.getShiftX();
        playerDestination.y += direction.getShiftY();
    }

    public Point getDestination() {
        return playerDestination;
    }
}