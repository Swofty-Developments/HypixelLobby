package net.swofty.lobby;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.swofty.lobby.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Data {

    public static void initialize() {
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("HypixelLobby").getDataFolder(), File.separator + "PlayerDatabase");

        userdata.mkdirs();
    }

    public static void editData(Player player, String dataType, String newValue) {
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("HypixelLobby").getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + player.getPlayer().getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        try {
            playerData.set(dataType, newValue);

            playerData.save(f);
        } catch (Exception exception) {
            exception.printStackTrace();
            Bukkit.getLogger().info("Could not edit " + player.getName() + " data");
        }
    }

    public static void startLoginProcess(Player player) throws IOException {
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("HypixelLobby").getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + player.getPlayer().getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        if (!f.exists()) {
            try {
                playerData.createSection("rank");
                playerData.createSection("lastlogin");
                playerData.createSection("parkour");
                playerData.createSection("checkpoint");
                playerData.createSection("hidden-players");
                playerData.createSection("parkour-best");
                playerData.createSection("mystery-dust");
                playerData.createSection("achievements");
                playerData.createSection("level");
                playerData.createSection("build");

                playerData.set("rank", "default");
                playerData.set("lastlogin", System.currentTimeMillis());
                playerData.set("parkour", "none");
                playerData.set("checkpoint", "1");
                playerData.set("hidden-players", "false");
                playerData.set("parkour-best", "0");
                playerData.set("mystery-dust", "0");
                playerData.set("achievements", "0");
                playerData.set("level", "1");
                playerData.set("build", "false");

                playerData.save(f);
            } catch (Exception exception) {
                exception.printStackTrace();
                Bukkit.getLogger().info("Could not make " + player.getName() + " data");
            }
        } else {
            playerData.set("lastlogin", System.currentTimeMillis());

            playerData.save(f);
        }
    }

    public static String getData(Player player, String dataType) {
        File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("HypixelLobby").getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + player.getPlayer().getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        String requestedData = "err";

        try {
            requestedData = playerData.getString(String.valueOf(dataType));
        } catch (Exception exception) {
            exception.printStackTrace();
            Bukkit.getLogger().info("Could not get " + player.getName() + " data");
        }

        return requestedData;
    }

    public static void reloadRank(Player player) {

        List<User> usersInGroupDefault = Util.getUsersInGroup("default");
        List<User> usersInGroupVIP = Util.getUsersInGroup("vip");
        List<User> usersInGroupVIPPlus = Util.getUsersInGroup("vip+");
        List<User> usersInGroupMVPPlus = Util.getUsersInGroup("mvp+");
        List<User> usersInGroupMVPPlusPlus = Util.getUsersInGroup("mvp++");
        List<User> usersInGroupHelper = Util.getUsersInGroup("helper");
        List<User> usersInGroupMod = Util.getUsersInGroup("mod");
        List<User> usersInGroupAdmin = Util.getUsersInGroup("admin");
        LuckPerms api = LuckPermsProvider.get();
        UserManager userManager = api.getUserManager();

        if (usersInGroupDefault.contains(userManager.getUser(player.getUniqueId()))) {
            Data.editData(player, "rank", "default");
        } else

        if (usersInGroupVIP.contains(userManager.getUser(player.getUniqueId()))) {
            Data.editData(player, "rank", "vip");
        } else

        if (usersInGroupVIPPlus.contains(userManager.getUser(player.getUniqueId()))) {
            Data.editData(player, "rank", "vip+");
        } else

        if (usersInGroupMVPPlus.contains(userManager.getUser(player.getUniqueId()))) {
            Data.editData(player, "rank", "mvp+");
        } else

        if (usersInGroupMVPPlusPlus.contains(userManager.getUser(player.getUniqueId()))) {
            Data.editData(player, "rank", "mvp++");
        } else

        if (usersInGroupHelper.contains(userManager.getUser(player.getUniqueId()))) {
            Data.editData(player, "rank", "helper");
        } else

        if (usersInGroupMod.contains(userManager.getUser(player.getUniqueId()))) {
            Data.editData(player, "rank", "mod");
        } else

        if (usersInGroupAdmin.contains(userManager.getUser(player.getUniqueId()))) {
            Data.editData(player, "rank", "admin");
        }

    }
}