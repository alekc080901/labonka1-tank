package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.GameEntity;
import ru.mipt.bit.platformer.game.core.MovableEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ru.mipt.bit.platformer.game.controls.commands.CommandType.MOVE;
import static ru.mipt.bit.platformer.game.controls.commands.CommandType.TOGGLE_HEALTH;

public class CommandFactory {

    private final long moveCommandDelay;
    private final long toggleHealthCommandDelay;
    private final Map<Object, Long> lastCommandExecuted = new HashMap<>();

    public CommandFactory(long moveCommandDelay, long toggleHealthCommandDelay) {
        this.moveCommandDelay = moveCommandDelay;
        this.toggleHealthCommandDelay = toggleHealthCommandDelay;
    }

    public Command fromInstruction(BaseLevel level, GameEntity entity, InputInstruction instruction) {
        CommandType commandType = CommandType.getType(instruction);
        Command command;
        long delay;
        switch (commandType) {
            case MOVE:
                command = new MoveCommand(level, (MovableEntity) entity, instruction);
                delay = moveCommandDelay;
                break;
            case TOGGLE_HEALTH:
                command = new ToggleHealthCommand();
                delay = toggleHealthCommandDelay;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + CommandType.getType(instruction));
        }
        if (isRecharging(commandType, entity, delay)) {
            return new NoneCommand();
        }
        refreshTimer(commandType, entity);
        return command;
    }

    private void refreshTimer(CommandType commandType, GameEntity entity) {
        lastCommandExecuted.put(new Key(commandType, entity), System.currentTimeMillis());
    }

    private boolean isRecharging(CommandType commandType, GameEntity entity, long delay) {
        long hasPassed = System.currentTimeMillis() - lastCommandExecuted.getOrDefault(new Key(commandType, entity), 0L);
        return hasPassed < delay;
    }

    class Key {
        final CommandType commandType;
        final GameEntity entity;

        public Key(CommandType commandType, GameEntity entity) {
            this.commandType = commandType;
            this.entity = entity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return Objects.equals(commandType, key.commandType) && Objects.equals(entity, key.entity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(commandType, entity);
        }
    }
}
