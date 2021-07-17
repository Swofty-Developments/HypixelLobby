package net.maploop.dnum.gui;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {
    private Player owner;
    //store the player that will be killed so we can access him in the next menu
    private Player player;

    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }

    public Player getViewingPlayer() {
        return player;
    }

    public void setViewingPlayer(Player playerToKill) {
        this.player = playerToKill;
    }
}
