package net.swofty.lobby.command.commands;

import net.swofty.lobby.Data;
import net.swofty.lobby.command.AbstractCommand;
import net.swofty.lobby.command.CommandParameters;
import net.swofty.lobby.command.CommandSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandParameters(usage = "/<command> <args>", permission = "rank.admin", description = "Debug comand for admins", inGameOnly = true)
public class Command_levelinfo extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {

        if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) == null) {
                send("&cCould not find player '" + args[0] + "'");
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);
            send("&e&lINFORMATION ABOUT " + target.getName() + "'s LEVEL;");
            send("Current Level: " + Data.getData(target, "level"));
            send("Current XP: " + Data.getData(target, "xp"));
            send("XP needed for LVL-UP: " + (Integer.parseInt(Data.getData(target, "level")) + 5000 + (Integer.parseInt(Data.getData(target, "level")) * 750)));
            send("&7level + 5000 + (level * 750)");
            return;
        }
        send("&e&lINFORMATION ABOUT YOUR LEVEL;");
        send("Current Level: " + Data.getData(sender.getPlayer(), "level"));
        send("Current XP: " + Data.getData(sender.getPlayer(), "xp"));
        send("XP needed for LVL-UP: " + (Integer.parseInt(Data.getData(sender.getPlayer(), "level")) + 5000 + (Integer.parseInt(Data.getData(sender.getPlayer(), "level")) * 750)));
        send("&7level + 5000 + (level * 750)");
    }
}
