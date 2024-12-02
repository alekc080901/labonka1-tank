package ru.mipt.bit.platformer.game.controls.command_queue;

import ru.mipt.bit.platformer.game.controls.input.generators.InstructionGenerator;
import ru.mipt.bit.platformer.game.controls.input.generators.PlayerInstructionGenerator;
import ru.mipt.bit.platformer.game.controls.input.generators.SimpleAIInstructionGenerator;
import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.CommandFactory;
import ru.mipt.bit.platformer.game.controls.commands.CommandType;
import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.PlayerTypes;
import ru.mipt.bit.platformer.game.core.entity.Tank;

import java.util.*;
import java.util.stream.Collectors;

public class CommandPublisher {
    /*
    Класс, принимающий команды от игрока (кем бы он ни был, не обязательно человека) и передающий их в игру.
     */
    private final Set<InstructionGenerator> instructionSources = new HashSet<>();
    private final CommandQueue commandQueue;
    private final BaseLevel level;
    private final CommandFactory commandFactory;


    public CommandPublisher(CommandQueue commandQueue, BaseLevel level, CommandFactory commandFactory) {
        this.commandQueue = commandQueue;
        this.level = level;
        this.commandFactory = commandFactory;
    }

    public void register(InstructionGenerator instructionGenerator) {
        instructionSources.add(instructionGenerator);
    }

    public void publishAll() {
        for (InstructionGenerator instructionGenerator : instructionSources) {
            Collection<InputInstruction> instructions = instructionGenerator.getInstructions();
            pushCommandsToQueue(instructionGenerator.getInstructionSource(), instructions);
        }
    }

    private void pushCommandsToQueue(GameEntity gameEntity, Collection<InputInstruction> userInstructions) {
        Set<QueueElement> commandsFromInstructions = userInstructions.stream()
                .map(instruction -> {
                    Command command = commandFactory.fromInstruction(level, gameEntity, instruction);
                    return new QueueElement(gameEntity, command, CommandType.get(instruction));
                })
                .collect(Collectors.toSet());
//        if (commandsFromInstructions.isEmpty()) {
//            var emptyCommandElement = new QueueElement(gameEntity, new NoneCommand(), CommandType.NONE);
//            commandsFromInstructions.add(emptyCommandElement);
//        }
        commandQueue.addCommands(commandsFromInstructions);
    }

    public static CommandPublisher initCommandPublisher(CommandQueue commandQueue, BaseLevel level) {
        Set<Tank> tanks = level.getTanks();

        CommandFactory commandFactory = new CommandFactory(0, 200);
        CommandPublisher commandPublisher = new CommandPublisher(commandQueue, level, commandFactory);
        for (Tank tank : tanks) {
            commandPublisher.register(
                    tank.whoDrives() == PlayerTypes.PLAYER ? new PlayerInstructionGenerator(tank) :
                            new SimpleAIInstructionGenerator(tank)
            );
        }
        return commandPublisher;
    }
}
