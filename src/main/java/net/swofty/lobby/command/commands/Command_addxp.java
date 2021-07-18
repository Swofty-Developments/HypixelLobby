package net.swofty.lobby.command.commands;

import net.swofty.lobby.Data;
import net.swofty.lobby.command.AbstractCommand;
import net.swofty.lobby.command.CommandParameters;
import net.swofty.lobby.command.CommandSource;
import net.swofty.lobby.manager.PlayerManager;
import org.bukkit.Bukkit;

@CommandParameters(usage = "/<command> <args>", permission = "rank.admin", description = "Debug comand for admins", inGameOnly = true)
public class Command_addxp extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        if (args.length == 0) {
            send("&cUsage: /addxp (XP) [player]");
            return;
        }

        if (args.length == 1) {

            try {
                new PlayerManager(sender.getPlayer()).addXP(Integer.parseInt(args[0]));
                send("&aAdded " + args[0] + "xp to your total xp");
            } catch (Exception e) {
                send("&cThere was an error adding " + args[0] + " to your total xp");
            }
        } else {
            try {
                new PlayerManager(Bukkit.getPlayer(args[1])).addXP(Integer.parseInt(args[0]));
                send("&aAdded " + args[0] + "xp to your total xp");
            } catch (Exception e) {
                send("&cThere was an error adding " + args[0] + " to " + args[1] + "'s total xp");
            }
        }
    }
}
