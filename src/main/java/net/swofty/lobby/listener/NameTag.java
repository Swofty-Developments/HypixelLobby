package net.swofty.lobby.listener;

import net.swofty.lobby.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class NameTag implements Listener {

    @EventHandler
    public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {

        event.setTag(new PlayerManager(event.getNamedPlayer()).getRankPrefix() + event.getNamedPlayer().getName());

    }
}
