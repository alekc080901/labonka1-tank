package ru.mipt.bit.platformer.game.core;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.continueProgress;

public class Tank implements MovableEntity, KillableEntity {
    /*
    Класс танка, который может двигаться и (скоро) стрелять под контролем игрока или ИИшки.
     */
    private static final float MOVEMENT_SPEED = 0.5f;

    private Coordinates coordinates;
    private Coordinates destination;
    private float rotation = 0f;

    private final float maxHealth = 100f;
    private float currentHealth = maxHealth - 20;  // -20 временно до возможности нанести урон

    private final TankMoveLogic moveLogic;
    private final PlayerTypes whoDrives;

    public Tank(Coordinates coordinates, PlayerTypes whoDrives) {
        this.coordinates = coordinates;
        this.destination = coordinates;
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

    public void setDestination(Coordinates destination) {
        this.destination = new Coordinates(destination);
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates.x, coordinates.y);
    }

    public Coordinates getDestination() {
        return destination;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public void turn(float direction) {
        this.rotation = direction;
    }

    @Override
    public void move(MoveCommand command, BaseLevel level) {
        if (!isEqual(moveLogic.getProgress(), 1f)) {
            return;
        }
        // Finish previous move (do nothing if move hasn't been started)
        moveLogic.finishMove();

        boolean hasStarted = moveLogic.startMove(command, level);
        // Rotation for playerMoveLogic and player on screen are different entities
        if (hasStarted) {
            moveLogic.setProgress(0f);
        }
    }

    @Override
    public void updateProgress(float deltaTime) {
        float playerMovementProgress = continueProgress(moveLogic.getProgress(), deltaTime, MOVEMENT_SPEED);
        moveLogic.setProgress(playerMovementProgress);
    }

    @Override
    public float getProgress() {
        return moveLogic.getProgress();
    }

    @Override
    public void hurt(double damage) {
        currentHealth -= Math.min(0, damage);
    }

    @Override
    public void repair(double health) {
        currentHealth += Math.max(health, maxHealth);
    }

    @Override
    public float getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public float getMaxHealth() {
        return maxHealth;
    }
}
