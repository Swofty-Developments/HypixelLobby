package net.swofty.lobby.manager;

import net.swofty.lobby.Data;
import net.swofty.lobby.util.BookGUI;
import net.swofty.lobby.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerManager implements HypixelPlayer {

    private final Player player;

    private final String id;

    public PlayerManager(Player player) {
        this.player = player;
        this.id = player.getUniqueId().toString();
    }

    @Override
    public String getRank() {
        return Data.getData(player, "rank");
    }

    @Override
    public String getRankPrefix() {
        switch (Data.getData(player, "rank")) {

            case "default":
                return "§7";

            case "vip":
                return "§a[VIP] ";

            case "vip+":
                return "§a[VIP§c+§a] ";

            case "mvp+":
                return "§b[MVP§c+§b] ";

            case "mvp++":
                return "§6[MVP§c++§6] ";

            case "helper":
                return "§9[HELPER] ";

            case "mod":
                return "§2[MOD] ";

            case "admin":
                return "§c[ADMIN] ";

        }
        return null;
    }

    @Override
    public String getRankColour() {
        switch (Data.getData(player, "rank")) {

            case "default":
                return "§7";

            case "vip":
            case "vip+":
                return "§a";

            case "mvp+":
                return "§b";

            case "mvp++":
                return "§6";

            case "helper":
                return "§9";

            case "mod":
                return "§2";

            case "admin":
                return "§c";

        }
        return null;
    }
    @Override
    public void sendMessage(String s) {
        player.sendMessage(Util.colorize(s));
    }

    @Override
    public void setRank(String rank) {
        Data.editData(player, "rank", rank);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set " + rank);

        BookGUI.openBook(BookGUI.getRankChangeBook(rank), player);
    }

    @Override
    public void addXP(int XP) {
        int totalXP = XP + Integer.parseInt(Data.getData(player, "xp"));

        if (totalXP > (Integer.parseInt(Data.getData(player, "level")) + 5000 + (Integer.parseInt(Data.getData(player, "level")) * 750))) {
            Data.editData(player, "level", String.valueOf(Integer.parseInt(Data.getData(player, "level")) + 1));
            Data.editData(player, "xp", "0");
            sendMessage("§a▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃");
            sendMessage("                              §a§ka§r §6LEVEL UP! §a§k9");
            sendMessage(" ");
            sendMessage("                   §7You are now §3Hypixel Level §a" + Data.getData(player, "level"));
            sendMessage(" ");
            sendMessage("                   §66.0x §7Coin Multiplier §aUnlocked§7!");
            sendMessage(" ");
            sendMessage("                  §eClaim your reward in the lobby!");
            sendMessage("§a▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃▃");
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 100, 100);
            player.setLevel(Integer.parseInt(Data.getData(player, "level")));

        } else {
            Data.editData(player, "xp", String.valueOf(Integer.parseInt(Data.getData(player, "xp")) + XP));
        }
    }


}
