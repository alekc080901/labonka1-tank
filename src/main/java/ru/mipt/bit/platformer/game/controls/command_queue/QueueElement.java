package ru.mipt.bit.platformer.game.controls.command_queue;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.CommandType;
import ru.mipt.bit.platformer.game.core.GameEntity;

public class QueueElement {
    private final GameEntity entity;
    private final Command command;
    private final CommandType type;

    public QueueElement(GameEntity entity, Command command, CommandType type) {
        this.entity = entity;
        this.command = command;
        this.type = type;
    }

    public GameEntity getEntity() {
        return entity;
    }

    public Command getCommand() {
        return command;
    }

    public CommandType getType() {
        return type;
    }
}
