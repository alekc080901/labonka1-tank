package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.data.GameSettings;

public class ToggleHealthCommand implements Command {

    @Override
    public void execute() {
        GameSettings.toggleHealthBar();
    }
}
