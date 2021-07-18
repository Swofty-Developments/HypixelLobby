package net.swofty.lobby.command.commands;

import net.swofty.lobby.command.AbstractCommand;
import net.swofty.lobby.command.CommandParameters;
import net.swofty.lobby.command.CommandSource;

@CommandParameters(usage = "/<command> <args>", permission = "rank.admin", description = "Sets your food and health to max", inGameOnly = true)
public class Command_heal extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        sender.getPlayer().setFoodLevel(20);
        sender.getPlayer().setHealth(20);
        send("&aYou have been HEALED");
    }
}
