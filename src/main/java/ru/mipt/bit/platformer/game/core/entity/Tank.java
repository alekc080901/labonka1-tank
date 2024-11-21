package ru.mipt.bit.platformer.game.core.entity;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.PlayerTypes;
import ru.mipt.bit.platformer.game.core.logic.TankMoveLogic;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements MovableEntity, KillableEntity, ShootableEntity, RotatableEntity {
    /*
    Класс танка, который может двигаться и (скоро) стрелять под контролем игрока или ИИшки.
     */
    private static final float MOVEMENT_SPEED = 0.5f;

    private Coordinates coordinates;
    private float rotation = 0f;

    private final float maxHealth = 100f;
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
    public float getRotation() {
        return rotation;
    }

    @Override
    public void turn(float direction) {
        this.rotation = direction;
    }

    @Override
    public void move(MoveCommand command, BaseLevel level) {
        moveLogic.makeMove(command, level);
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

    @Override
    public void shoot() {

    }

    public float getSpeed() {
        return MOVEMENT_SPEED;
    }
}
