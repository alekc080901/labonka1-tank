package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.GameEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SimpleAIInstructionGenerator implements InstructionGenerator {
    /*
    Реализация контракта генерации команд, производимых с подключенных устройств пользователем.
     */

    private final GameEntity gameEntity;

    public SimpleAIInstructionGenerator(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }

    @Override
    public GameEntity getInstructionSource() {
        return gameEntity;
    }

    @Override
    public Collection<InputInstruction> getInstructions() {
        Set<InputInstruction> moveCommands = new HashSet<>(
                Set.of(InputInstruction.UP, InputInstruction.DOWN, InputInstruction.LEFT, InputInstruction.RIGHT)
        );
        Set<InputInstruction> inputtedCommands = new HashSet<>();
        inputtedCommands.add(randomFromCollection(moveCommands));
        return inputtedCommands;

    }

    private static <T> T randomFromCollection(Collection<T> coll) {
        int num = (int) (Math.random() * coll.size());
        for(T t: coll) if (--num < 0) return t;
        return null;
    }
}
