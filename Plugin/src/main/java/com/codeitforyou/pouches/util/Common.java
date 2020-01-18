package com.codeitforyou.pouches.util;

import com.codeitforyou.pouches.Pouches;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Common {
    private static final Pouches PLUGIN = (Pouches) JavaPlugin.getProvidingPlugin(Pouches.class);

    public static String translate(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(final List<String> message) {
        List<String> translatedList = new ArrayList<>();
        message.forEach(msg -> translatedList.add(translate(msg)));
        return translatedList;
    }

    private static String parsePlaceholders(Player p, String text) {
        return PlaceholderAPI.setPlaceholders(p, text);
    }

    public static String parse(Player p, String text) {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? parsePlaceholders(p, text) : text;
    }

    public static String getVersion() {
        String version = null;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            PLUGIN.getLogger().warning("Invalid version detected!");
            Bukkit.getServer().getPluginManager().disablePlugin(PLUGIN);
        }
        return version;
    }
}
