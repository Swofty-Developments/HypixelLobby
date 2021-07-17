package net.maploop.dnum.npc;

import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCRegistery {
    public static final Map<String, NPC> npcRegistery = new HashMap<>();
    public static final Map<String, Boolean> idfk = new HashMap<>();
    public static final Map<UUID, BukkitTask> despawnTaskMap = new HashMap<>();
    public static final Map<UUID, BukkitTask> rotationTaskMap = new HashMap<>();

    public static Map<String, NPC> getNpcRegistery() {
        return npcRegistery;
    }

    public static boolean add(String id, NPC npc) {
        npcRegistery.put(id, npc);
        return true;
    }

    public boolean register(NPC npc) {
        add(npc.getParameters().idname(), npc);
        npc.createNPC();

        return true;
    }

    public NPC getNpc(String idname) {
        return npcRegistery.get(idname);
    }
}
