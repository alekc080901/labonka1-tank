package ru.mipt.bit.platformer.game.renderers;

import org.springframework.stereotype.Component;

@Component
public class GameRenderer implements Renderer {
    private final LogicRenderer logicRenderer;
    private final GraphicsRenderer graphicsRenderer;

    public GameRenderer(LogicRenderer logicRenderer, GraphicsRenderer graphicsRenderer) {
        this.logicRenderer = logicRenderer;
        this.graphicsRenderer = graphicsRenderer;
    }

    @Override
    public void render(float deltaTime) {
        graphicsRenderer.clear();
        logicRenderer.render(deltaTime);
        graphicsRenderer.render(deltaTime);
    }

    public void stop() {
        graphicsRenderer.dispose();
    }
}
