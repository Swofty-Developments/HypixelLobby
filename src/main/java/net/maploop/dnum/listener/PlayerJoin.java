package net.maploop.dnum.listener;

import net.maploop.dnum.Dnum;
import net.maploop.dnum.npc.NPC;
import net.maploop.dnum.npc.NPCRegistery;
import net.maploop.dnum.npc.PacketReader;
import net.maploop.dnum.util.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        new PacketReader(player).inject();

        NPCRegistery.rotationTaskMap.put(player.getUniqueId(), Dnum.getInstance().startRotating(player));
        NPCRegistery.despawnTaskMap.put(player.getUniqueId(), Dnum.getInstance().startShitScheduler(player));

        for(NPC npc : NPC.getNpcs()) {
            npc.spawn(player);
            if(npc.getLocation().distance(player.getLocation()) > 100) {
                NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), false);
            } else {
                NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        for (NPC npc : NPC.getNpcs()) {
            NPCRegistery.idfk.remove(event.getPlayer().getName() + "_" + npc.getParameters().idname());
            npc.despawn(event.getPlayer());
        }

        NPCRegistery.despawnTaskMap.get(event.getPlayer().getUniqueId()).cancel();
        NPCRegistery.rotationTaskMap.get(event.getPlayer().getUniqueId()).cancel();

        NPCRegistery.rotationTaskMap.remove(event.getPlayer().getUniqueId());
        NPCRegistery.despawnTaskMap.remove(event.getPlayer().getUniqueId());
    }
}
