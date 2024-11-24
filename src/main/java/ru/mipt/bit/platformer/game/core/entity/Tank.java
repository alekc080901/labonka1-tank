package ru.mipt.bit.platformer.game.core.entity;

import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.PlayerTypes;
import ru.mipt.bit.platformer.game.core.logic.TankMoveLogic;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements MovableEntity, KillableEntity, ShootableEntity, RotatableEntity {
    /*
    Класс танка, который может двигаться и (скоро) стрелять под контролем игрока или ИИшки.
     */
    private static final EntityMovePattern MOVE_PATTERN = EntityMovePattern.EASE;
    private static final float MOVEMENT_SPEED = EntityConfig.TANK_DEFAULT_SPEED;
    private static final float BULLET_SPEED = EntityConfig.BULLET_DEFAULT_SPEED;
    private static final float BULLET_DAMAGE = EntityConfig.BULLET_DEFAULT_DAMAGE;
    private static final long RECHARGE = 1000;
    private final static int Z_INDEX = 2;

    private Coordinates coordinates;
    private float rotation = 0f;

    private final float maxHealth = EntityConfig.TANK_DEFAULT_HEALTH;
    private float currentHealth = maxHealth - 20;  // -20 временно до возможности нанести урон

    private final TankMoveLogic moveLogic;
    private final PlayerTypes whoDrives;

    public Tank(Coordinates coordinates, PlayerTypes whoDrives) {
        this.coordinates = coordinates;
        this.whoDrives = whoDrives;
        this.moveLogic = new TankMoveLogic(this);
    }

    public PlayerTypes whoDrives() {
        return whoDrives;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean isMoving() {
        return moveLogic.isMoving();
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates.x, coordinates.y);
    }

    public Coordinates getDestination() {
        return moveLogic.getDestination();
    }

    @Override
    public EntityMovePattern getMovePattern() {
        return MOVE_PATTERN;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public int getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void turn(float direction) {
        this.rotation = direction;
    }

    @Override
    public void move(float rotation, BaseLevel level) {
        moveLogic.makeMove(rotation, level);
    }

    @Override
    public void updateProgress(float deltaTime) {
        moveLogic.updateProgress(deltaTime);
    }

    @Override
    public float getProgress() {
        return moveLogic.getProgress();
    }

    @Override
    public void hurt(double damage) {
        currentHealth = (float) Math.max(0, currentHealth - damage);
    }

    @Override
    public void repair(double health) {
        currentHealth = (float) Math.min(currentHealth + health, maxHealth);
    }

    @Override
    public float getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public float getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void shoot(BaseLevel level) {
        Bullet bullet = new Bullet(this, BULLET_SPEED, BULLET_DAMAGE);
        Coordinates nextCoordinate = bullet.yetAnotherStepForward();
        if (level.getAt(nextCoordinate) != null) return;
        bullet.setCoordinates(nextCoordinate);
        bullet.move(rotation, level);
        level.registerEntity(bullet, EntityConfig.BULLET_IMAGE_PATH);
    }

    @Override
    public long getRecharge() {
        return RECHARGE;
    }

    public float getSpeed() {
        return MOVEMENT_SPEED;
    }
}
