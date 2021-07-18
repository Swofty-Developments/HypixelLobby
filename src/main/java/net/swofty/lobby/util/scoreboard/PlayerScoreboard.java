package net.swofty.lobby.util.scoreboard;

import net.swofty.lobby.Loader;
import net.swofty.lobby.util.DLog;
import net.swofty.lobby.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
public class PlayerScoreboard {
    private static Scoreboard board;
    private static Objective objective;

    private final String title;
    private final Player target;
    private final DisplaySlot displaySlot;
    private final String[] lines;

    public static int refreshRate = 20, startDelay = 60;

    public PlayerScoreboard(String title, Player target, DisplaySlot displaySlot, String... lines) {
        this.title = title;
        this.target = target;
        this.displaySlot = displaySlot;
        this.lines = lines;
    }

    public void sendScoreboard(boolean refreshing) {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective("board", "dummy");

        objective.setDisplayName(Util.colorize(this.title));
        objective.setDisplaySlot(this.displaySlot);

        if (refreshing) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < lines.length; i++) {
                        objective.getScore(Util.colorize(lines[i].replace("%%space%%", buildSpace()))).setScore(i);
                    }

                    target.setScoreboard(board);
                }
            }.runTaskTimer(Loader.getInstance(), startDelay, refreshRate);
        } else {
            for (int i = 0; i < lines.length; i++) {
                objective.getScore(Util.colorize(lines[i].replace("%%space%%", buildSpace()))).setScore(i);
            }

            target.setScoreboard(board);
        }
    }

    private String buildSpace() {
        int spaces = 1;
        String built = "&c";
        for (String line : lines) {
            if (line.contains("%%space%%")) {
                spaces++;
            }
        }
        for (int i = 0; i < spaces; ++i) {
            built += "&c";
        }
        return built;
    }
}