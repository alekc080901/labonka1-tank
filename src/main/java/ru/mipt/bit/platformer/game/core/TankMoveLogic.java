package ru.mipt.bit.platformer.game.core;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;

public class TankMoveLogic {
    /*
    Класс, ответственный за перемещение игрока по полю (логическое). Также следит, чтобы игрок не врезался в препятствия.
     */

    private final Tank tank;
    private float movementProgress = 1f;

    public TankMoveLogic(Tank tank) {
        this.tank = tank;
    }

    public boolean startMove(MoveCommand direction, BaseLevel level) {
        Coordinates oldCoordinates = new Coordinates(tank.getCoordinates());
        tank.turn(direction.getRotation());
        moveAndChangeDestination(direction);
        if (!hasHitObstacle(level) && !hasTrespassedMap(level)) {
            return true;
        } else {
            tank.setDestination(oldCoordinates);
            return false;
        }
    }

    public void finishMove() {
        tank.setCoordinates(tank.getDestination());
    }

    public float getProgress() {
        return movementProgress;
    }

    public void setProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

    private boolean hasHitObstacle(BaseLevel level) {
        for (GameEntity obstacle : level.getObstacles()) {
            if (obstacle.getCoordinates().equals(tank.getDestination())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasTrespassedMap(BaseLevel level) {
        Coordinates coords = tank.getDestination();
        Coordinates mapUpperRightLimit = level.getUpperRightSize();
        return coords.x >= mapUpperRightLimit.x || coords.x < 0 || coords.y >= mapUpperRightLimit.y || coords.y < 0;
    }

    private void moveAndChangeDestination(MoveCommand direction) {
        var coords = tank.getDestination();
        coords.x += direction.getShiftX();
        coords.y += direction.getShiftY();
    }

    public void setPlayerCoordinates(Coordinates coordinates) {
        tank.setDestination(coordinates);
        finishMove();
    }
}
