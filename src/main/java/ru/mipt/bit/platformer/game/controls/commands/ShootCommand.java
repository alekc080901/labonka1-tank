package ru.mipt.bit.platformer.game.controls.commands;

import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;
import ru.mipt.bit.platformer.game.core.entity.ShootableEntity;

import java.util.HashMap;

public class ShootCommand implements Command {
    /*
    Enum с командой перемещения от пользователя и направлением перемещения.
     */

    private final ShootableEntity entity;
//    private final BaseLevel level;


    public ShootCommand(BaseLevel level, ShootableEntity entity) {
        this.entity = entity;
//        this.level = level;
    }
    @Override
    public void execute() {
        entity.shoot();
    }
}
