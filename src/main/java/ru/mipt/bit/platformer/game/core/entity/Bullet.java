package ru.mipt.bit.platformer.game.core.entity;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.logic.BulletMoveLogic;
import ru.mipt.bit.platformer.game.core.logic.BasicMoveLogic;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Bullet implements MovableEntity {
    /*
    Класс танка, который может двигаться и (скоро) стрелять под контролем игрока или ИИшки.
     */
    private static final EntityMovePattern MOVE_PATTERN = EntityMovePattern.SIMPLE;
    private final ShootableEntity shooter;
    private final float rotation;
    private final BasicMoveLogic moveLogic;
    private Coordinates coordinates;
    private final float damage;
    private final static int Z_INDEX = 1;

    public Bullet(ShootableEntity shooter, float bulletSpeed, float damage) {
        this.rotation = shooter.getRotation();
        this.coordinates = shooter.getCoordinates();
        this.shooter = shooter;
        this.moveLogic = new BulletMoveLogic(this, bulletSpeed);
        this.damage = damage;
    }

    public Coordinates yetAnotherStepForward() {
        return coordinates.add(MoveCommand.getChangeFromRotation(rotation));
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates.copy();
        moveLogic.setDestination(coordinates);
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
    public void move(float rotation, BaseLevel level) {
        moveLogic.makeMove(rotation, level);
    }

    public ShootableEntity getShooter() {
        return shooter;
    }

    @Override
    public boolean isMoving() {
        return moveLogic.isMoving();
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
    public Coordinates getDestination() {
        return moveLogic.getDestination();
    }

    @Override
    public EntityMovePattern getMovePattern() {
        return MOVE_PATTERN;
    }

    public float getDamage() {
        return damage;
    }
}
