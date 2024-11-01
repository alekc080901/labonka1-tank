package ru.mipt.bit.platformer.game.core;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;

public class TankMoveLogic {
    /*
    Класс, ответственный за перемещение игрока по полю (логическое). Также следит, чтобы игрок не врезался в препятствия.
    Композиция внутри танка, призванная упростить восприятие кода.
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

        if (!level.hasHitObstacle(tank) && !level.hasHitTank(tank) && !level.hasTrespassedMap(tank)) {
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

    private void moveAndChangeDestination(MoveCommand direction) {
        var coords = tank.getDestination();
        coords.x += direction.getShiftX();
        coords.y += direction.getShiftY();
    }
}
