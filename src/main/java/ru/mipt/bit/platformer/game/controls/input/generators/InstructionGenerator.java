package ru.mipt.bit.platformer.game.controls.input.generators;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

import java.util.Collection;

public interface InstructionGenerator {
    /*
    Контракт на генерацию команд (от пользователя или кого-либо еще).
     */
    GameEntity getInstructionSource();
    Collection<InputInstruction> getInstructions();
}
