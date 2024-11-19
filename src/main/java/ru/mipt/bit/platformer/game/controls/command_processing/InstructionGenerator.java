package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.GameEntity;

import java.util.Collection;

public interface InstructionGenerator {
    /*
    Контракт на генерацию команд (от пользователя или кого-либо еще).
     */
    GameEntity getInstructionSource();
    Collection<InputInstruction> getInstructions();
}
