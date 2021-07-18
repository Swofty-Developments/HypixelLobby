package net.swofty.lobby.npc.npcs;

import net.swofty.lobby.Loader;
import net.swofty.lobby.npc.NPC;
import net.swofty.lobby.npc.NPCParameters;
import net.swofty.lobby.util.BookGUI;
import net.swofty.lobby.util.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@NPCParameters(
        idname = "[NPC] Skyblock",
        name = "§e0 Playing",
        id = 3,
        signature = "XlI+W3ye+8a1kOQLxIgXUmI4vdZ5A9kJ8MyVbDMy/BOb9iN/L/PPwmwl9iT7gYKFaWd+/6m+9byDZLtrTkJGkse/GT+j9ubLeVw41eAlGT5esttrxG2BA9FGDRBkFIsAyptCoUg+81xcLTT1y3KKeLOshpbNel4ZTH89OGljA5j1LhVO6oM6ZOowYa4RsFPnuu5A0VKlukRG9zZmB3zExJljb50PuRhhavsNSOHEVSO0zEzBYI09EmXt7G+zKf/QXvhuQJiEOuYVL2Fmzhye3hbD5s/tLcQuCqz7k/yxnTxTkPpBi5vd+7T2gaNrWHEir8bzqwoFqP03xOKAC2ia1w6/bvobuqso3zGRH3veAAHKyh12xmWJcpywo2026b4Pwog1dHV3jcesRl6GisnZFXaHyTgMg4T3B0LCQkWsZoE41USDAhVC0+E8+lxtJYTZEyUtWM3MMJNXWROjQRNh4e35Jwipqk9YLmfdXoDYj6YytMTO4YGwmlEgqeFbpcjnSia6CF7vs4jemWoqoYKGdih5Id4PIBOJd55Ij47HS1WOt7apQHEtFRWPMW/URr0fHUCYELpVAPEhOh6jqfnwFJZJhZh/p/MyXP5kaVjWiXD5CmTl0Ujo5U5fHmmTbMrU78suUX6pSibZDSfYs9bIYaZLD7BYbbTW4xPYPqykiEI=",
        texture = "ewogICJ0aW1lc3RhbXAiIDogMTYwNzk2NDk0MTg5MiwKICAicHJvZmlsZUlkIiA6ICJhNzdkNmQ2YmFjOWE0NzY3YTFhNzU1NjYxOTllYmY5MiIsCiAgInByb2ZpbGVOYW1lIiA6ICIwOEJFRDUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzYxMmE3YjE3NzMwNzk5M2RmYTRmYzc2OWI3ZGEyYWQwOWQ3ODQ5Y2M0NDhiMzQ0ZWY1YWRhNzJlY2E3NWQ1NSIKICAgIH0KICB9Cn0=",

        world = "world",
        x = 7.5,
        y = 81,
        z = 22.5,

        looking = true
)
public class SkyblockNPC extends NPC {
    @Override
    public void onClick(PlayerClickNPCEvent event) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF("islands");
        } catch (IOException e) {
            Bukkit.getLogger().info("Error when transporting cross server");
        }
        Bukkit.getPlayer(event.getPlayer().getName()).sendPluginMessage(Loader.getInstance(), "BungeeCord", b.toByteArray());
    }
}
