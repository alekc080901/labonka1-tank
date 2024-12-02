package ru.mipt.bit.platformer.game.core.logic;

import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.entity.Tank;

import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.continueProgress;

public class TankMoveLogic extends BasicMoveLogic {

    private final Tank tank;

    public TankMoveLogic(Tank tank) {
        super(tank, tank.getSpeed());
        this.tank = tank;
    }

    @Override
    public void makeMove(float rotation, BaseLevel level) {
        if (!super.isMoving()) {
            tank.turn(rotation);
        }
        super.makeMove(rotation, level);
    }

    @Override
    protected float calculateProgress(float deltaTime) {
        return continueProgress(super.getProgress(), deltaTime, super.getSpeed());
    }
}
