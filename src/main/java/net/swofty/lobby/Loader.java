package net.swofty.lobby;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.event.user.track.UserDemoteEvent;
import net.luckperms.api.event.user.track.UserPromoteEvent;
import net.luckperms.api.node.NodeType;
import net.swofty.lobby.command.CommandLoader;
import net.swofty.lobby.command.commands.*;
import net.swofty.lobby.listener.*;
import net.swofty.lobby.manager.PlayerManager;
import net.swofty.lobby.npc.NPC;
import net.swofty.lobby.npc.NPCRegistery;
import net.swofty.lobby.npc.npcs.ExampleNPC;
import net.swofty.lobby.npc.npcs.HytaleNPC;
import net.swofty.lobby.npc.npcs.SkyblockNPC;
import net.swofty.lobby.util.DLog;
import net.swofty.lobby.util.Runnable;
import net.swofty.lobby.util.Util;
import net.swofty.lobby.util.scoreboard.GlobalScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;

import java.lang.reflect.Field;

/**
 * Dnum framework, a simple framework with a lot of useful utilities!
 * Made for those who are lazy.
 * This framework includes: An easy to use command framework,
 * An easy to use GUI framework, and useful things in utilities!
 *
 * @author Maploop
 */
public final class Loader extends JavaPlugin {
    private static Loader dnum;
    public CommandMap commandMap;
    public CommandLoader cl;
    NPCRegistery nr;

    @Override
    public void onEnable() {
        Data.initialize();
        LuckpermsListener();
        dnum = this;
        cl = new CommandLoader();
        nr = new NPCRegistery();
        try{
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            this.commandMap = (CommandMap) f.get(Bukkit.getServer());
        }catch (Exception e) {
            e.printStackTrace();
        }

        DLog.info("Loading commands...");
        loadCommands();
        loadNpcs();
        startNPCScheduler();
        DLog.info("Loading listeners...");
        loadListeners();

        DLog.info("Plugin was enabled!");

        Runnable.asyncSecond();

    }

    public BukkitTask startRotating(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                for (NPC npc : NPC.getNpcs()) {
                    if(!npc.getParameters().looking()) continue;

                    if(player.getWorld() != Bukkit.getWorld("world")) return;
                    if (npc.getLocation().distance(player.getLocation()) < 20)
                        npc.rotateHeadtoPlayer(player);
                }
            }
        }.runTaskTimer(this, 0, 1);
    }

    public void loadNpcs() {
        nr.register(new ExampleNPC());
        nr.register(new HytaleNPC());
        nr.register(new SkyblockNPC());
    }

    public BukkitTask startNPCScheduler() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    for (NPC npc : NPC.getNpcs()) {
                        if (player.getWorld().getName() != npc.getLocation().getWorld().getName()) continue;

                        if (npc.getLocation().distance(player.getLocation()) > 100) {
                            npc.despawn(player);
                            NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), false);
                        } else {
                            if (NPCRegistery.idfk.get(player.getName() + "_" + npc.getParameters().idname())) return;
                            npc.despawn(player);
                            Bukkit.getScheduler().runTaskLater(Loader.getInstance(), () -> npc.spawn(player), 5);
                            NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), true);
                        }
                    }
                });
            }
        }.runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        DLog.info("Plugin was disabled.");
    }

    public static Loader getInstance() { return dnum; }

    private void loadCommands() {
        cl.register(new Command_test());
        cl.register(new Command_rank());
        cl.register(new Command_simulateevent());
        cl.register(new Command_gm());
        cl.register(new Command_parkour());
        cl.register(new Command_build());
    }
    private void loadListeners() {
        PluginManager m = this.getServer().getPluginManager();
        m.registerEvents(new InventoryClick(), this);
        m.registerEvents(new SignGUIUpdate(), this);
        m.registerEvents(new PlayerJoin(), this);
        m.registerEvents(new PlayerMoveEvent(), this);
        m.registerEvents(new LobbyEvents(), this);
        m.registerEvents(new ItemInteractEvent(), this);
    }

    public void LuckpermsListener() {
        LuckPerms api = LuckPermsProvider.get();

        EventBus eventBus = api.getEventBus();

        eventBus.subscribe(this, UserDemoteEvent.class, this::onUserDemote);
        eventBus.subscribe(this, UserPromoteEvent.class, this::onUserPromote);
        eventBus.subscribe(this, NodeAddEvent.class, this::nodeAdd);
        eventBus.subscribe(this, NodeRemoveEvent.class, this::nodeRemove);
    }

    private void nodeAdd(NodeAddEvent event) {
        if (event.isUser() && event.getNode().getType() == NodeType.INHERITANCE) {
            Bukkit.getPlayer(event.getTarget().getFriendlyName()).sendMessage(Util.colorize("&cAs your rank was changed you have lost your plus color"));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + event.getTarget().getFriendlyName() + " meta clear");

            Data.reloadRank(Bukkit.getPlayer(event.getTarget().getFriendlyName()));
        }
    }

    private void nodeRemove(NodeRemoveEvent event) {
        if (event.isUser() && event.getNode().getType() == NodeType.INHERITANCE) {
            Bukkit.getPlayer(event.getTarget().getFriendlyName()).sendMessage(Util.colorize("&cAs your rank was changed you have lost your plus color"));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + event.getTarget().getFriendlyName() + " meta clear");

            Data.reloadRank(Bukkit.getPlayer(event.getTarget().getFriendlyName()));
        }
    }

    private void onUserPromote(UserPromoteEvent event) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + event.getUser().getUsername() + " meta clear");
        Bukkit.getPlayer(event.getUser().getUsername()).sendMessage(Util.colorize("&cAs your rank was changed you have lost your plus color"));

        Data.reloadRank(Bukkit.getPlayer(event.getUser().getUsername()));
    }

    private void onUserDemote(UserDemoteEvent event) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + event.getUser().getUsername() + " meta clear");
        Bukkit.getPlayer(event.getUser().getUsername()).sendMessage(Util.colorize("&cAs your rank was changed you have lost your plus color"));

        Data.reloadRank(Bukkit.getPlayer(event.getUser().getUsername()));
    }

}
