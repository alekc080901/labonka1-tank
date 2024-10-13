package ru.mipt.bit.platformer.game.graphics.gdx;

import ru.mipt.bit.platformer.data.LevelFileLoader;
import ru.mipt.bit.platformer.data.RandomGeneratorLoader;
import ru.mipt.bit.platformer.game.PlayerRenderer;
import ru.mipt.bit.platformer.game.controls.command_processing.PlayerCommandDistributor;
import ru.mipt.bit.platformer.game.controls.command_processing.PlayerCommandHandler;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.PlayerMoveLogic;
import ru.mipt.bit.platformer.game.core.Tank;
import ru.mipt.bit.platformer.game.graphics.LevelLoader;
import ru.mipt.bit.platformer.game.graphics.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GdxLevelLoader implements LevelLoader {

    private final String gdxMapPath;

    public GdxLevelLoader(String gdxMapPath) {
        this.gdxMapPath = gdxMapPath;
    }

    @Override
    public Renderer generateRendererFromFile(String filePath) {
        BaseLevel level = new LevelFileLoader().load(filePath);
        GdxLevel gdxLevel = new GdxLevel(gdxMapPath, level);
        level.setUpperRightSize(gdxLevel.getLevelSize());
        return new GdxRenderer(gdxLevel);
    }

    @Override
    public Renderer generateRandomRenderer() {
        Coordinates upperRightLimit = GdxLevel.getLevelSizeFromFile(gdxMapPath);
        BaseLevel level = new RandomGeneratorLoader(upperRightLimit).load();
        GdxLevel gdxLevel = new GdxLevel(gdxMapPath, level);
        level.setUpperRightSize(gdxLevel.getLevelSize());
        return new GdxRenderer(gdxLevel);
    }

    @Override
    public List<PlayerCommandHandler> getCommandHandlersFromLevelRenderer(Renderer renderer) {
        BaseLevel level = renderer.getLevel();
        Set<Tank> tanks = level.getTanks();

        List<PlayerCommandHandler> handlers = new ArrayList<>();
        for (Tank tank : tanks) {
            handlers.add(getCommandHandlerFromTank(renderer, tank, level));
        }
        return handlers;
    }

    private static PlayerCommandHandler getCommandHandlerFromTank(Renderer renderer, Tank tank, BaseLevel level) {
        PlayerMoveLogic playerMoveLogic = new PlayerMoveLogic(tank, level);
        PlayerRenderer playerRenderer = new PlayerRenderer(playerMoveLogic, renderer);

        return new PlayerCommandHandler(playerRenderer, new PlayerCommandDistributor());
    }

}
