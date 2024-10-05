package ru.mipt.bit.platformer.game.player;

import ru.mipt.bit.platformer.game.controls.MoveCommand;
import ru.mipt.bit.platformer.game.entities.Coordinates;
import ru.mipt.bit.platformer.game.entities.GameEntity;

import java.util.List;

public class PlayerMoveLogic {
    /*
    Класс, ответственный за перемещение игрока по полю (логическое). Также следит, чтобы игрок не врезался в препятствия.
     */

    private final Player player;
    private final List<GameEntity> obstacles;

    public PlayerMoveLogic(Player player, List<GameEntity> obstacles) {
        this.player = player;
        this.obstacles = obstacles;
    }

    public boolean startMove(MoveCommand direction) {
        Coordinates oldCoordinates = new Coordinates(player.getCoordinates());
        moveAndChangeDestination(direction);
        if (!hasHitObstacle()) {
            return true;
        } else {
            player.setDestination(oldCoordinates);
            return false;
        }
    }

    public void finishMove() {
        player.setCoordinates(player.getDestination());
    }

    private boolean hasHitObstacle() {
        for (GameEntity obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(player.getDestination())) {
                return true;
            }
        }
        return false;
    }

    private void moveAndChangeDestination(MoveCommand direction) {
        var coords = player.getDestination();
        coords.x += direction.getShiftX();
        coords.y += direction.getShiftY();

        player.setRotation(direction.getRotation());
    }

    public Coordinates getDestination() {
        return player.getDestination();
    }
}
