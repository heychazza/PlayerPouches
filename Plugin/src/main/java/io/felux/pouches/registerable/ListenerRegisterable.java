package io.felux.pouches.registerable;

import io.felux.pouches.Pouches;
import io.felux.pouches.listener.InteractListener;
import io.felux.pouches.listener.PlaceListener;
import org.bukkit.event.Listener;

public class ListenerRegisterable {
    private static final Listener[] LISTENERS = {
            new InteractListener(),
            new PlaceListener()
    };

    public static void register() {
        Pouches pouches = Pouches.getInstance();
        for (Listener listener : LISTENERS) {
            pouches.getServer().getPluginManager().registerEvents(listener, pouches);
        }
    }
}
