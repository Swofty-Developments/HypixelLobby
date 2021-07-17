package net.maploop.dnum.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public abstract class GUI implements InventoryHolder {
    public PlayerMenuUtility playerMenuUtility;
    public Inventory inventory;
    public ItemStack FILLER_GLASS = makeItem(Material.STAINED_GLASS_PANE, " ", 1, 15);

    public GUI(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getTitle();

    public abstract int getSize();

    public abstract void onClick(InventoryClickEvent event);

    public abstract void onClose(InventoryCloseEvent event);

    public abstract void setItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSize(), getTitle());
        this.setItems();
        playerMenuUtility.getOwner().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setFilter() {
        for (int slots = 0; slots < getSize(); slots++) {
            if (inventory.getItem(slots) == null) {
                inventory.setItem(slots, FILLER_GLASS);
            }
        }
    }

    @Deprecated
    public ItemStack makeItem(Material material, String displayName, int amount, int durability, String... lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeItem(Material material, String displayName, int amount, int durability, String lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        String[] lore1 = lore.split("\n");
        meta.setLore(Arrays.asList(lore1));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeAdvancedItem(Material material, String displayName, int amount, int durability, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeSkullItem(String displayname, String owner, int amount, String lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(owner);
        String[] lore1 = lore.split("\n");
        meta.setLore(Arrays.asList(lore1));
        meta.setDisplayName(displayname);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack makeCustomSkullItem(String url, String displayname, int amount, String... lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        if (url.isEmpty()) return item;

        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        itemMeta.setDisplayName(displayname);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack makeTexturedSkullItem(String texture, String displayname, int amount, String... lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        if (texture == null) return item;

        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field profileField;
        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        itemMeta.setDisplayName(displayname);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public void fillBorder() {
        ItemStack item = FILLER_GLASS;
        int size = inventory.getSize();
        int rows = (size + 1) / 9;

        // Fill top
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, item);
        }

        // Fill bottom
        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, item);
        }

        // Fill sides
        for (int i = 2; i <= rows - 1; i++) {
            int[] slots = new int[]{i * 9 - 1, (i - 1) * 9};
            inventory.setItem(slots[0], item);
            inventory.setItem(slots[1], item);
        }
    }

    public void fillBottom() {
        ItemStack item = FILLER_GLASS;
        int size = inventory.getSize();
        int rows = (size + 1) / 9;

        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, item);
        }
    }

    public void fillSides() {
        ItemStack item = FILLER_GLASS;
        int size = inventory.getSize();
        int rows = (size + 1) / 9;

        for (int i = 2; i <= rows - 1; i++) {
            int[] slots = new int[]{i * 9 - 1, i * 9};
            inventory.setItem(slots[0], item);
            inventory.setItem(slots[1], item);
        }
    }

    public void fillSides(ItemStack item) {
        int size = inventory.getSize();
        int rows = (size + 1) / 9;

        for (int i = 2; i <= rows - 1; i++) {
            int[] slots = new int[]{i * 9 - 1, (i - 1) * 9};
            inventory.setItem(slots[0], item);
            inventory.setItem(slots[1], item);
        }
    }
}
