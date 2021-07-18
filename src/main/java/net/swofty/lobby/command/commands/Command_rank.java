package net.swofty.lobby.command.commands;

import net.swofty.lobby.command.AbstractCommand;
import net.swofty.lobby.command.CommandParameters;
import net.swofty.lobby.command.CommandSource;
import net.swofty.lobby.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandParameters(usage = "/<command> <args>", permission = "rank.admin", description = "Set a players rank")
public class Command_rank extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        if (args.length == 0 || args.length == 1) {
            send("&cUsage: /rank <player> <rank>");
            return;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            send("&cCould not find player '" + args[0] + "'");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        switch (args[1]) {
            case "default":
                new PlayerManager(target).setRank("default");
                send("&aSuccessfully set " + target.getName() + "'s rank to " + args[1].toUpperCase());
                return;

            case "vip":
                new PlayerManager(target).setRank("vip");
                send("&aSuccessfully set " + target.getName() + "'s rank to " + args[1].toUpperCase());
                return;

            case "vip+":
                new PlayerManager(target).setRank("vip+");
                send("&aSuccessfully set " + target.getName() + "'s rank to " + args[1].toUpperCase());
                return;

            case "mvp+":
                new PlayerManager(target).setRank("mvp+");
                send("&aSuccessfully set " + target.getName() + "'s rank to " + args[1].toUpperCase());
                return;

            case "mvp++":
                new PlayerManager(target).setRank("mvp++");
                send("&aSuccessfully set " + target.getName() + "'s rank to " + args[1].toUpperCase());
                return;

            case "helper":
                new PlayerManager(target).setRank("helper");
                send("&aSuccessfully set " + target.getName() + "'s rank to " + args[1].toUpperCase());
                return;

            case "mod":
                new PlayerManager(target).setRank("mod");
                send("&aSuccessfully set " + target.getName() + "'s rank to " + args[1].toUpperCase());
                return;

            case "admin":
                send("&aSuccessfully set " + target.getName() + "'s rank to " + args[1].toUpperCase());
                new PlayerManager(target).setRank("admin");
                return;
        }

        send("&c'" + args[1] + "' is not a valid rank");
    }
}
