package com.codeitforyou.pouches.listener;

import com.codeitforyou.pouches.api.Pouch;
import com.codeitforyou.pouches.api.PouchRedeemEvent;
import com.codeitforyou.pouches.api.PouchSlot;
import com.codeitforyou.pouches.hook.WorldGuardHook;
import com.codeitforyou.pouches.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void onPouchInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItem();

        if (itemStack == null || itemStack.getType() == Material.AIR) return;

        Pouch pouch = Pouch.getPouch(itemStack);
        if (pouch == null) return;

        e.setCancelled(true);

        PouchSlot pouchSlot = PouchSlot.MAIN_HAND;

        if (!Bukkit.getVersion().contains("1.8")) {
            if (e.getHand() == EquipmentSlot.OFF_HAND)
                pouchSlot = PouchSlot.OFF_HAND;
        }

        if (!pouch.hasPermission(player)) {
            return;
        }

        if (pouch.getBlacklistedWorlds().contains(player.getWorld().getName())) {
            Lang.CANNOT_USE_IN_WORLD.send(player, Lang.PREFIX.asString());
            return;
        }

        if (WorldGuardHook.isEnabled()) {
            for (final String region : pouch.getBlacklistedRegions()) {
                if (WorldGuardHook.checkIfPlayerInRegion(player, region)) {
                    Lang.CANNOT_USE_IN_REGION.send(player, Lang.PREFIX.asString());
                    return;
                }
            }
        }

        if (Pouch.getCurrentPouches().contains(player.getUniqueId())) {
            Lang.ERROR_ALREADY_OPENING.send(player, Lang.PREFIX.asString());
            return;
        }

        PouchRedeemEvent pouchRedeemEvent = new PouchRedeemEvent(player, pouch, pouch.getAmount(), itemStack, pouchSlot);
        Bukkit.getServer().getPluginManager().callEvent(pouchRedeemEvent);
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
        ItemStack pouchItem = e.getItem();

        if (e.getSlot() == PouchSlot.MAIN_HAND) {
            if (pouchItem.getAmount() == 1) player.setItemInHand(null);
            else player.getItemInHand().setAmount(pouchItem.getAmount() - 1);
        } else {
            if (pouchItem.getAmount() == 1) player.getInventory().setItemInOffHand(null);
            else player.getInventory().getItemInOffHand().setAmount(pouchItem.getAmount() - 1);
        }

        Pouch.getCurrentPouches().add(player.getUniqueId());
        pouch.sendTitle(player, amount);
    }
}
