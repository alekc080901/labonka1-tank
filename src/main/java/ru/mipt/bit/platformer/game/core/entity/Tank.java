package ru.mipt.bit.platformer.game.core.entity;

import org.springframework.beans.factory.annotation.Value;
import ru.mipt.bit.platformer.game.controls.input.generators.InstructionGenerator;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.logic.TankMoveLogic;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements MovableEntity, KillableEntity, ShootableEntity, RotatableEntity {
    /*
    Класс танка, который может двигаться и (скоро) стрелять под контролем игрока или ИИшки.
     */
    private final EntityMovePattern movePattern;
    private final float movementSpeed;
    private final float bulletSpeed;
    private final float bulletDamage;
    private final long RECHARGE;

    @Value("${game.entity.tank.z-index}")
    private int zIndex;

    private Coordinates coordinates;
    private float rotation = 0f;

    private final float maxHealth;
    private float currentHealth;
    private final TankMoveLogic moveLogic;
    private InstructionGenerator driver;

    public Tank(Coordinates coordinates, EntityMovePattern movePattern, float movementSpeed,
                float bulletSpeed, float bulletDamage, float maxHealth, long recharge) {
        this.movePattern = movePattern;
        this.movementSpeed = movementSpeed;
        this.bulletSpeed = bulletSpeed;
        this.bulletDamage = bulletDamage;
        this.coordinates = coordinates;
        RECHARGE = recharge;
        this.maxHealth = maxHealth;
        this.moveLogic = new TankMoveLogic(this);

        this.currentHealth = maxHealth;
    }

    public void setDriver(InstructionGenerator driver) {
        this.driver = driver;
    }

    public InstructionGenerator whoDrives() {
        return driver;
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
        return movePattern;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public int getZIndex() {
        return zIndex;
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
        Bullet bullet = level.getEntityFactory().getBullet(this, bulletSpeed, bulletDamage);
        Coordinates nextCoordinate = bullet.yetAnotherStepForward();
        if (level.getAt(nextCoordinate) != null) return;
        bullet.setCoordinates(nextCoordinate);
        bullet.move(rotation, level);
        level.registerEntity(bullet, level.getEntityFactory().getGraphicPath(bullet), AbstractSound.SHOOT_SOUND);
    }

    @Override
    public long getRecharge() {
        return RECHARGE;
    }

    public float getSpeed() {
        return movementSpeed;
    }
}
