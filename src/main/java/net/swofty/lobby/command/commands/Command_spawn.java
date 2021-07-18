package net.swofty.lobby.command.commands;

import net.swofty.lobby.command.AbstractCommand;
import net.swofty.lobby.command.CommandParameters;
import net.swofty.lobby.command.CommandSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@CommandParameters(usage = "/<command> <args>", permission = "rank.default", description = "Teleports you to spawn", inGameOnly = true)
public class Command_spawn extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        sender.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 45.5, 86, 22.5));
    }
}
