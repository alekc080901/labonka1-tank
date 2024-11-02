package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.PlayerTypes;
import ru.mipt.bit.platformer.game.core.Tank;
import ru.mipt.bit.platformer.game.graphics.contracts.Renderers;

import java.util.*;

public class CommandHandler {
    /*
    Класс, принимающий команды от игрока (кем бы он ни был, не обязательно человека) и передающий их в игру.
     */
    private final Map<CommandReceiver, CommandGenerator> playerCommandHandler = new LinkedHashMap<>();

    public void register(CommandReceiver renderer, CommandGenerator commandGenerator) {
        playerCommandHandler.put(renderer, commandGenerator);
    }

    public void handleAllPlayers(float deltaTime) {
        // Сначала считываем все команды
        ArrayDeque<Command> playerCommands = new ArrayDeque<>();
        for (CommandGenerator commandGenerator : playerCommandHandler.values()) {
            playerCommands.addAll(commandGenerator.getCommands());
        }

        // Затем их обрабатываем
        for (CommandReceiver renderer : playerCommandHandler.keySet()) {
            renderer.handleCommand(Objects.requireNonNull(playerCommands.poll()), deltaTime);
        }
    }

    public static CommandHandler getCommandHandler(Renderers renderers) {
        BaseLevel level = renderers.levelRenderer().getSource();
        Set<Tank> tanks = level.getTanks();

        CommandHandler commandHandler = new CommandHandler();
        for (Tank tank : tanks) {
            CommandReceiver commandReceiver = new CommandReceiver(tank, renderers);
            commandHandler.register(
                    commandReceiver,
                    tank.whoDrives() == PlayerTypes.PLAYER ? new PlayerCommandGenerator() :
                            new SimpleAICommandGenerator()
            );
        }
        return commandHandler;
    }
}
