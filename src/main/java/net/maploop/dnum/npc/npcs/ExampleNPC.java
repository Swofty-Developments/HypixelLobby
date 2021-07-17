package net.maploop.dnum.npc.npcs;

import net.maploop.dnum.npc.NPC;
import net.maploop.dnum.npc.NPCParameters;

@NPCParameters(
        idname = "npc_example",
        name = "&2Example!",
        id = 1,
        signature = "",
        texture = "",

        world = "world",
        x = 0,
        y = 0,
        z = 0,

        looking = true
)
public class ExampleNPC extends NPC {
    @Override
    public void onClick(PlayerClickNPCEvent event) {
        event.getPlayer().sendMessage("Hello!");
    }
}
