package net.swofty.lobby.command.commands;

import net.swofty.lobby.Data;
import net.swofty.lobby.command.AbstractCommand;
import net.swofty.lobby.command.CommandParameters;
import net.swofty.lobby.command.CommandSource;

@CommandParameters(usage = "/<command> <args>", permission = "rank.admin", description = "Toggles the ability to build in lobbies", inGameOnly = true)
public class Command_build extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        if (Data.getData(sender.getPlayer(), "build").equals("true")) {
            Data.editData(sender.getPlayer(), "build", "false");
            send("&aBuild mode is now DISABLED");
        } else {
            Data.editData(sender.getPlayer(), "build", "true");
            send("&aBuild mode is now ENABLED");
        }
    }
}
