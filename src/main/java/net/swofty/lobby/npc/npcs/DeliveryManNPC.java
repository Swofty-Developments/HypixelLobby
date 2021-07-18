package net.swofty.lobby.npc.npcs;

import net.swofty.lobby.gui.GUI;
import net.swofty.lobby.gui.guis.DeliveryManGUI;
import net.swofty.lobby.npc.NPC;
import net.swofty.lobby.npc.NPCParameters;
import net.swofty.lobby.util.BookGUI;

@NPCParameters(
        idname = "[NPC] Delivery-Man",
        name = "§7",
        id = 4,
        signature = "ZwRL3NpYX8hGxzxwB4Fd/agijK34sBiR62lecprwSQ6G+L6YDx4sJJv2cZ0Y+VHI0KA/Y14MCd3G+vPU/l/Crcr3zL6qyaGesf7ky6lToLpXmPWva872DTLhPR9RRuvx9tdw8fxSAX+kKI0tfWKUSEiY5OnIUOd2YjqdRGQqQEGC9gDE837WHxNyllBwpT0qodJwUR0XKM6Nhx4xV9Z57I6IYxhRhj85a7kOcjZUzr51Hyik3rIruXtS7Y1bqKZMtqj3t2/LvsKeMW27BsSZIyURuAUt/oeJ18dAepo+joHgkqR4USHH6bcc/kpXFroAd7QtW/bzgdymYaX38i25N3S+QYpV8e9Vl59FYzych/v1vJFcuKt4BCIzBn2Lox3RajTRfnTfCfb/bT8Tge/U0EiG3bPT1ZfmDXuSan9GGu2WBhW9P0qT+xQb2U+BEOlAONkW7F4MtRUaCNEAaetYJvev70zHkWfJN44vxNKda+XHoCquUZCD2vBSauXOIaMugdQLH+bgP9vTpdhOSDRojlnH3CoVHkWPK/gQyAiYl/dhUyHUtzkRM6yESwEPosYZb3l8VOiAnetbS4iKw8BpMRtNE/HrRhT1rc/djL6g+tmbAtpKH5vXlhxf1UT0E1AySYxxikUgdF5DqolWGbZ8sk5D8Zz40DdmQyZEBSuZOtk=",
        texture = "ewogICJ0aW1lc3RhbXAiIDogMTYyMzE1OTU4MzU4MiwKICAicHJvZmlsZUlkIiA6ICIxYTc1ZTNiYmI1NTk0MTc2OTVjMmY4NTY1YzNlMDAzZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUZXJvZmFyIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2M3NjcwNjg4MWIxZDNmNTc1YjU1ZTE0YTcxODViYjk0NDcwNTc1Zjk1ODllZTU4YzVlYTQ4YjBjYjlhYmNkMmUiCiAgICB9CiAgfQp9",

        world = "world",
        x = 24.5,
        y = 81,
        z = 34.5,

        looking = true
)
public class DeliveryManNPC extends NPC {
    @Override
    public void onClick(PlayerClickNPCEvent event) {
        new DeliveryManGUI().open(event.getPlayer());
    }
}
