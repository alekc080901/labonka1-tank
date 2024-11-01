package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.game.controls.command_processing.CommandHandler;
import ru.mipt.bit.platformer.game.graphics.contracts.LevelLoader;
import ru.mipt.bit.platformer.game.graphics.contracts.Renderers;
import ru.mipt.bit.platformer.game.graphics.contracts.TimeCounter;
import ru.mipt.bit.platformer.game.graphics.gdx.GdxLevelLoader;
import ru.mipt.bit.platformer.game.graphics.gdx.GdxTimeCounter;

public class GameDesktopLauncher implements ApplicationListener {
    /*
    Класс, ответственный за инициализацию объектов
     */

    private Renderers renderers;
    private TimeCounter timeCounter;
    private CommandHandler commandHandler;

    @Override
    public void create() {
        LevelLoader levelLoader = new GdxLevelLoader("level.tmx");
//        renderer = loader.generateRendererFromFile("level1.level");  // TODO: Реализовать выбор метода генерации через CLI
        renderers = levelLoader.loadByRandom();
        commandHandler = CommandHandler.getCommandHandler(renderers);
        timeCounter = new GdxTimeCounter();
    }

    @Override
    public void render() {
        renderers.levelRenderer().clear();
        float deltaTime = timeCounter.getDelta();
        commandHandler.handleAllPlayers(deltaTime);
        renderers.levelRenderer().render();
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
        renderers.levelRenderer().dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
