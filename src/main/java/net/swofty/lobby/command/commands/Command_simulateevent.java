package net.swofty.lobby.command.commands;

import net.swofty.lobby.command.AbstractCommand;
import net.swofty.lobby.command.CommandParameters;
import net.swofty.lobby.command.CommandSource;
import net.swofty.lobby.gui.PlayerMenuUtility;
import net.swofty.lobby.gui.guis.ExampleGUI;
import net.swofty.lobby.util.BookGUI;
import net.swofty.lobby.util.signgui.SignGUI;

@CommandParameters(usage = "/<command> <args>", permission = "rank.default", description = "Simulate a given event", inGameOnly = true)
public class Command_simulateevent extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        if (args.length == 0) return;

        switch (args[0]) {
            case "rank::book_confirm":
                sender.getPlayer().kickPlayer("§cRejoin for your rank to finish processing");
                break;
        }
    }
}
