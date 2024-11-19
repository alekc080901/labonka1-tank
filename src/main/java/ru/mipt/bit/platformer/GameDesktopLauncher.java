package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.game.controls.command_processing.CommandPublisher;
import ru.mipt.bit.platformer.game.controls.command_processing.CommandQueue;
import ru.mipt.bit.platformer.game.controls.command_processing.CommandReceiver;
import ru.mipt.bit.platformer.game.graphic_contracts.LevelLoader;
import ru.mipt.bit.platformer.game.graphic_contracts.Renderers;
import ru.mipt.bit.platformer.game.graphic_contracts.TimeCounter;
import ru.mipt.bit.platformer.game.gdx.graphics.GdxLevelLoader;
import ru.mipt.bit.platformer.game.gdx.graphics.GdxTimeCounter;

public class GameDesktopLauncher implements ApplicationListener {
    /*
    Класс, ответственный за инициализацию объектов
     */

    private Renderers renderers;
    private TimeCounter timeCounter;
    private CommandPublisher commandPublisher;
    private CommandReceiver commandReceiver;

    @Override
    public void create() {
        LevelLoader levelLoader = new GdxLevelLoader("level.tmx");
        renderers = levelLoader.loadByRandom();
        CommandQueue commandQueue = new CommandQueue();
        commandPublisher = CommandPublisher.initCommandPublisher(commandQueue, renderers);
        commandReceiver = CommandReceiver.initCommandReceiver(commandQueue, renderers);
        timeCounter = new GdxTimeCounter();
    }

    @Override
    public void render() {
        renderers.levelRenderer().clear();
        float deltaTime = timeCounter.getDelta();
        commandPublisher.publishAll();
        commandReceiver.processAll(deltaTime);
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
