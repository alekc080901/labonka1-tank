package ru.mipt.bit.platformer.game.core.logic;

import ru.mipt.bit.platformer.game.core.entity.Bullet;
import ru.mipt.bit.platformer.game.core.entity.Tank;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;

public class BulletMoveLogic extends BasicMoveLogic {

    private final Bullet bullet;
    private BaseLevel level;

    public BulletMoveLogic(Bullet bullet, float speed) {
        super(bullet, speed);
        this.bullet = bullet;
    }

    @Override
    public void makeMove(float rotation, BaseLevel level) {
        this.level = level;
        super.makeMove(rotation, level);
    }

    @Override
    public void updateProgress(float deltaTime) {
        if (level == null) {
            throw new IllegalStateException("You can not update progress of Bullet until .makeMove()");
        }
        if (level.hasTank(bullet.getShooter(), bullet.getCoordinates())) {
            Tank targetTank = level.getTankAt(bullet.getCoordinates());
            targetTank.hurt(bullet.getDamage());
            finishMove();
            level.deleteEntity(bullet);
            return;
        }
        if (level.hasObstacle(bullet.getCoordinates()) || level.isOutOfBounds(bullet.getCoordinates())) {
            finishMove();
            level.deleteEntity(bullet);
            return;
        }
        super.updateProgress(deltaTime);
    }

    @Override
    protected boolean canMove(BaseLevel level) {
        return true;
    }

    @Override
    public void finishMove() {
        bullet.setCoordinates(super.getDestination());
        movementProgress -= 1f;
        setDestination(bullet.yetAnotherStepForward());
    }

}
