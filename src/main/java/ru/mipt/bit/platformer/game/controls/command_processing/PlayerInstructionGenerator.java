package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.*;
import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.gdx.input.InputReceiver;
import ru.mipt.bit.platformer.game.gdx.input.KeyboardReceiver;
import ru.mipt.bit.platformer.game.core.GameEntity;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerInstructionGenerator implements InstructionGenerator {
    /*
    Реализация контракта генерации команд, производимых с подключенных устройств пользователем.
     */
    private final GameEntity gameEntity;
    private final Map<CommandType, InputInstruction> lastInstructions = new HashMap<>();


    private final List<InputReceiver> devices = List.of(
            new KeyboardReceiver()
    );

    public PlayerInstructionGenerator(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }

    @Override
    public GameEntity getInstructionSource() {
        return gameEntity;
    }

    @Override
    public Collection<InputInstruction> getInstructions() {
        Set<InputInstruction> inputtedInstructions = new HashSet<>();
        for (InputReceiver device : devices) {
            Collection<InputInstruction> userInstructions = device.receiveInput();
            //
            //
            inputtedInstructions.addAll(userInstructions);
        }

        return filterRepeatedInstructions(inputtedInstructions);

    }

    private Collection<InputInstruction> filterRepeatedInstructions(Set<InputInstruction> instructions) {
        /*
        Убирает лишние команды (повторяющиеся команды одного типа). Если несколько инструкций одного типа,
        выбирается предыдущая введенная команда.
         */
        Set<InputInstruction> filteredInstructions = new HashSet<>();
        Set<InputInstruction> newInstructions = new HashSet<>();
        for (InputInstruction instruction : instructions) {
            if (instruction.equals(getLastInstructionOfType(instruction))) {
                filteredInstructions.add(instruction);
            } else {
                newInstructions.add(instruction);
            }
        }

        var types = filteredInstructions.stream().map(CommandType::getType).collect(Collectors.toSet());
        for (InputInstruction newInstruction : newInstructions) {
            if (!types.contains(CommandType.getType(newInstruction))) {
                filteredInstructions.add(newInstruction);
                lastInstructions.put(CommandType.getType(newInstruction), newInstruction);
            }
        }
        return filteredInstructions;
    }

    private InputInstruction getLastInstructionOfType(InputInstruction instruction) {
        return lastInstructions.getOrDefault(CommandType.getType(instruction), null);
    }
}
