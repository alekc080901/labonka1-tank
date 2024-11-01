package ru.mipt.bit.platformer.game.core;

public interface KillableEntity extends GameEntity {
    /*
    Некий объект игры, способный к перемещению.
     */
    void hurt(double damage);
    void repair(double health);
    float getCurrentHealth();
    float getMaxHealth();
}
