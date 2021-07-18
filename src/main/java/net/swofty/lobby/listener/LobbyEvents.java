package net.swofty.lobby.listener;

import net.swofty.lobby.Data;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;

import java.io.IOException;

public class LobbyEvents implements Listener {
    @EventHandler
    public void onMobSpawn(SpawnerSpawnEvent event) throws IOException {
        event.setCancelled(true);
    }

    @EventHandler
    public void onMobSpawn2(SpawnerSpawnEvent event) throws IOException {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(BlockDamageEvent event) throws IOException {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent event) throws IOException {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage3(EntityDamageByBlockEvent event) throws IOException {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage4(EntityDamageByEntityEvent event) throws IOException {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) throws IOException {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) throws IOException {
        if (Data.getData(event.getPlayer(), "build").equals("false")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) throws IOException {
        if (Data.getData(event.getPlayer(), "build").equals("false")) {
            event.setCancelled(true);
        }
    }
}
