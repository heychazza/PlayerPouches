package io.felux.pouches.command;

import io.felux.pouches.Pouches;
import io.felux.pouches.api.Pouch;
import io.felux.pouches.command.util.Command;
import io.felux.pouches.util.Lang;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListCommand {
    @Command(aliases = {"list"}, about = "List loaded pouches.", permission = "pouches.list", usage = "list")
    public static void execute(final CommandSender sender, final Pouches plugin, final String[] args) {
        List<Pouch> pouchList = plugin.getPouchManager().getPouches();

        if (pouchList.isEmpty()) {
            Lang.ERROR_NO_POUCHES_EXIST.send(sender, Lang.PREFIX.asString());
            return;
        }

        Lang.LIST_COMMAND_HEADER.send(sender, Lang.PREFIX.asString(), pouchList.size());
        for (Pouch pouch : pouchList) Lang.LIST_COMMAND_FORMAT.send(sender, Lang.PREFIX.asString(), pouch.getId());
        Lang.LIST_COMMAND_FOOTER.send(sender, Lang.PREFIX.asString(), pouchList.size());
    }
}
