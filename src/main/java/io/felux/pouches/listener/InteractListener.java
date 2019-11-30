package io.felux.pouches.listener;

import de.tr7zw.itemnbtapi.NBTItem;
import io.felux.pouches.Pouches;
import io.felux.pouches.api.Pouch;
import io.felux.pouches.api.PouchRedeemEvent;
import io.felux.pouches.hook.WorldGuardHook;
import io.felux.pouches.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void onPouchInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItem();

        if (itemStack != null && itemStack.getType() != Material.AIR) {
            NBTItem nbt = new NBTItem(itemStack);

            if (nbt.hasKey("pouches-id") && Pouches.getInstance().getPouchManager().getPouch(nbt.getString("pouches-id")) != null) {
                Pouch pouch = Pouches.getInstance().getPouchManager().getPouch(nbt.getString("pouches-id"));

                if (pouch.getBlacklistedWorlds().contains(player.getWorld().getName())) {
                    player.sendMessage(ChatColor.RED + "You cannot use this pouch in this world!");
                    return;
                }

                if (WorldGuardHook.isEnabled()) {
                    for (final String region : pouch.getBlacklistedRegions()) {
                        if (WorldGuardHook.checkIfPlayerInRegion(player, region)) {
                            player.sendMessage(ChatColor.RED + "You cannot use this pouch in this region!");
                            return;
                        }
                    }
                }

                if (Pouch.getCurrentPouches().contains(player.getUniqueId())) {
                    Lang.ERROR_ALREADY_OPENING.send(player, Lang.PREFIX.asString());
                    return;
                }

                PouchRedeemEvent pouchRedeemEvent = new PouchRedeemEvent(player, pouch, pouch.getAmount());
                Bukkit.getServer().getPluginManager().callEvent(pouchRedeemEvent);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPouchRedeem(PouchRedeemEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        Pouch pouch = e.getPouch();
        Long amount = e.getAmount();

        ItemStack pouchItem = player.getItemInHand();

        if (pouchItem.getAmount() == 1) player.setItemInHand(null);
        else player.getItemInHand().setAmount(pouchItem.getAmount() - 1);

        Pouch.getCurrentPouches().add(player.getUniqueId());
        player.sendMessage(ChatColor.YELLOW + "You opened the " + ChatColor.GOLD + pouch.getId() + ChatColor.YELLOW + " pouch.");
        pouch.sendTitle(player, amount);
    }
}
