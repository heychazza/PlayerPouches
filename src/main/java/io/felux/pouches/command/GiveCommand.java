package io.felux.pouches.command;

import io.felux.pouches.Pouches;
import io.felux.pouches.api.Pouch;
import io.felux.pouches.command.util.Command;
import io.felux.pouches.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand {
    @Command(aliases = {"give"}, about = "Give a pouch.", permission = "pouches.give", usage = "give <player> <pouch> [amount]", requiredArgs = 2)
    public static void execute(final CommandSender sender, final Pouches plugin, final String[] args) {
        final int amount = args.length > 2 ? Integer.parseInt(args[2]) : 1;

        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            Lang.ERROR_INVALID_PLAYER.send(sender, Lang.PREFIX.asString());
            return;
        }

        final Pouch pouch = Pouches.getInstance().getPouchManager().getPouch(args[1]);
        if (pouch == null) {
            Lang.ERROR_INVALID_POUCH.send(sender, Lang.PREFIX.asString());
            return;
        }
        ItemStack pouchItem = pouch.getItem().clone();
        pouchItem.setAmount(amount);
        target.getInventory().addItem(pouchItem);

        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (player.getUniqueId() == target.getUniqueId()) {
                // Self
                Lang.GIVE_COMMAND_SELF.send(sender, Lang.PREFIX.asString(), pouch.getId(), amount);
            } else {
                // Another player
                Lang.GIVE_COMMAND_OTHER.send(sender, Lang.PREFIX.asString(), target.getName(), pouch.getId(), amount);
            }
            return;
        }

        // Console
        Lang.GIVE_COMMAND_OTHER.send(sender, Lang.PREFIX.asString(), target.getName(), pouch.getId(), amount);
    }
}
