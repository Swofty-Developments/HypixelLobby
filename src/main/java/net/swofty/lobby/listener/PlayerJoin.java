package net.swofty.lobby.listener;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.swofty.lobby.Data;
import net.swofty.lobby.Loader;
import net.swofty.lobby.manager.PlayerManager;
import net.swofty.lobby.npc.NPC;
import net.swofty.lobby.npc.NPCRegistery;
import net.swofty.lobby.npc.PacketReader;
import net.swofty.lobby.npc.npcs.TutorialNPC;
import net.swofty.lobby.util.Items;
import net.swofty.lobby.util.Util;
import net.swofty.lobby.util.hologram.Hologram;
import net.swofty.lobby.util.scoreboard.GlobalScoreboard;
import net.swofty.lobby.util.scoreboard.PlayerScoreboard;
import net.swofty.lobby.util.tablist.GlobalTablist;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;

import java.io.IOException;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "difficulty peaceful");

        player.sendMessage(" ");
        player.setGameMode(GameMode.SURVIVAL);

        try {

            TutorialNPC.tutorial1.remove(player);
            TutorialNPC.tutorial2.remove(player);
            TutorialNPC.tutorial3.remove(player);
            TutorialNPC.tutorial4.remove(player);
            TutorialNPC.tutorial5.remove(player);

        } catch (Exception e) {

        }

        Data.startLoginProcess(player);
        Data.reloadRank(player);
        Data.editData(player.getPlayer(), "parkour", "none");
        Data.editData(player, "checkpoint", "1");

        Boolean hidden = Boolean.valueOf(Data.getData(player, "hidden-players"));
        Items.giveSpawnItems(player, hidden);

        player.setLevel(1);
        player.teleport(new Location(Bukkit.getWorld("world"), 45.5, 86, 22.5));

        GlobalTablist tab = new GlobalTablist();
        tab.add(GlobalTablist.TabPosition.HEADER, "&bYou are playing on &e&lMC.HYPIXEL.NET");
        tab.add(GlobalTablist.TabPosition.FOOTER, "&aRanks, Boosters and MORE! &c&lSTORE.HYPIXEL.NET");
        tab.sendTablist(true);

        // new PlayerScoreboard("&e&lHYPIXEL", player, DisplaySlot.SIDEBAR, "&b ", "Rank: " + new PlayerManager(player).getRankPrefix(), "Mystery Dust: &a" + Data.getData(player, "mystery-dust"), "Achievements: &a" + Data.getData(player, "achievements"), "Level: &a" + Data.getData(player, "level"), "&a ", "Lobby: &a1", "Players: &a" + Bukkit.getOnlinePlayers().size(), "%%space%%", "&ewww.hypixel.net").sendScoreboard(true);


        EntityArmorStand h1 = new Hologram("§e§lFIRST LOOK TRAILER", player, new Location(Bukkit.getWorld("world"), 37.5, 87, 26.5), false).send();
        EntityArmorStand h2 = new Hologram("§b§lHYTALE", player, new Location(Bukkit.getWorld("world"), 37.5, 87.4, 26.5), false).send();
        EntityArmorStand h3 = new Hologram("§bSkyblock", player, new Location(Bukkit.getWorld("world"), 7.5, 82.2, 22.5), false).send();
        EntityArmorStand h4 = new Hologram("§e&lParkour Challenge", player, new Location(Bukkit.getWorld("world"), 41.5, 79.5, 55.5), false).send();
        EntityArmorStand h5 = new Hologram("§a&lStart", player, new Location(Bukkit.getWorld("world"), 41.5, 79.15, 55.5), false).send();
        EntityArmorStand h6 = new Hologram("§e&lRIGHT CLICK", player, new Location(Bukkit.getWorld("world"), 24.5, 82, 34.5), false).send();
        EntityArmorStand h7 = new Hologram("§bThe Delivery Man", player, new Location(Bukkit.getWorld("world"), 24.5, 82.4, 34.5), false).send();
        EntityArmorStand h8 = new Hologram("§e&lRIGHT CLICK", player, new Location(Bukkit.getWorld("world"), 24.5, 82, 10.5), false).send();
        EntityArmorStand h9 = new Hologram("§bTutorial", player, new Location(Bukkit.getWorld("world"), 24.5, 82.4, 10.5), false).send();
        EntityArmorStand h10 = new Hologram("§e§lCLICK", player, new Location(Bukkit.getWorld("world"), 20.5, 80.9, 44.5), false).send();
        EntityArmorStand h11 = new Hologram("§bMystery Vault", player, new Location(Bukkit.getWorld("world"), 20.5, 81.2, 44.5), false).send();
        EntityArmorStand h12 = new Hologram("§e§lCLICK", player, new Location(Bukkit.getWorld("world"), 20.5, 80.9, 0.5), false).send();
        EntityArmorStand h13 = new Hologram("§bMystery Vault", player, new Location(Bukkit.getWorld("world"), 20.5, 81.2, 0.5), false).send();


        player.setPlayerListName(new PlayerManager(player).getRankPrefix() + player.getName());

        new PacketReader(player).inject();

        NPCRegistery.rotationTaskMap.put(player.getUniqueId(), Loader.getInstance().startRotating(player));

        NPCRegistery.rotationTaskMap.put(player.getUniqueId(), Loader.getInstance().startRotating(player));

        for(NPC npc : NPC.getNpcs()) {
            npc.spawn(player);
            if (npc.getLocation().distance(player.getLocation()) > 100) {
                NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), false);
            } else {
                NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), true);
            }
        }

        switch (new PlayerManager(player).getRank()) {

            case "vip":
            case "vip+":
            case "mvp+":
                event.setJoinMessage(new PlayerManager(player).getRankPrefix() + event.getPlayer().getName() + " §6joined the lobby!");
                return;
            case "mvp++":
            case "helper":
            case "mod":
            case "admin":
                event.setJoinMessage(Util.colorize(" &b>&c>&a> " + new PlayerManager(player).getRankPrefix() + event.getPlayer().getName() + " §6joined the lobby! &a<&c<&b<"));
                return;
        }

        event.setJoinMessage("");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        for (NPC npc : NPC.getNpcs()) {
            NPCRegistery.idfk.remove(event.getPlayer().getName() + "_" + npc.getParameters().idname());
            npc.despawn(event.getPlayer());
        }

        new PacketReader(event.getPlayer()).uninject();

        NPCRegistery.rotationTaskMap.get(event.getPlayer().getUniqueId()).cancel();

        NPCRegistery.rotationTaskMap.remove(event.getPlayer().getUniqueId());
        event.setQuitMessage("");
    }
}
