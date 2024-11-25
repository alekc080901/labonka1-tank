package ru.mipt.bit.platformer.game.controls.command_queue;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

@Component
public class CommandQueue {

    private final Queue<QueueElement> commands = new ArrayDeque<>();

    public void addCommands(Collection<QueueElement> elements) {
        commands.addAll(elements);
    }

    public QueueElement pollCommand() {
        return commands.poll();
    }

    public boolean isEmpty() {
        return commands.isEmpty();
    }

    @Override
    public String toString() {
        return commands.toString();
    }
}
