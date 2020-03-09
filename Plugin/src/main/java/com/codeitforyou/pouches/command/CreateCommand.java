package com.codeitforyou.pouches.command;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.pouches.Pouches;
import com.codeitforyou.pouches.api.Pouch;
import com.codeitforyou.pouches.util.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class CreateCommand {
    @Command(aliases = {"create"}, about = "Create a pouch by the id <id>.", permission = "pouches.create", usage = "create <id>", requiredArgs = 1)
    public static void execute(final CommandSender sender, final Pouches plugin, final String[] args) {
        final String pouchId = args[0];

        final Pouch targetPouch = plugin.getPouchManager().getPouch(pouchId);
        if (targetPouch != null) {
            // Already exists?!
            Lang.ERROR_ALREADY_EXISTS.send(sender, Lang.PREFIX.asString());
            return;
        }

        File pouchFile = new File(plugin.getDataFolder() + "/pouch/" + pouchId.toLowerCase() + ".yml");
        if (!pouchFile.exists()) {
            try {
                if (pouchFile.getParentFile().mkdir()) {

                    if (!pouchFile.createNewFile()) {
                        throw new RuntimeException("Failed to create pouch file at " + pouchFile.getPath() + "!");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration pouchConfig = YamlConfiguration.loadConfiguration(pouchFile);
        pouchConfig.options().header(plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " Configuration" + "\n" + "Configure your '" + pouchId.toLowerCase() + "' pouch here.");
        pouchConfig.set("id", pouchId);
        pouchConfig.set("item.name", "&b" + pouchId + " Pouch");
        pouchConfig.set("item.lore", Arrays.asList("&7Go into the plugin folder", "&7to edit this pouch.", "&7", "&7plugins/Pouches/pouch/" + pouchId.toLowerCase() + ".yml"));
        pouchConfig.set("item.glow", true);
        pouchConfig.set("item.material", "PAPER");
        pouchConfig.set("item.data", 0);
        pouchConfig.set("rewards", Arrays.asList("[broadcast] &b%player% &7won &f$%amount% &7from a pouch!", "[console] eco give %player% %amount%"));
        pouchConfig.set("settings.permission", true);
        pouchConfig.set("settings.format", true);
        pouchConfig.set("settings.amount.min", 1000);
        pouchConfig.set("settings.amount.max", 2000);
        pouchConfig.set("settings.title.unrevealed.first", "&c&l&n&k");
        pouchConfig.set("settings.title.unrevealed.second", "&a&l&n");
        pouchConfig.set("settings.title.unrevealed.subtitle", "&7&o(( Opening pouch... ))");
        pouchConfig.set("settings.title.revealed.first", "&c&l");
        pouchConfig.set("settings.title.revealed.second", "&a&l");
        pouchConfig.set("settings.title.revealed.title", "$%amount%");
        pouchConfig.set("settings.title.revealed.subtitle", "&7&o(( Congratulations! ))");
        pouchConfig.set("settings.blacklist.region", Collections.singletonList("bad_region"));
        pouchConfig.set("settings.blacklist.world", Collections.singletonList("bad_world"));

        try {
            pouchConfig.save(pouchFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Lang.CREATE_COMMAND.send(sender, Lang.PREFIX.asString(), pouchId.toLowerCase());
        plugin.loadPouches();

    }
}
