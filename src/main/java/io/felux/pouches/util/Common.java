package io.felux.pouches.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static String translate(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(final List<String> message) {
        List<String> translatedList = new ArrayList<>();
        message.forEach(msg -> translatedList.add(translate(msg)));
        return translatedList;
    }

    private static String parsePlaceholders(OfflinePlayer p, String text) {
        return PlaceholderAPI.setPlaceholders(p, text);
    }

    public static String parse(OfflinePlayer p, String text) {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? parsePlaceholders(p, text) : text;
    }
}
