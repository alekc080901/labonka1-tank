package ru.mipt.bit.platformer.game.core.logic;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.continueProgress;

public class BasicMoveLogic {
    /*
    Класс, ответственный за перемещение игрока по полю (логическое). Также следит, чтобы игрок не врезался в препятствия.
    Композиция внутри танка, призванная упростить восприятие кода.
     */

    private final MovableEntity entity;
    private final float moveSpeed;
    private boolean isMoving = false;
    protected float movementProgress = 0f;
    private Coordinates destination;


    public BasicMoveLogic(MovableEntity entity, float moveSpeed) {
        this.entity = entity;
        this.moveSpeed = moveSpeed;
        this.destination = entity.getCoordinates().copy();
    }

    public void makeMove(float rotation, BaseLevel level) {
        if (isMoving()) return;

        Coordinates oldCoordinates = entity.getCoordinates().copy();
        moveAndChangeDestination(MoveCommand.getChangeFromRotation(rotation));

        if (canMove(level)) {
            isMoving = true;
        } else {
            destination = oldCoordinates;
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    protected boolean canMove(BaseLevel level) {
        Coordinates destination = entity.getDestination();
        return !level.hasObstacle(destination)
                && !level.occupiedWithEnemyTank(entity, destination)
                && !level.isOutOfBounds(destination);
    }

    public void finishMove() {
        entity.setCoordinates(destination);
        movementProgress -= 1f;
        isMoving = false;
    }

    public float getProgress() {
        return movementProgress;
    }

    public void updateProgress(float deltaTime) {
        movementProgress = calculateProgress(deltaTime);
        if (movementProgress >= 1f) {
            finishMove();
        }
    }

    public void setDestination(Coordinates newDestination) {
        destination = newDestination.copy();
    }

    protected float calculateProgress(float deltaTime) {
        return continueProgress(movementProgress, deltaTime, moveSpeed);
    }

    public Coordinates getDestination() {
        return destination;
    }

    private void moveAndChangeDestination(Coordinates target) {
        destination = destination.add(target);
    }

    public float getSpeed() {
        return moveSpeed;
    }
}
