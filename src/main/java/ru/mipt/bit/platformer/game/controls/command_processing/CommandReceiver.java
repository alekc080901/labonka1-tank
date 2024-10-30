package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.GameEntity;
import ru.mipt.bit.platformer.game.core.MovableEntity;
import ru.mipt.bit.platformer.game.core.TankMoveLogic;
import ru.mipt.bit.platformer.game.graphics.contracts.ActionRenderer;
import ru.mipt.bit.platformer.game.graphics.contracts.MoveRenderer;
import ru.mipt.bit.platformer.game.graphics.contracts.Renderers;

import javax.swing.*;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class CommandReceiver {
    /*
    Класс, ответственный за отображение перемещения игрока на уровне. Обертка над логикой PlayerMoveLogic для рендера
    текстуры игрока на поле.
     */

    private final Renderers renderers;
    private final GameEntity entity;

    public CommandReceiver(GameEntity gameEntity, Renderers renderers) {
        this.entity = gameEntity;
        this.renderers = renderers;
    }

    public void handleCommand(Command command, float deltaTime) {
        if (command instanceof MoveCommand) {
            ((MovableEntity) entity).move((MoveCommand) command, renderers.levelRenderer().getSource());
            renderers.moveRenderer().turnEntity(entity);
        }

        if (entity instanceof MovableEntity ) {
            ((MovableEntity) entity).updateProgress(deltaTime);
            renderers.moveRenderer().shiftEntity((MovableEntity) entity);
        }
    }
}
