package net.maploop.dnum.command.commands;

import net.maploop.dnum.command.AbstractCommand;
import net.maploop.dnum.command.CommandParameters;
import net.maploop.dnum.command.CommandSource;
import net.maploop.dnum.gui.PlayerMenuUtility;
import net.maploop.dnum.gui.guis.ExampleGUI;
import net.maploop.dnum.util.Util;
import net.maploop.dnum.util.hologram.Hologram;
import net.maploop.dnum.util.signgui.SignGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

@CommandParameters(
        usage = "/<command> <args>",
        permission = "dnum.example",
        description = "Example command that opens the ExampleGUI!",
        inGameOnly = true,
        aliases = {"e", "ex"}
)
// Commands are automatically registered in the CommadMap. You don't need to put the commands in your plugin.yml.
// You need to name your classes like "Command_<name>" unless it won't work. To change this, go to the AbstractCommand class
// And set the COMMAND_PREFIX to whatever you want. Or make it a suffix!

public class Command_example extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        send("§aHello! This is an §eexample§a.");

        // This is how you open the SignGUI.
        if(true) {
            SignGUI.openSignEditor(sender.getPlayer(), new String[] {"", "^^^^^", "Type text", "in here!"}, event -> {
                sender.getPlayer().sendMessage(event.getSignText()[0]);
                // new Hologram("first", event.getSignText()[0].split(","), sender.getPlayer().getLocation(), 0.5, true);
            });
            return;
        }

        // This is how you open menus to a player:
        new ExampleGUI(new PlayerMenuUtility(sender.getPlayer())).open();

        /**
         * This line is for testing purposes.
         */
    }
}
