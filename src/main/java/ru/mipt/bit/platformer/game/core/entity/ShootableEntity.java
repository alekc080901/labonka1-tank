package ru.mipt.bit.platformer.game.core.entity;

import ru.mipt.bit.platformer.game.core.level.BaseLevel;

public interface ShootableEntity {
    void shoot(BaseLevel level);
//    void onCollide(BaseLevel level);
    long getRecharge();
}
