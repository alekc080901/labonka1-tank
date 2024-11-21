package ru.mipt.bit.platformer.game.core.logic;

import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.continueProgress;

public class MoveLogic {
    /*
    Класс, ответственный за перемещение игрока по полю (логическое). Также следит, чтобы игрок не врезался в препятствия.
    Композиция внутри танка, призванная упростить восприятие кода.
     */

    private final MovableEntity entity;
    private final float moveSpeed;
    private boolean isMoving = false;
    private float movementProgress = 0f;
    private Coordinates destination;


    public MoveLogic(MovableEntity entity, float moveSpeed) {
        this.entity = entity;
        this.moveSpeed = moveSpeed;
        this.destination = entity.getCoordinates().copy();
    }

    public void makeMove(MoveCommand direction, BaseLevel level) {
        if (isMoving()) return;

        Coordinates oldCoordinates = new Coordinates(entity.getCoordinates());
        moveAndChangeDestination(direction);

        if (canMove(level)) {
            isMoving = true;
        } else {
            destination = oldCoordinates;
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    private boolean canMove(BaseLevel level) {
        return !level.hasHitObstacle(entity) && !level.hasHitTank(entity) && !level.hasTrespassedMap(entity);
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

    protected float calculateProgress(float deltaTime) {
        return movementProgress + deltaTime / moveSpeed;
    }

    public Coordinates getDestination() {
        return destination;
    }

    private void moveAndChangeDestination(MoveCommand direction) {
        destination.x += direction.getShiftX();
        destination.y += direction.getShiftY();
    }

    public float getSpeed() {
        return moveSpeed;
    }
}
