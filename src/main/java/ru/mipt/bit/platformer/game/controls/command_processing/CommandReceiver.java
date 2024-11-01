package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.GameEntity;
import ru.mipt.bit.platformer.game.core.MovableEntity;
import ru.mipt.bit.platformer.game.graphics.contracts.Renderers;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class CommandReceiver {
    /*
    Класс, ответственный за обработку и делегирование всех приходящих команд на объект сущности.
     */

    private final Renderers renderers;
    private final GameEntity entity;

    public CommandReceiver(GameEntity gameEntity, Renderers renderers) {
        this.entity = gameEntity;
        this.renderers = renderers;
    }

    public void handleCommand(Command command, float deltaTime) {
        if (command instanceof MoveCommand) {
            ((MoveCommand) command).bind((MovableEntity) entity, renderers.levelRenderer().getSource());
        }
        command.execute();
        onAnyCommand(deltaTime);
    }

    private void onAnyCommand(float deltaTime) {
        if (entity instanceof MovableEntity ) {
            ((MovableEntity) entity).updateProgress(deltaTime);
            renderers.moveRenderer().shiftEntity((MovableEntity) entity);
            renderers.moveRenderer().turnEntity(entity);
        }
    }
}
