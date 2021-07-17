package net.maploop.dnum.command;

import java.util.ArrayList;
import java.util.List;

public class CommandLoader
{
    private final List<AbstractCommand> commands;

    public CommandLoader() {
        this.commands = new ArrayList<>();
    }

    public void register(AbstractCommand command) {
        commands.add(command);
        command.register();
    }

    public int getCommandAmount() {
        return commands.size();
    }
}
