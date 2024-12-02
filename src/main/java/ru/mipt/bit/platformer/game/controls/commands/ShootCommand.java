package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.core.entity.ShootableEntity;

public class ShootCommand implements Command {
    /*
    Enum с командой перемещения от пользователя и направлением перемещения.
     */

    private final ShootableEntity entity;
    private final BaseLevel level;


    public ShootCommand(BaseLevel level, ShootableEntity entity) {
        this.entity = entity;
        this.level = level;
    }
    @Override
    public void execute() {
        entity.shoot(level);
    }
}
