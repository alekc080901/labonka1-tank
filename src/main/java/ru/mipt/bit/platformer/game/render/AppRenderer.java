package ru.mipt.bit.platformer.game.render;

public class AppRenderer implements Renderer {
    private final GameRenderer gameRenderer;
    private final GraphicsRenderer graphicsRenderer;

    public AppRenderer(GameRenderer gameRenderer, GraphicsRenderer graphicsRenderer) {
        this.gameRenderer = gameRenderer;
        this.graphicsRenderer = graphicsRenderer;
    }

    @Override
    public void render(float deltaTime) {
        graphicsRenderer.clear();
        gameRenderer.render(deltaTime);
        graphicsRenderer.render(deltaTime);
    }

    public void stop() {
        graphicsRenderer.dispose();
    }
}
