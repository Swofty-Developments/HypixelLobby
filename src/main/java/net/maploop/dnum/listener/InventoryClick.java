package net.maploop.dnum.listener;

import net.maploop.dnum.gui.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClick implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;

        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof GUI) {
            GUI GUI = (GUI) holder;
            GUI.onClick(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if(holder instanceof GUI) {
            GUI gui = (GUI) holder;
            gui.onClose(event);
        }
    }
}
