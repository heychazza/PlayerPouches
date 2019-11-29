package io.felux.pouches.listener;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import io.felux.pouches.Pouches;
import io.felux.pouches.api.Pouch;
import io.felux.pouches.api.PouchRedeemEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().getInventory().addItem(Pouches.getInstance().getPouchManager().getPouches().get(0).getItem());
    }

    @EventHandler
    public void onPouchInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItem();

        if (itemStack != null && itemStack.getType() != Material.AIR) {
            NBTContainer nbt = NBTItem.convertItemtoNBT(itemStack);

            if (nbt.hasKey("pouches-id") && Pouches.getInstance().getPouchManager().getVoucher(nbt.getString("pouches-id")) != null) {
                Pouch pouch = Pouches.getInstance().getPouchManager().getVoucher(nbt.getString("pouches-id"));

                PouchRedeemEvent pouchRedeemEvent = new PouchRedeemEvent(player, pouch, pouch.getAmount());
                Bukkit.getServer().getPluginManager().callEvent(pouchRedeemEvent);
            }
        }
    }

    @EventHandler
    public void onPouchRedeem(PouchRedeemEvent e) {
        Player player = e.getPlayer();
        Pouch pouch = e.getPouch();
        Long amount = e.getAmount();

        player.sendMessage(ChatColor.YELLOW + "You clicked the " + pouch.getId() + " pouch.");

//        System.out.println("Player: " + player.getName());
//        System.out.println("Pouch: " + pouch);
//        System.out.println("Amount: " + amount);
//        System.out.println(" ");

        for (String reward : pouch.getPouchRewards()) {
            player.sendMessage(ChatColor.YELLOW + "[REWARD] " + ChatColor.WHITE + reward);
        }

        Pouches pouches = Pouches.getInstance();
        pouch.sendTitle(player, amount);
    }
}
