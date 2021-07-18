package net.swofty.lobby.util.signgui;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.swofty.lobby.Loader;
import net.swofty.lobby.util.protocol.WrapperPlayClientUpdateSign;
import net.swofty.lobby.util.protocol.WrapperPlayServerBlockChange;
import net.swofty.lobby.util.protocol.WrapperPlayServerOpenSignEntity;
import net.swofty.lobby.util.protocol.WrapperPlayServerUpdateSign;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;

public class SignGUI {
    private PacketAdapter packetListener;
    private final Player player;
    private Sign sign;
    private final LeaveListener listener = new LeaveListener();

    public SignGUI(Player player, String[] lines, UpdateHandler uh) {
        this.player = player;
        int x_start = player.getLocation().getBlockX();
        int y_start = 255;
        int z_start = player.getLocation().getBlockZ();

        while (!player.getWorld().getBlockAt(x_start, y_start, z_start).getType().equals(Material.AIR)) {
            y_start--;
            if (y_start == 1)
                return;
        }

        Material material = Material.getMaterial("OAK_WALL_SIGN");
        if (material == null)
            material = Material.getMaterial("WALL_SIGN");
        player.getWorld().getBlockAt(x_start, y_start, z_start).setType(material);
        sign = (Sign) player.getWorld().getBlockAt(x_start, y_start, z_start).getState();

        sign.setLine(1, lines[0]);
        sign.setLine(2, lines[1]);
        sign.setLine(3, lines[2]);

        sign.update(false, false);

        Bukkit.getScheduler().runTaskLater(Loader.getInstance(), () ->
        {
            try {
                openSignEditor(player, sign);
            } catch (Exception ignored) {
            }
        }, 2);

        Bukkit.getPluginManager().registerEvents(listener, Loader.getInstance());
        registerSignUpdateListener(uh);
        //if(!auxiliar.equals(upperVersion?Material.OAK_WALL_SIGN:Material.getMaterial("WALL_SIGN")))
        //    Bukkit.getScheduler().runTaskLater(plugin, () -> p.getWorld().getBlockAt(x_start, y_start, z_start).setType(auxiliar), 40);
    }

    private static void openSignEditor(Player player, Sign sign) throws Exception {
        Object entityPlayer = getEntityPlayer(player);
        attachEntityPlayerToSign(entityPlayer, sign);
        Object position = getBlockPosition(sign.getBlock());
        Object packet = createPositionalPacket(position, "PacketPlayOutOpenSignEditor");
        sendPacketToEntityPlayer(packet, entityPlayer);
    }

    private static Object getEntityPlayer(Player player) throws Exception {
        Field entityPlayerField = getFirstFieldOfType(player,
                MinecraftReflector.getMinecraftServerClass("Entity"));
        return entityPlayerField.get(player);
    }

    private static void sendPacketToEntityPlayer(Object packet, Object entityPlayer) throws Exception {
        Object connection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
        connection
                .getClass()
                .getDeclaredMethod("sendPacket", MinecraftReflector.getMinecraftServerClass("Packet"))
                .invoke(connection, packet);
    }

    private static Object createPositionalPacket(Object position, String typeOfPacket) throws Exception {
        return createPositionalPacket(position, MinecraftReflector.getMinecraftServerClass(typeOfPacket));
    }

    private static Object createPositionalPacket(Object position, Class<?> typeOfPacket) throws Exception {
        return typeOfPacket
                .getConstructor(MinecraftReflector.getMinecraftServerClass("BlockPosition"))
                .newInstance(position);
    }

    private static Object getBlockPosition(Block block) throws Exception {
        return MinecraftReflector.getMinecraftServerClass("BlockPosition")
                .getConstructor(int.class, int.class, int.class)
                .newInstance(block.getX(), block.getY(), block.getZ());
    }

    private static void attachEntityPlayerToSign(Object entityPlayer, Sign sign) throws Exception {
        Object tileEntitySign = getTileEntitySign(sign);

        maketileEntitySignEditable(tileEntitySign);

        Field signEntityHumanField = getFirstFieldOfType(tileEntitySign,
                MinecraftReflector.getMinecraftServerClass("EntityHuman"));
        signEntityHumanField.set(tileEntitySign, entityPlayer);
    }

    private static Object getTileEntitySign(Sign sign) throws Exception {
        Field tileEntityField = getFirstFieldOfType(sign,
                MinecraftReflector.getMinecraftServerClass("TileEntity"));
        return tileEntityField.get(sign);
    }

    private static void maketileEntitySignEditable(Object tileEntitySign) throws Exception {
        Field signIsEditable = tileEntitySign.getClass().getDeclaredField("isEditable");
        signIsEditable.setAccessible(true);
        signIsEditable.set(tileEntitySign, true);
    }

    private static Field getFirstFieldOfType(Object source, Class<?> desiredType) throws NoSuchFieldException {
        return getFirstFieldOfType(source.getClass(), desiredType);
    }

    private static Field getFirstFieldOfType(Class<?> source, Class<?> desiredType) throws NoSuchFieldException {
        Class<?> ancestor = source;
        while (ancestor != null) {
            Field[] fields = ancestor.getDeclaredFields();
            for (Field field : fields) {
                Class<?> candidateType = field.getType();
                if (desiredType.isAssignableFrom(candidateType)) {
                    field.setAccessible(true);
                    return field;
                }
            }
            ancestor = ancestor.getSuperclass();
        }
        throw new NoSuchFieldException("Cannot match " + desiredType.getName() + " in ancestry of " + source.getName());
    }

    private class LeaveListener implements Listener {
        @EventHandler
        public void onLeave(PlayerQuitEvent e) {
            if (e.getPlayer().equals(player)) {
                sign.getBlock().setType(Material.AIR);
                ProtocolLibrary.getProtocolManager().removePacketListener(packetListener);
                HandlerList.unregisterAll(this);
            }
        }
    }

    private void registerSignUpdateListener(UpdateHandler uh) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        packetListener = new PacketAdapter(Loader.getInstance(), PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPlayer().equals(player)) {
                    String input;
                    if (Bukkit.getVersion().contains("1.8"))
                        input = event.getPacket().getChatComponentArrays().read(0)[0].getJson().replaceAll("\"", "");
                    else
                        input = event.getPacket().getStringArrays().read(0)[0];

                    uh.onUpdate(input);

                    Bukkit.getScheduler().runTask(Loader.getInstance(), () -> sign.getBlock().setType(Material.AIR));
                    manager.removePacketListener(this);
                    HandlerList.unregisterAll(listener);
                }
            }
        };

        manager.addPacketListener(packetListener);
    }

    public interface UpdateHandler {
        void onUpdate(String input);
    }
}