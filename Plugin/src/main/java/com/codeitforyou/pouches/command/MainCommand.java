package com.codeitforyou.pouches.command;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.pouches.Pouches;
import com.codeitforyou.pouches.util.Lang;
import org.bukkit.command.CommandSender;

public class MainCommand {
    @Command(about = "Main command.")
    public static void execute(final CommandSender sender, final Pouches plugin, final String[] args) {
        Lang.MAIN_COMMAND.send(sender, Lang.PREFIX.asString(), plugin.getDescription().getName(), plugin.getDescription().getVersion(), plugin.getDescription().getAuthors().get(0));
    }
}
