package io.felux.pouches.util;

import io.felux.pouches.Pouches;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LogUtil {
    private static final Pouches PLUGIN = (Pouches) JavaPlugin.getProvidingPlugin(Pouches.class);

    public static void getBanner() {
        sendTranslated("&b ");
        sendTranslated("&b    ___");
        sendTranslated("&b   / _ \\");
        sendTranslated("&b  / ___/ " + " &7" + PLUGIN.getDescription().getName() + " v" + PLUGIN.getDescription().getVersion() + " (" + Common.getVersion() + ")");
        sendTranslated("&b /_/     " + " &7Running on Bukkit - " + PLUGIN.getServer().getName());
        sendTranslated("&b ");
        sendTranslated("[" + PLUGIN.getDescription().getPrefix() + "] Created by Felux.io Development.");
        sendTranslated("&b ");
    }

    public static void sendTranslated(String message) {
        Bukkit.getConsoleSender().sendMessage(Common.translate(message));
    }

    public static void loading(String message) {
        Bukkit.getConsoleSender().sendMessage("[" + PLUGIN.getDescription().getPrefix() + "] Loading " + Common.translate(message) + "..");
    }

    private static void debug(String message) {
        if (PLUGIN.getConfig().getBoolean("debug", false))
            PLUGIN.getLogger().info("[D] " + message);
    }
}
