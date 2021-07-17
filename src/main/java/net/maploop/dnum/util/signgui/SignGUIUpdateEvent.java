package net.maploop.dnum.util.signgui;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SignGUIUpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String[] signText;
    private Player player;

    public SignGUIUpdateEvent(Player player, String[] signText)
    {
        this.player = player;
        this.signText = signText;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public Player getPlayer()
    {
        return player;
    }

    public String[] getSignText()
    {
        return signText;
    }
}
