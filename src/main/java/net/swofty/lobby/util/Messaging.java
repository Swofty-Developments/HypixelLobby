package net.swofty.lobby.util;

public class Messaging {
    public static boolean isDebugging() {
        return true;
    }

    public static void debug(Object... message) {
        StringBuilder string = new StringBuilder();

        for (Object item : message) {
            string.append(item);
        }

        DLog.info("[DEBUG] " + string.toString());
    }
}