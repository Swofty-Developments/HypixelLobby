package net.swofty.lobby.listener;

import net.swofty.lobby.Data;
import net.swofty.lobby.Loader;
import net.swofty.lobby.gui.guis.MysteryVaultGUI;
import net.swofty.lobby.manager.PlayerManager;
import net.swofty.lobby.npc.NPC;
import net.swofty.lobby.npc.NPCRegistery;
import net.swofty.lobby.npc.PacketReader;
import net.swofty.lobby.util.DLog;
import net.swofty.lobby.util.Items;
import net.swofty.lobby.util.Util;
import net.swofty.lobby.util.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class ItemInteractEvent implements Listener {
    @EventHandler
    public void onEntityInteract(PlayerInteractEvent event) throws IOException {

        Player player = event.getPlayer();
        ItemStack item;

        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }

        try {
            if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {

                new BukkitRunnable() {
                    @Override
                    public void run() {

                        try {
                            player.closeInventory();
                        } catch (Exception e) {

                        }
                        new MysteryVaultGUI().open(player);

                    }
                }.runTaskLater(Loader.getInstance(), 2);
            }
        } catch (Exception e) {

        }

        try {
            item = player.getItemInHand();

            if (item == null || !item.hasItemMeta()) {
                return;
            }
        } catch (Exception e) {
            return;
        }

        switch (item.getType()) {
            case BED:
                player.performCommand("parkour cancel");
                break;

            case IRON_PLATE:
                player.performCommand("parkour checkpoint");
                break;
        }

        if (item.getItemMeta().getDisplayName().contains("Hidden")) {

            if (!Data.getData(player, "parkour").equals("none")) {
                Data.editData(player, "hidden-players", "false");
                Items.giveParkourItems(player, false);
                player.sendMessage("§aPlayer visibility enabled!");
                return;
            }

            Data.editData(player, "hidden-players", "false");
            Items.giveSpawnItems(player, false);
            player.sendMessage("§aPlayer visibility enabled!");

        } else if (item.getItemMeta().getDisplayName().contains("Visible")) {

            if (!Data.getData(player, "parkour").equals("none")) {
                Data.editData(player, "hidden-players", "false");
                Items.giveParkourItems(player, true);
                player.sendMessage("§aPlayer visibility disabled!");
                return;
            }

            Data.editData(player, "hidden-players", "true");
            Items.giveSpawnItems(player, true);
            player.sendMessage("§cPlayer visibility disabled!");

        } else if (item.getItemMeta().getDisplayName().contains("Reset")) {

            player.performCommand("parkour reset");

        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) throws IOException {
        DLog.info(event.getPlayer().getItemInHand().getItemMeta().getDisplayName());

    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) throws IOException {
        DLog.info(event.getPlayer().getItemInHand().getItemMeta().getDisplayName());

    }
}

