package io.felux.pouches.util;

import io.felux.pouches.Pouches;
import io.felux.pouches.title.Title;
import io.felux.pouches.title.version.*;
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


    private static Title title;

    public static Title getTitle() {
        return title;
    }

    public static void setupTitle() {
        String version = null;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            Bukkit.getServer().getLogger().warning("Invalid version detected!");
            Bukkit.getServer().getPluginManager().disablePlugin(PLUGIN);
        }

        if (version == null) return;
        Bukkit.getServer().getLogger().info("Your server is running version " + version);
        switch (version) {
            case "v1_8_R3":
                Common.title = new Title_v1_8_R3();
                break;
            case "v1_9_R2":
                Common.title = new Title_v1_9_R2();
                break;
            case "v1_10_R1":
                Common.title = new Title_v1_10_R1();
                break;
            case "v1_11_R1":
                Common.title = new Title_v1_11_R1();
                break;
            case "v1_12_R1":
                Common.title = new Title_v1_12_R1();
                break;
            case "v1_13_R2":
                Common.title = new Title_v1_13_R2();
                break;
            case "v1_14_R1":
                Common.title = new Title_v1_14_R1();
                break;
            default:
                Bukkit.getLogger().warning("Invalid version (" + version + ") detected!");
                Bukkit.getServer().getPluginManager().disablePlugin(PLUGIN);
                break;
        }
    }
}
