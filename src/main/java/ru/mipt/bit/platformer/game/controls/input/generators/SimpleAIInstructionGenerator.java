package ru.mipt.bit.platformer.game.controls.input.generators;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SimpleAIInstructionGenerator implements InstructionGenerator {
    /*
    Реализация контракта генерации команд, производимых с подключенных устройств пользователем.
     */

    private static final double DEFAULT_SHOOT_INTERVAL = 3000;
    private final GameEntity gameEntity;
    private long timeUntilNextShot = System.currentTimeMillis();
    private double shotRandomTimeModifier = calculateShotTimeModifier();

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

        addShotIfNecessary(inputtedCommands);
        return inputtedCommands;
    }

    private void addShotIfNecessary(Set<InputInstruction> inputtedCommands) {
        if (System.currentTimeMillis() - timeUntilNextShot > shotRandomTimeModifier * DEFAULT_SHOOT_INTERVAL) {
            timeUntilNextShot = System.currentTimeMillis();
            shotRandomTimeModifier = calculateShotTimeModifier();
            inputtedCommands.add(InputInstruction.SHOOT);
        }
    }

    private static <T> T randomFromCollection(Collection<T> coll) {
        int num = (int) (Math.random() * coll.size());
        for(T t: coll) if (--num < 0) return t;
        return null;
    }

    private static double calculateShotTimeModifier() {
        return (Math.random() * 1.5) + 0.5;
    }
}
