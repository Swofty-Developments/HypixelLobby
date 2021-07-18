package net.swofty.lobby.listener;

import net.swofty.lobby.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProcess implements Listener {
    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {

        if (((event.getMessage().contains("/l") || event.getMessage().contains("/p")) && (event.getMessage().contains("lp") || event.getMessage().contains("luckperms") || event.getMessage().contains("perm"))) || (event.getMessage().contains(":") && !event.getMessage().contains("::"))) {
            if (!new PlayerManager(event.getPlayer()).getRank().equals("admin")) {
                event.getPlayer().sendMessage("Unknown command. Type \"help\" for help.");
                event.setCancelled(true);
                return;
            }
        }


        switch (event.getMessage().toLowerCase()) {

            case "/help":
            case "/?":
                event.getPlayer().sendMessage(" ");
                event.getPlayer().sendMessage("§e§lHYPIXEL");
                event.getPlayer().sendMessage("§eClick to select a help option...");
                event.getPlayer().sendMessage(" ");
                event.getPlayer().sendMessage(" §c* §bHypixel Minigames");
                event.getPlayer().sendMessage(" §c* §bFound a Server Bug/Issue");
                event.getPlayer().sendMessage(" §c* §bReport a Rule Breaker");
                event.getPlayer().sendMessage(" §c* §bStore");
                event.getPlayer().sendMessage(" §c* §bSupport");
                event.getPlayer().sendMessage(" §c* §bAllowed Modifications");
                event.getPlayer().sendMessage(" §c* §bHypixel Rules & Policies");
                event.getPlayer().sendMessage(" §c* §bGeneral Gameplay/Server");
                event.getPlayer().sendMessage(" ");
                event.getPlayer().sendMessage("§eNeed more help? Visit §bour forums§e.");
                event.getPlayer().sendMessage(" ");
                event.setCancelled(true);
                break;

            case "/plugins":
            case "/pl":
                if (!new PlayerManager(event.getPlayer()).getRank().equals("admin")) {
                    event.getPlayer().sendMessage("§cYou're not allowed to do this!");
                    event.setCancelled(true);
                } else {
                    event.getPlayer().sendMessage("§eHypixelLobby core is §afunctional§e. Use §a/test §efor more info.");
                    event.setCancelled(true);
                }
                break;

            case "/ver":
            case "/version":
            case "/about":
                event.getPlayer().sendMessage("§eThis server is running §aCraftBukkit version git-Swofty-hypixellobbyserverjar (MC: 1.8.8) (Implementing API version 1.8.8-R0.1-SNAPSHOT");
                event.setCancelled(true);
                break;

            case "/luckperms":
            case "/lp":
            case "/tps":
                if (!new PlayerManager(event.getPlayer()).getRank().equals("admin")) {
                    event.getPlayer().sendMessage("Unknown command. Type \"help\" for help.");
                    event.setCancelled(true);
                }
                break;
        }
    }
}
