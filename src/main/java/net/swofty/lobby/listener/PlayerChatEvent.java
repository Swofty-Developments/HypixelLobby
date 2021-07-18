package net.swofty.lobby.listener;

import net.swofty.lobby.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class PlayerChatEvent implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) throws IOException {

        if (new PlayerManager(event.getPlayer()).getRank().equals("default")) {
            event.setFormat(new PlayerManager(event.getPlayer()).getRankPrefix() + event.getPlayer().getName() + "§7: " + event.getMessage());
        } else {
            event.setFormat(new PlayerManager(event.getPlayer()).getRankPrefix() + event.getPlayer().getName() + "§f: " + event.getMessage());
        }
    }
}