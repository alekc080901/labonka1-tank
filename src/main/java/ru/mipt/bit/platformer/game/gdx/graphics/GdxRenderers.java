package ru.mipt.bit.platformer.game.gdx.graphics;

import ru.mipt.bit.platformer.game.graphic_contracts.LevelRenderer;
import ru.mipt.bit.platformer.game.graphic_contracts.MoveRenderer;
import ru.mipt.bit.platformer.game.graphic_contracts.Renderers;

public class GdxRenderers implements Renderers {
    private final LevelRenderer levelRenderer;
    private final MoveRenderer moveRenderer;

    public GdxRenderers(LevelRenderer levelRenderer, MoveRenderer moveRenderer) {
        this.levelRenderer = levelRenderer;
        this.moveRenderer = moveRenderer;
    }

    @Override
    public LevelRenderer levelRenderer() {
        return levelRenderer;
    }

    @Override
    public MoveRenderer moveRenderer() {
        return moveRenderer;
    }
}
