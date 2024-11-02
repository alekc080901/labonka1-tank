package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.controls.commands.NoneCommand;
import ru.mipt.bit.platformer.game.controls.input.InputReceiver;
import ru.mipt.bit.platformer.game.controls.input.KeyboardReceiver;
import ru.mipt.bit.platformer.game.core.GameEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerCommandGenerator implements CommandGenerator {
    /*
    Реализация контракта генерации команд, производимых с подключенных устройств пользователем.
     */
//    private final GameEntity gameEntity;
    private MoveCommand lastMoveCommand;

    private final List<InputReceiver> devices = List.of(
            new KeyboardReceiver()
    );

//    public PlayerCommandGenerator(GameEntity gameEntity) {
//        this.gameEntity = gameEntity;
//    }

    @Override
    public Collection<Command> getCommands() {
        Set<Command> inputtedCommands = new HashSet<>();
        for (InputReceiver device : devices) {
            Collection<Command> chosenCommands = device.receiveInput();

            MoveCommand moveCommand = getMoveCommand(chosenCommands);
            Set<Command> otherCommands = getNonMoveCommands(chosenCommands);
            if (moveCommand != null) {
                otherCommands.add(moveCommand);
            }

            inputtedCommands.addAll(otherCommands);
        }
        if (inputtedCommands.isEmpty()) {
            inputtedCommands.add(new NoneCommand());
        }
        return inputtedCommands;

    }

    private static Set<Command> getNonMoveCommands(Collection<Command> chosenCommands) {
        return chosenCommands.stream().filter(command -> !(command instanceof MoveCommand)).collect(Collectors.toSet());
    }

    private MoveCommand getMoveCommand(Collection<Command> commands) {
        MoveCommand moveCommand = null;
        for (Command command : commands) {
            if (command instanceof MoveCommand) {
                if (command == lastMoveCommand) {
                    return lastMoveCommand;
                }
                moveCommand = (MoveCommand) command;
                lastMoveCommand = moveCommand;
            }
        }
        return moveCommand;
    }


}
