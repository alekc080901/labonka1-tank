package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.CommandType;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.graphic_contracts.Renderers;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class CommandReceiver {
    /*
    Класс, ответственный за обработку и делегирование всех приходящих команд на объект сущности.
     */
    private final CommandQueue commandQueue;
    private final Renderers renderers;

    public CommandReceiver(CommandQueue commandQueue, Renderers renderers) {
        this.commandQueue = commandQueue;
        this.renderers = renderers;
    }

    public void processAll(float deltaTime) {
        while (!commandQueue.isEmpty()) {
            handleCommand(deltaTime);
        }
    }

    private void handleCommand(float deltaTime) {
        QueueElement queueElement = commandQueue.pollCommand();
        queueElement.getCommand().execute();
        onAnyCommand(queueElement, deltaTime);
    }

    private void onAnyCommand(QueueElement queueElement, float deltaTime) {
        if (queueElement.getEntity() instanceof MovableEntity) {
            MovableEntity movableEntity = (MovableEntity) queueElement.getEntity();

            if (movableEntity.getMoveProgress() == 1f) {
                movableEntity.stopMoving();
            }
            movableEntity.updateMoveProgress(deltaTime);
            renderers.moveRenderer().shiftEntity(movableEntity);
            renderers.moveRenderer().turnEntity(movableEntity);
        }
    }

    public static CommandReceiver initCommandReceiver(CommandQueue queue, Renderers renderers) {
        return new CommandReceiver(queue, renderers);
    }
}
