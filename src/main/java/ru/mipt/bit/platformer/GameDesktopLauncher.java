package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandPublisher;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandQueue;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandReceiver;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;
import ru.mipt.bit.platformer.game.core.Level;
import ru.mipt.bit.platformer.game.gdx.LevelLoader;
import ru.mipt.bit.platformer.game.core.TimeCounter;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevelLoader;
import ru.mipt.bit.platformer.game.gdx.utils.GdxTimeCounter;
import ru.mipt.bit.platformer.game.render.AppRenderer;

public class GameDesktopLauncher implements ApplicationListener {
    /*
    Класс, ответственный за инициализацию объектов
     */

    private AppRenderer appRenderer;
    private TimeCounter timeCounter;
    private CommandPublisher commandPublisher;
    private CommandReceiver commandReceiver;

    @Override
    public void create() {
        LevelLoader levelLoader = new GdxLevelLoader("level.tmx");
        Level level = levelLoader.loadByRandom();
        appRenderer = GdxLevelLoader.fromLevel((GdxLevel) level);

        CommandQueue commandQueue = new CommandQueue();
        commandPublisher = CommandPublisher.initCommandPublisher(commandQueue, level.getSourceLevel());
        commandReceiver = CommandReceiver.initCommandReceiver(commandQueue);

        timeCounter = new GdxTimeCounter();
    }

    @Override
    public void render() {
        float deltaTime = timeCounter.getDelta();
        commandPublisher.publishAll();
        commandReceiver.processAll();
        appRenderer.render(deltaTime);
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
        appRenderer.stop();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
