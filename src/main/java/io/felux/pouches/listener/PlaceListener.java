package io.felux.pouches.listener;

import io.felux.pouches.util.Common;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlaceListener implements Listener {

    @EventHandler
    public void onPouchInteract(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        if (Common.getPouch(item) != null) e.setCancelled(true);
    }
}
