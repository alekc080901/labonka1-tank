package ru.mipt.bit.platformer.game.controls.command_queue;

import org.springframework.stereotype.Component;

@Component
public class CommandReceiver {
    /*
    Класс, ответственный за обработку и делегирование всех приходящих команд на объект сущности.
     */
    private final CommandQueue commandQueue;

    public CommandReceiver(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }

    public void processAll() {
        while (!commandQueue.isEmpty()) {
            QueueElement queueElement = commandQueue.pollCommand();
            queueElement.getCommand().execute();
        }
    }

    public static CommandReceiver initCommandReceiver(CommandQueue queue) {
        return new CommandReceiver(queue);
    }
}
