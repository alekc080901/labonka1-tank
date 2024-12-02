package ru.mipt.bit.platformer.game.controls.command_queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.controls.input.generators.InstructionGenerator;
import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.CommandFactory;
import ru.mipt.bit.platformer.game.controls.commands.CommandType;
import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.Tank;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommandPublisher {
    private final CommandQueue commandQueue;
    private final BaseLevel level;
    private final CommandFactory commandFactory;

    @Autowired
    public CommandPublisher(CommandQueue commandQueue, BaseLevel level, CommandFactory commandFactory) {
        this.commandQueue = commandQueue;
        this.level = level;
        this.commandFactory = commandFactory;

        getInputGenerators(level);
    }

    public void publishAll() {
        for (InstructionGenerator instructionGenerator : getInputGenerators(level)) {
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
        commandQueue.addCommands(commandsFromInstructions);
    }

    private Collection<InstructionGenerator> getInputGenerators(BaseLevel level) {
        return level.getTanks().stream()
                .map(Tank::whoDrives)
                .collect(Collectors.toList());
    }
}
