package io.felux.pouches.manager;

import io.felux.pouches.Pouches;
import io.felux.pouches.listener.InteractListener;
import org.bukkit.event.Listener;

public class ListenerManager {
    private static final Listener[] LISTENERS = {
            new InteractListener()
    };

    public static void register() {
        Pouches pouches = Pouches.getInstance();
        for (Listener listener : LISTENERS) {
            pouches.getServer().getPluginManager().registerEvents(listener, pouches);
        }
    }
}
