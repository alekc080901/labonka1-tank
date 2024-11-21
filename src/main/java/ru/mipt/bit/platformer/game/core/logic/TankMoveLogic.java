package ru.mipt.bit.platformer.game.core.logic;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.entity.Tank;

import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.continueProgress;

public class TankMoveLogic extends MoveLogic {
    /*
    Класс, ответственный за перемещение игрока по полю (логическое). Также следит, чтобы игрок не врезался в препятствия.
    Композиция внутри танка, призванная упростить восприятие кода.
     */

    private final Tank tank;

    public TankMoveLogic(Tank tank) {
        super(tank, tank.getSpeed());
        this.tank = tank;
    }

    @Override
    public void makeMove(MoveCommand direction, BaseLevel level) {
        if (!super.isMoving()) {
            tank.turn(direction.getRotation());
        }
        super.makeMove(direction, level);
    }

    @Override
    protected float calculateProgress(float deltaTime) {
        return continueProgress(super.getProgress(), deltaTime, super.getSpeed());
    }
}
