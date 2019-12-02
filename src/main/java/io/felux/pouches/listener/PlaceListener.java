package io.felux.pouches.listener;

import io.felux.pouches.api.Pouch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceListener implements Listener {

    @EventHandler
    public void onPouchInteract(BlockPlaceEvent e) {
        if (Pouch.getPouch(e.getItemInHand()) != null) e.setCancelled(true);
    }
}
