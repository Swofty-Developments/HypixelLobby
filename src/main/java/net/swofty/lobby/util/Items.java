package net.swofty.lobby.util;

import net.swofty.lobby.gui.GUI;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import java.awt.*;

public class Items {

    public static void giveSpawnItems(Player player, Boolean hidden) {

        player.getInventory().clear();

        player.getInventory().setItem(0, GUI.makeItemOther(Material.COMPASS, "§aGame Menu §7(Right Click)", 1, 0, "§7Right Click to bring up the Game Menu!"));
        player.getInventory().setItem(1, GUI.makeSkullItemOther("§aMy Profile §7(Right Click)", player.getName(), 1, "§7Right-click to browse quests, view achievements,\n§7active Network Boosters and more!"));
        player.getInventory().setItem(4, GUI.makeItemOther(Material.CHEST, "§aCollectibles §7(Right Click)", 1, 1, "§7Mystery Dust: §b0\n\n§7Collect fun cosmetic items! Get new\n§7items by opening §bMystery Boxes §7or\n§7hitting milestone rewards. Some can also\n§7be crafted using §bMystery Dust§7.\n\n§bMystery Dust §7can be earned by\n§7opening §bMystery Boxes.\n\n§7You can support the Hypixel server by\n§7buying §bMystery Boxes §7on our store.\n\n§ehttps://store.hypixel.net"));

        if (hidden) {
            player.getInventory().setItem(7, GUI.makeItemOther(Material.INK_SACK, "§fPlayers: §cHidden §7(Right Click)", 1, 8, "§7Right-click to toggle player visibility!"));
        } else {
            player.getInventory().setItem(7, GUI.makeItemOther(Material.INK_SACK, "§fPlayers: §aVisible §7(Right Click)", 1, 10, "§7Right-click to toggle player visibility!"));
        }

        player.getInventory().setItem(8, GUI.makeItemOther(Material.NETHER_STAR, "§aLobby Selector §7(Right Click)", 1, 0, "§7Right-click to switch between different lobbies!\nUse this to stay with your friends."));
    }

    public static void giveParkourItems(Player player, Boolean hidden) {

        player.getInventory().clear();

        player.getInventory().setItem(0, GUI.makeItemOther(Material.COMPASS, "§aGame Menu §7(Right Click)", 1, 0, "§7Right Click to bring up the Game Menu!"));
        player.getInventory().setItem(1, GUI.makeSkullItemOther("§aMy Profile §7(Right Click)", player.getName(), 1, "§7Right-click to browse quests, view achievements,\n§7active Network Boosters and more!"));
        player.getInventory().setItem(3, GUI.makeItemOtherStack(new ItemStack(148, 1), "§aTeleport to Last Checkpoint", ""));
        player.getInventory().setItem(4, GUI.makeItemOtherStack(new ItemStack(324, 1), "§cReset", ""));
        player.getInventory().setItem(5, GUI.makeItemOtherStack(new ItemStack(355, 1), "§cCancel", ""));

        if (hidden) {
            player.getInventory().setItem(7, GUI.makeItemOther(Material.INK_SACK, "§fPlayers: §cHidden §7(Right Click)", 1, 8, "§7Right-click to toggle player visibility!"));
        } else {
            player.getInventory().setItem(7, GUI.makeItemOther(Material.INK_SACK, "§fPlayers: §aVisible §7(Right Click)", 1, 10, "§7Right-click to toggle player visibility!"));
        }

        player.getInventory().setItem(8, GUI.makeItemOther(Material.NETHER_STAR, "§aLobby Selector §7(Right Click)", 1, 0, "§7Right-click to switch between different lobbies!\nUse this to stay with your friends."));
    }
}
