package net.swofty.lobby.command.commands;

import net.swofty.lobby.Data;
import net.swofty.lobby.command.AbstractCommand;
import net.swofty.lobby.command.CommandParameters;
import net.swofty.lobby.command.CommandSource;
import net.swofty.lobby.manager.PlayerManager;
import net.swofty.lobby.util.Items;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandParameters(usage = "/<command> <args>", permission = "rank.default", description = "Manage your parkour experience", inGameOnly = true)
public class Command_parkour extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        if (args.length == 0) {
            send("&c/parkour start");
            send("&c/parkour reset");
            send("&c/parkour checkpoint");
            send("&c/parkour cancel");
            return;
        }

        switch (args[0]) {
            case "cancel":
                send("&cParkour challenge cancelled!");
                Data.editData(sender.getPlayer(), "parkour", "none");
                Items.giveSpawnItems(sender.getPlayer(), Boolean.valueOf(Data.getData(sender.getPlayer(), "hidden-players")));
                Data.editData(sender.getPlayer(),"checkpoint", "1");
                return;

            case "start":
                if (!Data.getData(sender.getPlayer(), "parkour").equals("null")) {
                    sender.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 39, 79, 52));
                } else {
                    send("&cYou are currently in a parkour race. Use /parkour reset");
                }
                return;

            case "reset":
                if (!Data.getData(sender.getPlayer(), "parkour").equals("null")) {
                    send("&a&lTeleported you to the start of the parkour!");
                    sender.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 41, 79, 55));
                } else {
                    sender.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 41, 79, 55));
                }
                return;

            case "checkpoint":
                if (Data.getData(sender.getPlayer(), "parkour") != "null") {

                    String checkpoint = Data.getData(sender.getPlayer(), "checkpoint");

                    switch (checkpoint) {
                        case "1":
                            sender.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 39, 79, 52));
                            break;

                        case "2":
                            sender.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 72, 94, 113));
                            break;

                        case "3":
                            sender.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -4.5, 83, 118.5));
                            break;


                    }
                } else {
                    sender.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 41, 5, 55));
                }
                return;
        }

        send("&c/parkour start");
        send("&c/parkour reset");
        send("&c/parkour checkpoint");
        send("&c/parkour cancel");
    }
}
