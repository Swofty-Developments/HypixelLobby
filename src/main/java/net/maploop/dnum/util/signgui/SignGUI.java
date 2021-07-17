package net.maploop.dnum.util.signgui;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.maploop.dnum.Dnum;
import net.maploop.dnum.util.protocol.WrapperPlayClientUpdateSign;
import net.maploop.dnum.util.protocol.WrapperPlayServerBlockChange;
import net.maploop.dnum.util.protocol.WrapperPlayServerOpenSignEntity;
import net.maploop.dnum.util.protocol.WrapperPlayServerUpdateSign;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.UUID;

public class SignGUI {
    private static Hashtable<UUID, BlockPosition> playerBlockPositions;//table for keeping of track of where everyone's 'fake' signs are

    public static void openSignEditor(Player player, String[] text, SignGUIUpdateHandler handler) {
        playerBlockPositions = new Hashtable<UUID, BlockPosition>();
        registerSignUpdateListener(handler);

        int x = player.getLocation().getBlockX();
        int y = 255;
        int z = player.getLocation().getBlockZ();
        com.comphenix.protocol.wrappers.BlockPosition bp = new com.comphenix.protocol.wrappers.BlockPosition(x, y, z);

        WrapperPlayServerBlockChange blockChangePacket = new WrapperPlayServerBlockChange();
        WrappedBlockData blockData = WrappedBlockData.createData(Material.SIGN_POST);
        blockChangePacket.setBlockData(blockData);
        blockChangePacket.setLocation(bp);
        blockChangePacket.sendPacket(player);

        WrapperPlayServerUpdateSign updateSignPacket = new WrapperPlayServerUpdateSign();
        updateSignPacket.setLocation(new BlockPosition(x, y, z));
        WrappedChatComponent[] lines = new WrappedChatComponent[4];
        lines[0] = WrappedChatComponent.fromText(text[0]);
        lines[1] = WrappedChatComponent.fromText(text[1]);
        lines[2] = WrappedChatComponent.fromText(text[2]);
        lines[3] = WrappedChatComponent.fromText(text[3]);
        updateSignPacket.setLines(lines);

        updateSignPacket.sendPacket(player);

        //open the gui
        WrapperPlayServerOpenSignEntity packet = new WrapperPlayServerOpenSignEntity();
        packet.setLocation(new BlockPosition(x, y, z));
        packet.sendPacket(player);

        playerBlockPositions.put(player.getUniqueId(), bp);
    }

    private static void registerSignUpdateListener(SignGUIUpdateHandler handler) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        if(playerBlockPositions == null)
        {
            playerBlockPositions = new Hashtable<UUID, BlockPosition>();
        }
        manager.addPacketListener(new PacketAdapter(Dnum.getInstance(), PacketType.Play.Client.UPDATE_SIGN){
            @Override
            public void onPacketReceiving(PacketEvent event)
            {
                String[] text = new String[4];
                Player player = event.getPlayer();
                WrapperPlayClientUpdateSign packet = new WrapperPlayClientUpdateSign(event.getPacket());
                com.comphenix.protocol.wrappers.BlockPosition bp = packet.getLocation();

                BlockPosition playerBlockPos = playerBlockPositions.get(player.getUniqueId());
                if(playerBlockPos != null)
                {
                    //we only care about update packets from the player block location since that's the location we sent our open packet to
                    if(bp.getX() == playerBlockPos.getX() && bp.getY() == playerBlockPos.getY() && bp.getZ() == playerBlockPos.getZ())
                    {
                        for(int i = 0; i < packet.getLines().length; i++)
                        {
                            WrappedChatComponent chat = packet.getLines()[i];
                            String str = StringEscapeUtils.unescapeJavaScript(chat.getJson());
                            str = str.substring(1, str.length()-1);
                            text[i] = str;
                        }

                        WrapperPlayServerBlockChange blockChangePacket = new WrapperPlayServerBlockChange();
                        WrappedBlockData blockData = WrappedBlockData.createData(Material.AIR);
                        blockChangePacket.setBlockData(blockData);
                        blockChangePacket.setLocation(playerBlockPos);
                        blockChangePacket.sendPacket(player);

                        playerBlockPositions.remove(player.getUniqueId());

                        //trigger the sign update event
                        SignGUIUpdateEvent updateEvent = new SignGUIUpdateEvent(player, text);
                        Bukkit.getServer().getPluginManager().callEvent(updateEvent);
                        handler.onUpdate(updateEvent);
                    }
                }
            }

            @Override
            public void onPacketSending(PacketEvent event)
            {

            }
        });
    }

    public interface SignGUIUpdateHandler {
        void onUpdate(SignGUIUpdateEvent event);
    }
}
