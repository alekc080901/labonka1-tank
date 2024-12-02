package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.data.GameSettings;

public class ToggleHealthCommand implements Command {

    private final GameSettings settings;

    public ToggleHealthCommand(GameSettings settings) {
        this.settings = settings;
    }

    @Override
    public void execute() {
        settings.toggleHealthBar();
    }
}
