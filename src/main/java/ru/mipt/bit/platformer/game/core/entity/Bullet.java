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
    private final EntityMovePattern movePattern;
    private final ShootableEntity shooter;
    private float rotation;
    private final BasicMoveLogic moveLogic;
    private Coordinates coordinates;
    private final float damage;
    private final int zIndex;

    public Bullet(ShootableEntity shooter, float bulletSpeed, EntityMovePattern movePattern, String explosionPath,
                  float damage, int zIndex) {
        this.movePattern = movePattern;
        this.rotation = shooter.getRotation();
        this.coordinates = shooter.getCoordinates();
        this.shooter = shooter;
        this.moveLogic = new BulletMoveLogic(this, bulletSpeed, explosionPath);
        this.damage = damage;
        this.zIndex = zIndex;
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
        return zIndex;
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
        return movePattern;
    }

    public float getDamage() {
        return damage;
    }
}
