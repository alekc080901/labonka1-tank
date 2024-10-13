package ru.mipt.bit.platformer.game.graphics;

import ru.mipt.bit.platformer.game.PlayerRenderer;
import ru.mipt.bit.platformer.game.controls.command_processing.PlayerCommandDistributor;
import ru.mipt.bit.platformer.game.controls.command_processing.PlayerCommandHandler;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.graphics.gdx.GdxLevel;
import ru.mipt.bit.platformer.game.graphics.gdx.GdxRenderer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelLoader {

    public static GdxRenderer generateWithDefaultLayout() {
        Tank playerTank = new Tank(new Coordinates(1, 1));
        Obstacle obstacle = new Obstacle(new Coordinates(1, 3));

        Set<Tank> tanks = new HashSet<>();
        tanks.add(playerTank);
        Set<Obstacle> obstacles = new HashSet<>();
        obstacles.add(obstacle);

        BaseLevel level = new BaseLevel(tanks, obstacles);
        GdxLevel gdxLevel = new GdxLevel("level.tmx", level);
        level.setUpperRightSize(gdxLevel.getLevelSize());

        return new GdxRenderer(gdxLevel);
    }

    public static List<PlayerCommandHandler> getCommandHandlersFromLevelRenderer(Renderer<Entity> renderer) {
        BaseLevel level = renderer.getLevel();
        Set<Tank> tanks = level.getTanks();

        List<PlayerCommandHandler> handlers = new ArrayList<>();
        for (Tank tank : tanks) {
            handlers.add(getCommandHandlerFromTank(renderer, tank, level));
        }
        return handlers;
    }

    private static PlayerCommandHandler getCommandHandlerFromTank(Renderer<Entity> renderer, Tank tank, BaseLevel level) {
        PlayerMoveLogic playerMoveLogic = new PlayerMoveLogic(tank, level);
        PlayerRenderer playerRenderer = new PlayerRenderer(playerMoveLogic, renderer);

        return new PlayerCommandHandler(playerRenderer, new PlayerCommandDistributor());
    }

}
