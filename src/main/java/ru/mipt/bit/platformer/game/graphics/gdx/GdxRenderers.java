package ru.mipt.bit.platformer.game.graphics.gdx;

import ru.mipt.bit.platformer.game.graphics.contracts.LevelRenderer;
import ru.mipt.bit.platformer.game.graphics.contracts.MoveRenderer;
import ru.mipt.bit.platformer.game.graphics.contracts.Renderers;

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
