package net.swofty.lobby.util;

import net.swofty.lobby.Data;
import net.swofty.lobby.Loader;
import net.swofty.lobby.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Runnable {

    public static void asyncSecond() {

        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {

                        org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Loader.getInstance().getServer().getScoreboardManager();
                        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
                        Objective objective = scoreboard.registerNewObjective("Test", "Dummy");

                        objective.getScore(Util.colorize(" ")).setScore(9);
                        objective.getScore(Util.colorize("Rank: " + new PlayerManager(player).getRankPrefix().replace("[", "").replace("]", ""))).setScore(8);
                        objective.getScore(Util.colorize("Mystery Dust: &a" + Data.getData(player, "mystery-dust"))).setScore(7);
                        objective.getScore(Util.colorize("Achievements: &a" + Data.getData(player, "achievements"))).setScore(6);
                        objective.getScore(Util.colorize("Level: &a" + Data.getData(player, "level"))).setScore(5);
                        objective.getScore(Util.colorize("   ")).setScore(4);
                        objective.getScore(Util.colorize("Lobby: &a#1")).setScore(3);
                        objective.getScore(Util.colorize("Players: &a" + Bukkit.getOnlinePlayers().size())).setScore(2);
                        objective.getScore(Util.colorize("  ")).setScore(1);
                        objective.getScore(Util.colorize("&ewww.hypixel.net")).setScore(1);

                        Team zero = scoreboard.registerNewTeam("zero");

                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        objective.setDisplayName(Util.colorize("&e&lHYPIXEL"));
                        player.setScoreboard(scoreboard);
                }

            }
        }.runTaskTimer(Loader.getInstance(), 0, 20);

        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (player.getLocation().getX() >= 41 && player.getLocation().getX() <= 42 && player.getLocation().getZ() >= 55 && player.getLocation().getZ() <= 56) {

                        if (Data.getData(player, "parkour").equals("none")) {
                            Items.giveParkourItems(player, Boolean.valueOf(Data.getData(player, "hidden-players")));
                            player.sendMessage("§a§lParkour challenge started!");
                            player.sendMessage("§aUse §e/parkour checkpoint §ato teleport to the last checkpoint or §e/parkour cancel §ato cancel!");
                            Data.editData(player, "parkour", String.valueOf(System.currentTimeMillis()));
                            Data.editData(player, "checkpoint", "1");
                        } else {

                            boolean time2 = System.currentTimeMillis() % 2 == 0;
                            if (time2) {
                                player.getPlayer().sendMessage("§a§lReset your timer to 00:00! Get to the finish line!");
                                Data.editData(player.getPlayer(), "parkour", String.valueOf(System.currentTimeMillis()));
                                Data.editData(player, "checkpoint", "1");
                            }
                        }
                    }

                    if (player.getLocation().getY() > 88 && player.getLocation().getX() >= 74 && player.getLocation().getX() <= 75 && player.getLocation().getZ() >= 113 && player.getLocation().getZ() <= 114) {

                        if (!Data.getData(player, "parkour").equals("none") && Data.getData(player, "checkpoint").equals("1")) {
                            long millis = System.currentTimeMillis() - Long.parseLong(Data.getData(player, "parkour"));
                            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
                            String formatted = sdf.format(new Date(millis));

                            player.sendMessage("§a§lYou reached §e§lCheckpoint #1 §a§lafter §e§l" + formatted);
                            Data.editData(player, "checkpoint", "2");
                        }
                    }

                    if (player.getLocation().getX() >= -8 && player.getLocation().getX() <= -7 && player.getLocation().getZ() >= 118 && player.getLocation().getZ() <= 119) {

                        if (!Data.getData(player, "parkour").equals("none") && Data.getData(player, "checkpoint").equals("2")) {
                            long millis = System.currentTimeMillis() - Long.parseLong(Data.getData(player, "parkour"));
                            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
                            String formatted = sdf.format(new Date(millis));

                            player.sendMessage("§a§lYou reached §e§lCheckpoint #2 §a§lafter §e§l" + formatted);
                            Data.editData(player, "checkpoint", "3");
                        }
                    }

                    if (player.getLocation().getX() >= -19 && player.getLocation().getX() <= -18 && player.getLocation().getZ() >= 118 && player.getLocation().getZ() <= 119) {

                        if (!Data.getData(player, "parkour").equals("none") && Data.getData(player, "checkpoint").equals("3")) {
                            long millis = System.currentTimeMillis() - Long.parseLong(Data.getData(player, "parkour"));
                            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
                            String formatted = sdf.format(new Date(millis));

                            if (Data.getData(player, "parkour-best").equals("0")) {
                                player.sendMessage("§a§lYour got a time of §e§l" + formatted + "§a§l! Try again to get an even faster time");
                                Data.editData(player, "parkour-best", String.valueOf(millis));
                                return;
                            }

                            if (millis < Long.parseLong(Data.getData(player, "parkour-best"))) {
                                long millis2 = Long.parseLong(Data.getData(player, "parkour-best"));
                                SimpleDateFormat sdf2 = new SimpleDateFormat("mm:ss.SSS");
                                String formatted2 = sdf2.format(new Date(millis2));
                                player.sendMessage("§a§lYour time of §e§l" + formatted + " §a§lbeat your previous record of §e§l" + formatted2);
                                Data.editData(player, "checkpoint", "1");
                                Data.editData(player, "parkour", "none");
                                Data.editData(player, "parkour-best", String.valueOf(millis));
                                Items.giveSpawnItems(player, Boolean.valueOf(Data.getData(player, "hidden-items")));
                            } else {
                                long millis2 = Long.parseLong(Data.getData(player, "parkour-best"));
                                SimpleDateFormat sdf2 = new SimpleDateFormat("mm:ss.SSS");
                                String formatted2 = sdf2.format(new Date(millis2));
                                player.sendMessage("§a§lYour time of §e§l" + formatted + " §a§ldid not beat your previous record of §e§l" + formatted2 + "§a§l! Try again to beat your old record!");
                                Data.editData(player, "checkpoint", "1");
                                Data.editData(player, "parkour", "none");
                                Items.giveSpawnItems(player, Boolean.valueOf(Data.getData(player, "hidden-items")));
                            }
                        } else {
                            boolean time2 = System.currentTimeMillis() % 2 == 0;
                            if (time2) {
                                player.sendMessage("§a§lThis is the finish line for the parkour! Get to the start line and climb back up here!");
                            }
                        }
                    }

                }
            }
        }.runTaskTimerAsynchronously(Loader.getInstance(), 0, 4);
    }
}
