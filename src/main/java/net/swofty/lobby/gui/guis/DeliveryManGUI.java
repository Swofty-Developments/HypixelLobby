package net.swofty.lobby.gui.guis;

import net.swofty.lobby.Data;
import net.swofty.lobby.gui.GUI;
import net.swofty.lobby.gui.PlayerMenuUtility;
import net.swofty.lobby.manager.PlayerManager;
import net.swofty.lobby.util.BookGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
public class DeliveryManGUI extends GUI {

    @Override
    public String getTitle() {
        return "The Delivery Man";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void onClick(InventoryClickEvent event) {

        if (event.getCurrentItem() == null) { return; }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Daily Reward")) {

            if (Data.getData(Bukkit.getPlayer(event.getWhoClicked().getName()), "daily-reward-claimed").equals("none")) {
                new PlayerManager(Bukkit.getPlayer(event.getWhoClicked().getName())).addXP(2000);
                Data.editData(player, "daily-reward-claimed", String.valueOf(System.currentTimeMillis()));
                player.sendMessage("§aYou have successfully claimed §32,000 Hypixel Experience §aand §63,000 Arcade Coins§a!");
                event.setCancelled(true);
                player.closeInventory();
            } else {
                long time = (System.currentTimeMillis() - Long.parseLong(Data.getData(player, "daily-reward-claimed")));
                long finalTime = 86400000 - time;

                if (finalTime < 1) {
                    new PlayerManager(Bukkit.getPlayer(event.getWhoClicked().getName())).addXP(2000);
                    Data.editData(player, "daily-reward-claimed", String.valueOf(System.currentTimeMillis()));
                    player.sendMessage("§aYou have successfully claimed §32,000 Hypixel Experience §aand §63,000 Arcade Coins§a!");
                    event.setCancelled(true);
                    player.closeInventory();
                    return;
                }

                int h = (int) ((finalTime / 1000) / 3600);
                int m = (int) (((finalTime / 1000) / 60) % 60);
                int s = (int) ((finalTime / 1000) % 60);

                event.getWhoClicked().sendMessage("§cYou have claimed this reward recently! Check back in " + h + "h " + m + "m " + s + "s!");
                player.closeInventory();
                event.setCancelled(true);
                return;
            }
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Website")) {
            event.getWhoClicked().sendMessage("§6You have already claimed this reward");
            event.setCancelled(true);
            return;
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Survey")) {
            event.getWhoClicked().sendMessage("§cThere is no survey available right now! Please check back soon!");
            event.setCancelled(true);
            return;
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Mystery")) {
            event.getWhoClicked().sendMessage("§cThis is not currently available! Please check back soon!");
            event.setCancelled(true);
            return;
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Social Media")) {
            player.closeInventory();
            BookGUI.openBook(BookGUI.getSocialMediasBook(), player);
            return;
        }

        event.setCancelled(true);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void setItems() {

        inventory.setItem(29, makeItemOther(Material.MINECART, "§cSurvey", 1, 0, "§7There isn't a survey available\n§7right now!"));
        inventory.setItem(30, makeItemOther(Material.MINECART, "§6Social Media Awards", 1, 0, "§7Click to view all available\n§7Social Media Awards!"));
        inventory.setItem(31, makeItemOther(Material.DIAMOND_BLOCK, "§aDaily Reward", 1, 0, "§7Daily rewards for visiting our\n§7website including: §6Coins§7,\n§3Hypixel Experience§7,\n§bSkyWars Souls§7, §cUnique\n§cCosmetics §7and more!\n\n§7Current Streak: §b0\n\n§eClick here to get the link in chat!"));
        inventory.setItem(32, makeItemOther(Material.MINECART, "§6Website Link", 1, 0, "§7You have linked your account to\n§7the forums."));

        inventory.setItem(20, makeItemOther(Material.ENDER_CHEST, "§aMystery Box Delivery", 1, 0, "§7Your free monthly 1-Star\n§7Mystery Box for July have arrived!\n\n§cNot Available!"));
        inventory.setItem(21, makeItemOther(Material.ENDER_CHEST, "§aMystery Box Delivery", 5, 0, "§7Your free monthly §b5 §7(Total of §b5§7)\n§7Mystery Boxes for July have arrived!\n\n§7Requires §aVIP\n\n§cNot Available!"));
        inventory.setItem(22, makeItemOther(Material.ENDER_CHEST, "§aMystery Box Delivery", 5, 0, "§7Your free monthly §b5 §7(Total of §b10§7)\n§7Mystery Boxes for July have arrived!\n\n§7Requires §aVIP§6+\n\n§cNot Available!"));
        inventory.setItem(23, makeItemOther(Material.ENDER_CHEST, "§aMystery Box Delivery", 5, 0, "§7Your free monthly §b5 §7(Total of §b15§7)\n§7Mystery Boxes for July have arrived!\n\n§7Requires §bMVP\n\n§cNot Available!"));
        inventory.setItem(24, makeItemOther(Material.ENDER_CHEST, "§aMystery Box Delivery", 5, 0, "§7Your free monthly §b5 §7(Total of §b20§7)\n§7Mystery Boxes for July have arrived!\n\n§7Requires §bMVP§c+\n\n§cNot Available!"));

        if (Data.getData(player, "daily-reward-claimed").equals("none")) {
            inventory.setItem(33, makeItemOther(Material.MINECART, "§cDaily Reward", 1, 0, "§7Free 2,000 Network\n§7Experience and 3,000 Arcade\n§7Coins!"));
        } else {

            long time = (System.currentTimeMillis() - Long.parseLong(Data.getData(player, "daily-reward-claimed")));
            long finalTime = 86400000 - time;

            if (finalTime < 1) {
                inventory.setItem(33, makeItemOther(Material.MINECART, "§cDaily Reward", 1, 0, "§7Free 2,000 Network\n§7Experience and 3,000 Arcade\n§7Coins!"));
                return;
            }

            int h = (int) ((finalTime / 1000) / 3600);
            int m = (int) (((finalTime / 1000) / 60) % 60);
            int s = (int) ((finalTime / 1000) % 60);

            inventory.setItem(33, makeItemOther(Material.MINECART, "§cDaily Reward", 1, 0, "§7You have claimed this reward\n§7recently! Check back in " + h + "h " + m + "m\n§7" + s + "s!"));
        }
    }
}