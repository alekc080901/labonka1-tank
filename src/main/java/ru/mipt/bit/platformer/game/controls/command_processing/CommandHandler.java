package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.TankMoveLogic;
import ru.mipt.bit.platformer.game.core.Tank;
import ru.mipt.bit.platformer.game.graphics.contracts.MoveRenderer;
import ru.mipt.bit.platformer.game.graphics.contracts.Renderers;
import ru.mipt.bit.platformer.game.graphics.gdx.GdxLevelRenderer;

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
            playerCommands.add(commandGenerator.getCurrentCommand());
        }

        // Затем их обрабатываем
        for (CommandReceiver renderer : playerCommandHandler.keySet()) {
            renderer.handleCommand(playerCommands.poll(), deltaTime);
        }
    }

    public static CommandHandler getCommandHandler(Renderers renderers) {
        BaseLevel level = ((GdxLevelRenderer) renderers.levelRenderer()).getLevel();
        Set<Tank> tanks = level.getTanks();

        CommandHandler commandHandler = new CommandHandler();
        for (Tank tank : tanks) {
            CommandReceiver commandReceiver = new CommandReceiver(tank, renderers);
            commandHandler.register(commandReceiver, new PlayerCommandGenerator());
        }
        return commandHandler;
    }
}
