package net.swofty.lobby.npc;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import net.swofty.lobby.Loader;
import net.swofty.lobby.util.Messaging;
import net.swofty.lobby.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class NPC extends Reflections {
    @Getter
    private final NPCParameters parameters;

    private final int entityID;
    private final Location location;
    private final GameProfile gameprofile;
    private final String texture;
    private final String signature;
    private PacketPlayOutNamedEntitySpawn spawnPacket;
    private PacketPlayOutAnimation animationPacket;
    private int stand1id;
    private int stand2id;
    private static final ArrayList<NPC> npcs = new ArrayList<>();
    public abstract void onClick(PlayerClickNPCEvent event);
    public NPC() {
        this.parameters = this.getClass().getAnnotation(NPCParameters.class);
        this.entityID = parameters.id();
        gameprofile = new GameProfile(UUID.randomUUID(), Util.colorize(parameters.name()));
        this.texture = parameters.texture();
        this.signature = parameters.signature();
        this.location = new Location(Bukkit.getWorld(parameters.world()), parameters.x(), parameters.y(), parameters.z(), parameters.yaw(), parameters.pitch());
    }
    public void createNPC() {
        System.out.println("NPC " + parameters.idname() + " was successfully created and put into place.");
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
        setValue(packet, "a", entityID);
        setValue(packet, "b", gameprofile.getId());
        setValue(packet, "c", getFixLocation(location.getX()));
        setValue(packet, "d", getFixLocation(location.getY()));
        setValue(packet, "e", getFixLocation(location.getZ()));
        setValue(packet, "f", getFixRotation(location.getYaw()));
        setValue(packet, "g", getFixRotation(location.getPitch()));
        setValue(packet, "h", 0);
        DataWatcher w = new DataWatcher(null);
        w.a(6, (float) 20);
        w.a(10, (byte) 127);
        setValue(packet, "i", w);
        gameprofile.getProperties().put("textures", new Property("textures", texture, signature));
        spawnPacket = packet;
        npcs.add(this);
    }
    public void removeNPC(Player player) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entityID);
        rmvFromTablist(player);
        npcs.remove(this);
    }
    public void spawn(Player player) {
        addToTablist(player);
        sendPacket(spawnPacket, player);
        new BukkitRunnable() {
            @Override
            public void run() {
                rmvFromTablist(player);
            }
        }.runTaskLater(Loader.getInstance(), 80);
    }
    public void despawn(Player player) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entityID);
        rmvFromTablist(player);
        sendPacket(packet, player);
    }
    public void spawnStands() {
        Location loc = getLocation();
    }
    public void despawnStands(int id) {
    }
    public void addToTablist(Player player) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString("§8" + this.getParameters().idname())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);
        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
        setValue(packet, "b", players);
        sendPacket(packet, player);
    }
    public void rmvFromTablist(Player player) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString("§8" + this.getParameters().idname())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);
        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
        setValue(packet, "b", players);
        sendPacket(packet, player);
    }
    public void rotateHeadtoPlayer(Player player) {
        Location npcloc = location.setDirection(player.getLocation().subtract(location).toVector());
        float yaw1 = npcloc.getYaw();
        float pitch1 = npcloc.getPitch();
        PacketPlayOutEntity.PacketPlayOutEntityLook packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(entityID, getFixRotation(yaw1), getFixRotation(pitch1), true);
        PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
        setValue(packetHead, "a", entityID);
        setValue(packetHead, "b", getFixRotation(yaw1));
        sendPacket(packet, player);
        sendPacket(packetHead, player);
    }
    public int getFixLocation(double pos) {
        return MathHelper.floor(pos * 32.0D);
    }
    public byte getFixRotation(float rotation) {
        return (byte) ((int) (rotation * 256.0F / 360.0F));
    }
    public static ArrayList<NPC> getNpcs() {
        return npcs;
    }
    public Location getLocation() {
        return location;
    }
    public int getEntityID() {
        return entityID;
    }

    public static final class DespawnPreventer implements Listener {

        // Method for checking if player is seeing NPCs or not.
        // If yes, we don't do anything, if not, we de-spawn and then re-spawn the NPCs
        public void start() {
            // Starting a scheduler every 20 ticks (1 second)
            Bukkit.getScheduler().runTaskTimer(Loader.getInstance(), () -> {
                // Getting all online players and functioning these for each one of them.
                Bukkit.getOnlinePlayers().forEach(player -> {
                    // Getting all NPCs
                    for (NPC npc : NPC.getNpcs()) {
                        List<String> viewiing = NPCRegistery.VIEWING_NPCS.get(player.getName());

                        if (npc.getLocation().distance(player.getLocation()) >= 60) {
                            npc.despawn(player);

                            // Player HashMap for checking if the player is looking at a npc.
                            viewiing.remove(npc.getParameters().idname());
                            NPCRegistery.VIEWING_NPCS.put(player.getName(), viewiing);

                            // NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), false);
                        } else {
                            // If player is already looking at the NPC we return.
                            if (viewiing.contains(npc.getParameters().idname())) return;
                            // if (NPCRegistery.idfk.get(player.getName() + "_" + npc.getParameters().idname())) return;

                            // Logging the fact that one NPC was spawned for a player.
                            Messaging.debug("[", player.getName(), "] <= [", npc.getParameters().id(), "] (NPC LOG)");
                            npc.despawn(player);
                            Bukkit.getScheduler().runTaskLater(Loader.getInstance(), () -> npc.spawn(player), 5);

                            // Putting the player as true in the HashMap, this indicates that they can see a specific NPC.
                            viewiing.add(npc.getParameters().idname());
                            NPCRegistery.VIEWING_NPCS.put(player.getName(), viewiing);
                            // NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), true);
                        }
                    }
                });
            }, 10, 20);
        }
    }
    public static class PlayerClickNPCEvent extends Event implements Cancellable {
        private final Player player;
        private final NPC npc;
        private boolean isCancelled;
        @Getter
        private final int entityId;
        private static final HandlerList HANDLERS = new HandlerList();
        @Getter
        private final ClickType clickType;
        public PlayerClickNPCEvent(Player player, ClickType clickType, int entityId, NPC npc) {
            this.player = player;
            this.npc = npc;
            this.clickType = clickType;
            this.entityId = entityId;
        }
        public NPC getNpc() {
            return npc;
        }
        public Player getPlayer() {
            return player;
        }
        @Override
        public HandlerList getHandlers() {
            return HANDLERS;
        }
        public static HandlerList getHandlerList() {
            return HANDLERS;
        }
        @Override
        public boolean isCancelled() {
            return isCancelled;
        }
        @Override
        public void setCancelled(boolean b) {
            isCancelled = b;
        }
        public enum ClickType {
            LEFT,
            RIGHT,
            SHIFT_LEFT,
            SHIFT_RIGHT
        }
    }
}