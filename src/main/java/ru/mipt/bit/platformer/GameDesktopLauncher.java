package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.data.LevelLoader;
import ru.mipt.bit.platformer.data.LevelRandomLoader;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandPublisher;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandQueue;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandReceiver;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GdxEntitySubscriber;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;
import ru.mipt.bit.platformer.game.core.TimeCounter;
import ru.mipt.bit.platformer.game.gdx.graphics.renderer.GdxGraphicsRenderer;
import ru.mipt.bit.platformer.game.gdx.graphics.renderer.GdxMoveRenderer;
import ru.mipt.bit.platformer.game.gdx.sound.BGMPlayer;
import ru.mipt.bit.platformer.game.gdx.sound.SFXPlayer;
import ru.mipt.bit.platformer.game.gdx.utils.GdxTimeCounter;
import ru.mipt.bit.platformer.game.render.AppRenderer;
import ru.mipt.bit.platformer.game.render.GameRenderer;
import ru.mipt.bit.platformer.game.render.GraphicsRenderer;
import ru.mipt.bit.platformer.game.render.MoveRenderer;

import java.util.Set;

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
        BGMPlayer bgmPlayer = new BGMPlayer();
        SFXPlayer sfxPlayer = new SFXPlayer();

        GdxLevel gdxLevel = new GdxLevel("level.tmx");
        LevelLoader levelLoader = new LevelRandomLoader(Set.of(new GdxEntitySubscriber(gdxLevel, sfxPlayer)),
                GdxLevel.getLevelSizeFromFile("level.tmx"));
        BaseLevel level = levelLoader.load();
        appRenderer = fromLevel(level, gdxLevel);

        CommandQueue commandQueue = new CommandQueue();
        commandPublisher = CommandPublisher.initCommandPublisher(commandQueue, level);
        commandReceiver = CommandReceiver.initCommandReceiver(commandQueue);

        timeCounter = new GdxTimeCounter();

        bgmPlayer.play();
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

    private static AppRenderer fromLevel(BaseLevel baseLevel, GdxLevel gdxLevel) {
        GraphicsRenderer graphicsRenderer = new GdxGraphicsRenderer(gdxLevel);
        MoveRenderer moveRenderer = new GdxMoveRenderer(gdxLevel);
        GameRenderer gameRenderer = new GameRenderer(baseLevel, moveRenderer);
        return new AppRenderer(gameRenderer, graphicsRenderer);
    }
}
