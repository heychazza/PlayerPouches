package io.felux.pouches.util;

import io.felux.pouches.Pouches;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

public enum Lang {
    PREFIX("&8[&dPouches&8]"),

    MAIN_COMMAND("{0} &7Running &f{1} &7version &6{2} &7by &d{3}&7."),

    HELP_COMMAND_HEADER("", "{0} &7Listing Commands:", "&7"),
    HELP_COMMAND_FORMAT(" &d/pouches {1} &8- &7{2}"),
    HELP_COMMAND_FOOTER("", "{0} &7Total of &f{1} &7commands."),

    RELOAD_COMMAND("{0} &7Successfully reloaded the configuration file."),

    GIVE_COMMAND_SELF("{0} &7You've given yourself a &d{1} &7pouch (&f{2}x&7)."),
    GIVE_COMMAND_OTHER("{0} &7You've given &d{1} &7a &5{2} &7pouch (&f{3}x&7)."),

    PROGRESS_START("&8["),
    PROGRESS_CHARACTER(":"),
    PROGRESS_END("&8]"),
    PROGRESS_INCOMPLETE("&7"),
    PROGRESS_COMPLETE("&a"),

    COMMAND_NO_PERMISSION("{0} &cYou don't have permissions to do that."),
    COMMAND_PLAYER_ONLY("{0} &cThe command or args specified can only be used by a player."),
    COMMAND_INVALID("{0} &cThat command doesn't exist, use &f/pouch help&c."),
    COMMAND_UNKNOWN("{0} &cThat player couldn't be found."),
    COMMAND_USAGE("{0} &7Usage: &d/pouch {1}");

    private static FileConfiguration c;
    private String message;

    Lang(final String... def) {
        this.message = String.join("\n", def);
    }

    public static String format(String s, final Object... objects) {
        for (int i = 0; i < objects.length; ++i) {
            s = s.replace("{" + i + "}", String.valueOf(objects[i]));
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean init(Pouches pouches) {
        Lang.c = pouches.getConfig();
        for (final Lang value : values()) {
            if (value.getMessage().split("\n").length == 1) {
                Lang.c.addDefault(value.getPath().toLowerCase(), value.getMessage());
            } else {
                Lang.c.addDefault(value.getPath().toLowerCase(), value.getMessage().split("\n"));
            }
        }
        Lang.c.options().copyDefaults(true);
        pouches.saveConfig();
        return true;
    }

    private String getMessage() {
        return this.message;
    }

    public String getPath() {
        return "message." + this.name().toLowerCase().toLowerCase();
    }

    public void send(final Player player, final Object... args) {
        final String message = this.asString(args);
        Arrays.stream(message.split("\n")).forEach(player::sendMessage);
    }

    public void send(final CommandSender sender, final Object... args) {
        if (sender instanceof Player) {
            this.send((Player) sender, args);
        } else {
            Arrays.stream(this.asString(args).split("\n")).forEach(sender::sendMessage);
        }
    }

    public String asString(final Object... objects) {
        Optional<String> opt = Optional.empty();
        if (Lang.c.contains(this.getPath())) {
            if (Lang.c.isList(getPath())) {
                opt = Optional.of(String.join("\n", Lang.c.getStringList(this.getPath())));
            } else if (Lang.c.isString(this.getPath())) {
                opt = Optional.ofNullable(Lang.c.getString(this.getPath()));
            }
        }
        return this.format(opt.orElse(this.message), objects);
    }
}

