package com.codeitforyou.pouches.command;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.pouches.Pouches;
import com.codeitforyou.pouches.api.Pouch;
import com.codeitforyou.pouches.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAllCommand {
    @Command(aliases = {"giveall"}, about = "Give online players a pouch.", permission = "pouches.giveall", usage = "giveall <pouch> [amount]", requiredArgs = 1)
    public static void execute(final CommandSender sender, final Pouches plugin, final String[] args) {
        final int amount = args.length > 1 ? Integer.parseInt(args[1]) : 1;

        if (plugin.getPouchManager().getPouches().isEmpty()) {
            Lang.ERROR_NO_POUCHES_EXIST.send(sender, Lang.PREFIX.asString());
            return;
        }

        final Pouch pouch = Pouches.getInstance().getPouchManager().getPouch(args[0]);
        if (pouch == null) {
            Lang.ERROR_INVALID_POUCH.send(sender, Lang.PREFIX.asString());
            return;
        }
        ItemStack pouchItem = pouch.getItem().clone();
        pouchItem.setAmount(amount);

        int onlineCount = 0;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.getInventory().addItem(pouchItem);
            onlineCount++;
        }

        Lang.GIVE_ALL_COMMAND.send(sender, Lang.PREFIX.asString(), onlineCount, pouch.getId(), amount);
    }
}
