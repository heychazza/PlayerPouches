package io.felux.pouches.listener;

import io.felux.pouches.Pouches;
import io.felux.pouches.api.Pouch;
import io.felux.pouches.api.PouchReward;
import io.felux.pouches.nbt.NBT;
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
    public void onVoucherUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItem();

        if (itemStack != null && itemStack.getType() != Material.AIR) {
            NBT nbt = NBT.get(itemStack);

            if (nbt.hasKey("voucherid") && Pouches.getInstance().getPouchManager().getVoucher(nbt.getString("voucherid")) != null) {
                Pouch pouch = Pouches.getInstance().getPouchManager().getVoucher(nbt.getString("voucherid"));
                player.sendMessage("You clicked the " + pouch.getId() + " voucher.");

                for (PouchReward reward : pouch.getPouchRewards()) {
                    for (String rewardReward : reward.getRewards()) {
                        player.sendMessage(reward.getId() + ": " + rewardReward);
                    }
                }
            }

        }
    }
}
