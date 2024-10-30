package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SimpleAICommandGenerator implements CommandGenerator {
    /*
    Реализация контракта генерации команд, производимых с подключенных устройств пользователем.
     */

    @Override
    public Command getCurrentCommand() {
        Set<Command> moveCommands = new HashSet<>(
                Set.of(MoveCommand.UP, MoveCommand.DOWN, MoveCommand.LEFT, MoveCommand.RIGHT)
        );
        return randomFromCollection(moveCommands);

    }

    private static <T> T randomFromCollection(Collection<T> coll) {
        int num = (int) (Math.random() * coll.size());
        for(T t: coll) if (--num < 0) return t;
        return null;
    }
}
