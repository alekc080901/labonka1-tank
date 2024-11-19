package ru.mipt.bit.platformer.game.gdx.graphics.level;

import ru.mipt.bit.platformer.data.MapFileLoader;
import ru.mipt.bit.platformer.data.MapLoader;
import ru.mipt.bit.platformer.data.MapRandomLoader;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.gdx.graphics.renderer.GdxGraphicsRenderer;
import ru.mipt.bit.platformer.game.gdx.graphics.renderer.GdxMoveRenderer;
import ru.mipt.bit.platformer.game.core.Level;
import ru.mipt.bit.platformer.game.render.AppRenderer;
import ru.mipt.bit.platformer.game.gdx.LevelLoader;
import ru.mipt.bit.platformer.game.render.GraphicsRenderer;
import ru.mipt.bit.platformer.game.render.MoveRenderer;
import ru.mipt.bit.platformer.game.render.GameRenderer;

public class GdxLevelLoader implements LevelLoader {

    private final String gdxMapPath;

    public GdxLevelLoader(String gdxMapPath) {
        this.gdxMapPath = gdxMapPath;
    }

    @Override
    public Level loadFromFile(String filePath) {
        MapLoader loader = new MapFileLoader(filePath);
        return loadLevel(loader);
    }

    @Override
    public Level loadByRandom() {
        Coordinates upperRightLimit = GdxLevel.getLevelSizeFromFile(gdxMapPath);
        MapLoader loader = new MapRandomLoader(upperRightLimit);
        return loadLevel(loader);
    }

    private GdxLevel loadLevel(MapLoader mapLoader) {
        BaseLevel level = mapLoader.load();
        GdxLevel gdxLevel = new GdxLevel(gdxMapPath, level);
        level.setUpperRightSize(gdxLevel.getLevelSize());
        return gdxLevel;
    }

    public static AppRenderer fromLevel(GdxLevel level) {
        GraphicsRenderer graphicsRenderer = new GdxGraphicsRenderer(level);
        MoveRenderer moveRenderer = new GdxMoveRenderer(level);
        GameRenderer gameRenderer = new GameRenderer(level.getSourceLevel(), moveRenderer);
        return new AppRenderer(gameRenderer, graphicsRenderer);
    }
}
