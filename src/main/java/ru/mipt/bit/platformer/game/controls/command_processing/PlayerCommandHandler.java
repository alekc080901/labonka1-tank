package ru.mipt.bit.platformer.game.controls.command_processing;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.PlayerRenderer;

public class PlayerCommandHandler {
    /*
    Класс, принимающий команды от игрока (кем бы он ни был) и передающий их в игру.
     */
    private final PlayerRenderer playerRenderer;
    private final CommandDistributor commandDistributors;

    public PlayerCommandHandler(PlayerRenderer playerRenderer, CommandDistributor commandDistributors) {
        this.playerRenderer = playerRenderer;
        this.commandDistributors = commandDistributors;
    }

    public void handleSinglePlayer(float deltaTime) {
        Command command = commandDistributors.getCurrentCommand();
        playerRenderer.handleCommand(command, deltaTime);
    }
}
