package net.swofty.lobby.util;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Util {
    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<User> getUsersInGroup(String groupName) {
        LuckPerms api = LuckPermsProvider.get();
        Group group = api.getGroupManager().getGroup(groupName);
        if (group == null) throw new IllegalArgumentException("Group " + groupName + " not found");
        UserManager userManager = api.getUserManager();
        List<User> users = new ArrayList<>();
        for (UUID uuid : userManager.searchAll(NodeMatcher.key(InheritanceNode.builder(group).build())).join().keySet()) {
            User user = userManager.isLoaded(uuid) ? userManager.getUser(uuid) : userManager.loadUser(uuid).join();
            if (user == null) throw new IllegalStateException("Could not load data of " + uuid);
            users.add(user);
        }
        return users;
    }

    /*
                List<User> usersInGroupVIPPlus = API.getUsersInGroup("vip+");
            List<User> usersInGroupMVPPlus = API.getUsersInGroup("mvp+");
            List<User> usersInGroupMVPPlusPlus = API.getUsersInGroup("mvp++");
            LuckPerms api = LuckPermsProvider.get();
            UserManager userManager = api.getUserManager();

            if (usersInGroupVIPPlus.contains(userManager.getUser(player.getUniqueId()))) {
     */
}
