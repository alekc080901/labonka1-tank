package ru.mipt.bit.platformer.game.gdx.graphics;

import ru.mipt.bit.platformer.data.MapFileLoader;
import ru.mipt.bit.platformer.data.MapLoader;
import ru.mipt.bit.platformer.data.MapRandomLoader;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.graphic_contracts.LevelLoader;
import ru.mipt.bit.platformer.game.graphic_contracts.LevelRenderer;
import ru.mipt.bit.platformer.game.graphic_contracts.MoveRenderer;
import ru.mipt.bit.platformer.game.graphic_contracts.Renderers;

public class GdxLevelLoader implements LevelLoader {

    private final String gdxMapPath;

    public GdxLevelLoader(String gdxMapPath) {
        this.gdxMapPath = gdxMapPath;
    }

    @Override
    public Renderers loadFromFile(String filePath) {
        MapLoader loader = new MapFileLoader(filePath);
        GdxLevel level = loadLevel(loader);
        return getRenderers(level);
    }

    @Override
    public Renderers loadByRandom() {
        Coordinates upperRightLimit = GdxLevel.getLevelSizeFromFile(gdxMapPath);
        MapLoader loader = new MapRandomLoader(upperRightLimit);
        GdxLevel level = loadLevel(loader);
        return getRenderers(level);
    }

    private GdxLevel loadLevel(MapLoader mapLoader) {
        BaseLevel level = mapLoader.load();
        GdxLevel gdxLevel = new GdxLevel(gdxMapPath, level);
        level.setUpperRightSize(gdxLevel.getLevelSize());
        return gdxLevel;
    }

    private Renderers getRenderers(GdxLevel level) {
        LevelRenderer levelRenderer = new GdxLevelRenderer(level);
        MoveRenderer moveRenderer = new GdxMoveRenderer(level);
        return new GdxRenderers(levelRenderer, moveRenderer);
    }

}
