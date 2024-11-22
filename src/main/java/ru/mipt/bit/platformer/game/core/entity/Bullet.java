package ru.mipt.bit.platformer.game.core.entity;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.logic.BulletMoveLogic;
import ru.mipt.bit.platformer.game.core.logic.MoveLogic;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Bullet implements MovableEntity {
    /*
    Класс танка, который может двигаться и (скоро) стрелять под контролем игрока или ИИшки.
     */
    private static final EntityMovePattern MOVE_PATTERN = EntityMovePattern.SIMPLE;
    private final float rotation;
    private final MoveLogic moveLogic;
    private Coordinates coordinates;

    public Bullet(GameEntity shooter, float bulletSpeed) {
        this.rotation = shooter.getRotation();
        this.coordinates = shooter.getCoordinates();
        this.moveLogic = new BulletMoveLogic(this, bulletSpeed);
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
    public void move(float rotation, BaseLevel level) {
        moveLogic.makeMove(rotation, level);
    }

    public void firstMove(float rotation, BaseLevel level) {
        move(rotation, level);
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
}
