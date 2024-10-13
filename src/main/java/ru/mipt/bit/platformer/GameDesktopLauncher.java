package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.game.controls.command_processing.PlayerCommandHandler;
import ru.mipt.bit.platformer.game.graphics.LevelLoader;
import ru.mipt.bit.platformer.game.graphics.Renderer;
import ru.mipt.bit.platformer.game.graphics.TimeCounter;
import ru.mipt.bit.platformer.game.graphics.gdx.GdxLevelLoader;
import ru.mipt.bit.platformer.game.graphics.gdx.GdxTimeCounter;

import java.util.List;

public class GameDesktopLauncher implements ApplicationListener {
    /*
    Класс, ответственный за инициализацию объектов
     */

    private Renderer renderer;
    private TimeCounter timeCounter;
    private List<PlayerCommandHandler> commandHandlers;

    @Override
    public void create() {
        LevelLoader levelLoader = new GdxLevelLoader("level.tmx");
//        renderer = loader.generateRendererFromFile("level1.level");  // TODO: Реализовать выбор метода генерации через CLI
        renderer = levelLoader.generateRandomRenderer();
        commandHandlers = levelLoader.getCommandHandlersFromLevelRenderer(renderer);
        timeCounter = new GdxTimeCounter();
    }

    @Override
    public void render() {
        renderer.clear();

        float deltaTime = timeCounter.getDelta();
        for (PlayerCommandHandler playerHandler : commandHandlers) {
            playerHandler.handleSinglePlayer(deltaTime);
        }

        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        renderer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
