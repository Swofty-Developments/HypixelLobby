package net.swofty.lobby.gui.guis;

import net.swofty.lobby.gui.GUI;
import net.swofty.lobby.gui.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ExampleGUI extends GUI {

    @Override
    public String getTitle() {
        return "Example";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void setItems() {

    }
}
