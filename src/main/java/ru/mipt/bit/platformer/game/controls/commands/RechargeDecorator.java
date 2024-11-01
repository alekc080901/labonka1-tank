package ru.mipt.bit.platformer.game.controls.commands;

public class RechargeDecorator implements Command {
    // TODO: Не OCP, разные сущности имеют общий таймер команд, исправится скоро
    private static long lastTimeExecuted = System.currentTimeMillis();
    private final Command wrappedCommand;
    private final long delayTimeMillis;

    public RechargeDecorator(Command command, int delayTime) {
        this.wrappedCommand = command;
        this.delayTimeMillis = delayTime;
    }

    @Override
    public void execute() {
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime - lastTimeExecuted);
        if (currentTime - lastTimeExecuted > delayTimeMillis) {
            wrappedCommand.execute();
            lastTimeExecuted = currentTime;
        }
    }
}
