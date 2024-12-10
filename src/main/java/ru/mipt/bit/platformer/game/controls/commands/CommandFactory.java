package ru.mipt.bit.platformer.game.controls.commands;

import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.data.GameSettings;
import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;
import ru.mipt.bit.platformer.game.core.entity.ShootableEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandFactory {
    private final long moveCommandDelay;
    private final long toggleHealthCommandDelay;
    private final GameSettings settings;
    private final Map<InputInstruction, CommandType> accordingTypesMap;
    private final Map<Object, Long> lastCommandExecuted = new HashMap<>();

    public CommandFactory(long moveCommandDelay,
                          long toggleHealthCommandDelay,
                          GameSettings settings,
                          Map<InputInstruction, CommandType> accordingTypesMap) {
        this.moveCommandDelay = moveCommandDelay;
        this.toggleHealthCommandDelay = toggleHealthCommandDelay;
        this.accordingTypesMap = accordingTypesMap;
        this.settings = settings;
    }

    public Command fromInstruction(BaseLevel level, GameEntity entity, InputInstruction instruction) {
        CommandType commandType = accordingTypesMap.get(instruction);
        Command command;
        long delay;
        switch (commandType) {
            case MOVE:
                command = new MoveCommand(level, (MovableEntity) entity, instruction);
                delay = moveCommandDelay;
                break;
            case TOGGLE_HEALTH:
                command = new ToggleHealthCommand(settings);
                delay = toggleHealthCommandDelay;
                break;
            case SHOOT:
                ShootableEntity shootableEntity = (ShootableEntity) entity;
                command = new ShootCommand(level, shootableEntity);
                delay = shootableEntity.getRecharge();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + accordingTypesMap.get(instruction));
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
