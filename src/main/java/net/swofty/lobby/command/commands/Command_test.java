package net.swofty.lobby.command.commands;

import net.swofty.lobby.command.AbstractCommand;
import net.swofty.lobby.command.CommandParameters;
import net.swofty.lobby.command.CommandSource;
import net.swofty.lobby.gui.PlayerMenuUtility;
import net.swofty.lobby.gui.guis.ExampleGUI;
import net.swofty.lobby.util.signgui.SignGUI;

@CommandParameters(usage = "/<command> <args>", permission = "rank.admin", description = "Test", inGameOnly = true)
public class Command_test extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        send("&aTest Successful");
    }
}
