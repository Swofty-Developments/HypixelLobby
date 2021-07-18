package net.swofty.lobby.util.tablist;

import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.swofty.lobby.Loader;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GlobalTablist {
    public static final List<ChatComponentText> header = new ArrayList<>();
    public static final List<ChatComponentText> footer = new ArrayList<>();

    public static int startDelay = 60, refreshRate = 20;

    public void add(TabPosition pos, String s) {
        switch (pos) {
            case HEADER:
                header.add(new ChatComponentText(s.replaceAll("&", "§")));
                break;
            case FOOTER:
                footer.add(new ChatComponentText(s.replaceAll("&", "§")));
                break;
        }
    }

    public void sendTablist(boolean refreshing) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        int c = 0, c1 = 0;

        try {
            if (refreshing) {
                new BukkitRunnable() {
                    int c = 0, c1 = 0;

                    @SneakyThrows
                    @Override
                    public void run() {
                        Field a = packet.getClass().getDeclaredField("a");
                        Field b = packet.getClass().getDeclaredField("b");
                        a.setAccessible(true);
                        b.setAccessible(true);

                        if(c >= header.size())
                            c = 0;
                        if(c1 >= footer.size())
                            c1 = 0;

                        a.set(packet, header.get(c));
                        b.set(packet, footer.get(c1));

                        for(Player player : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                        }
                        ++c;
                        ++c1;
                    }
                }.runTaskTimer(Loader.getInstance(), startDelay, refreshRate);
            } else {
                Field a = packet.getClass().getDeclaredField("a");
                Field b = packet.getClass().getDeclaredField("b");
                a.setAccessible(true);
                b.setAccessible(true);

                if(c >= header.size())
                    c = 0;
                if(c1 >= footer.size())
                    c1 = 0;

                a.set(packet, header.get(c));
                b.set(packet, footer.get(c1));

                for(Player player : Bukkit.getOnlinePlayers()) {
                    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                }
                ++c;
                ++c1;
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendTablist(Player player, boolean refreshing) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        int c = 0, c1 = 0;

        try {
            if (refreshing) {
                new BukkitRunnable() {
                    int c = 0, c1 = 0;

                    @SneakyThrows
                    @Override
                    public void run() {
                        Field a = packet.getClass().getDeclaredField("a");
                        Field b = packet.getClass().getDeclaredField("b");
                        a.setAccessible(true);
                        b.setAccessible(true);

                        if(c >= header.size())
                            c = 0;
                        if(c1 >= footer.size())
                            c1 = 0;

                        a.set(packet, header.get(c));
                        b.set(packet, footer.get(c1));

                        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

                        ++c;
                        ++c1;
                    }
                }.runTaskTimer(Loader.getInstance(), startDelay, refreshRate);
            } else {
                Field a = packet.getClass().getDeclaredField("a");
                Field b = packet.getClass().getDeclaredField("b");
                a.setAccessible(true);
                b.setAccessible(true);

                if(c >= header.size())
                    c = 0;
                if(c1 >= footer.size())
                    c1 = 0;

                a.set(packet, header.get(c));
                b.set(packet, footer.get(c1));

                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

                ++c;
                ++c1;
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public enum TabPosition {
        HEADER,
        FOOTER;
    }
}