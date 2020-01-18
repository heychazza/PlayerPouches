package com.codeitforyou.pouches.command;

import com.codeitforyou.pouches.Pouches;
import com.codeitforyou.pouches.command.util.Command;
import com.codeitforyou.pouches.util.Lang;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    @Command(aliases = {"reload"}, about = "Reload the configuration files.", permission = "pouches.reload", usage = "reload")
    public static void execute(final CommandSender sender, final Pouches plugin, final String[] args) {
        plugin.reloadConfig();
        plugin.loadPouches();
        Lang.RELOAD_COMMAND.send(sender, Lang.PREFIX.asString(), plugin.getPouchManager().getPouches().size());
    }
}
