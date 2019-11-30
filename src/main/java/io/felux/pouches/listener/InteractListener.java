package io.felux.pouches.listener;

import de.tr7zw.itemnbtapi.NBTItem;
import io.felux.pouches.Pouches;
import io.felux.pouches.api.Pouch;
import io.felux.pouches.api.PouchRedeemEvent;
import io.felux.pouches.hook.WorldGuardHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        // TODO: Remove this once commands are added.
        e.getPlayer().getInventory().addItem(Pouches.getInstance().getPouchManager().getPouches().get(0).getItem());
    }

    @EventHandler
    public void onPouchInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        ItemStack itemStack = e.getItem();

        if (block != null && itemStack != null && itemStack.getType() != Material.AIR) {
            NBTItem nbt = new NBTItem(itemStack);

            if (nbt.hasKey("pouches-id") && Pouches.getInstance().getPouchManager().getVoucher(nbt.getString("pouches-id")) != null) {
                Pouch pouch = Pouches.getInstance().getPouchManager().getVoucher(nbt.getString("pouches-id"));

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

                PouchRedeemEvent pouchRedeemEvent = new PouchRedeemEvent(player, pouch, pouch.getAmount());
                Bukkit.getServer().getPluginManager().callEvent(pouchRedeemEvent);
            }
        }
    }

    @EventHandler
    public void onPouchRedeem(PouchRedeemEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        Pouch pouch = e.getPouch();
        Long amount = e.getAmount();

//        Pouches pouches = Pouches.getInstance();

        player.sendMessage(ChatColor.YELLOW + "You opened the " + ChatColor.GOLD + pouch.getId() + ChatColor.YELLOW + " pouch.");
        pouch.sendTitle(player, amount);
    }
}
