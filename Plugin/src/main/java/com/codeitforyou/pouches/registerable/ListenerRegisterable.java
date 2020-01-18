package com.codeitforyou.pouches.registerable;

import com.codeitforyou.pouches.Pouches;
import com.codeitforyou.pouches.listener.InteractListener;
import com.codeitforyou.pouches.listener.PlaceListener;
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
