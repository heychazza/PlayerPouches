package io.felux.pouches.util;

import io.felux.pouches.Pouches;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LogUtil {
    private static final Pouches PLUGIN = (Pouches) JavaPlugin.getProvidingPlugin(Pouches.class);

    public static void getBanner() {
        sendTranslated("&b ");
        sendTranslated("&b    ____    __         ");
        sendTranslated("&b   / __/__ / /_ ____ __");
        sendTranslated("&b  / _// -_) / // /\\ \\ /");
        sendTranslated("&b /_/  \\__/_/\\_,_//_\\_\\ ");
        sendTranslated("&b ");
        sendTranslated("&b &7A Product of &bFelux.io Development&7.");
        sendTranslated("&b &7Running on " + Common.getVersion() + " as Bukkit - " + PLUGIN.getServer().getName());
        sendTranslated("&b ");
    }

    public static void sendTranslated(String message) {
        Bukkit.getConsoleSender().sendMessage(Common.translate(message));
    }

    public static void loading(String message) {
        Bukkit.getConsoleSender().sendMessage("[" + PLUGIN.getDescription().getPrefix() + "] Loading " + Common.translate(message) + "..");
    }

    public static void debug(String message) {
        if (PLUGIN.getConfig().getBoolean("debug", false))
            PLUGIN.getLogger().info("[D] " + message);
    }
}
