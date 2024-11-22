package ru.mipt.bit.platformer.game.core.logic;

import ru.mipt.bit.platformer.game.core.entity.Bullet;

public class BulletMoveLogic extends MoveLogic {

    private final Bullet bullet;

    public BulletMoveLogic(Bullet bullet, float speed) {
        super(bullet, speed);
        this.bullet = bullet;
    }

    @Override
    public void finishMove() {
        bullet.setCoordinates(super.getDestination());
        movementProgress -= 1f;
        super.setDestination(bullet.yetAnotherStepForward());
    }
}
