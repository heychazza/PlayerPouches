package io.felux.pouches.command;

import io.felux.pouches.Pouches;
import io.felux.pouches.command.util.Command;
import io.felux.pouches.util.Lang;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    @Command(aliases = {"reload"}, about = "Reload the plugin.", permission = "pouches.reload", usage = "reload")
    public static void execute(final CommandSender sender, final Pouches plugin, final String[] args) {
//        plugin.handleReload();
        Lang.RELOAD_COMMAND.send(sender, Lang.PREFIX.asString());
    }
}
