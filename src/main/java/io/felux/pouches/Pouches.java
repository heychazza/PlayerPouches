package io.felux.pouches;

import io.felux.pouches.hook.WorldGuardHook;
import io.felux.pouches.manager.FileManager;
import io.felux.pouches.manager.PouchManager;
import io.felux.pouches.registerable.CommandRegisterable;
import io.felux.pouches.registerable.ListenerRegisterable;
import io.felux.pouches.util.Common;
import io.felux.pouches.util.PouchMapper;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;

public class Pouches extends JavaPlugin {
    private FileManager fileManager;
    private PouchManager pouchManager;
    private static Pouches instance;

    public PouchManager getPouchManager() {
        return pouchManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Common.setupTitle();
        new BukkitRunnable() {
            @Override
            public void run() {
                fileManager = new FileManager(instance);
                pouchManager = new PouchManager(fileManager.getPouchConfigs().stream().map(PouchMapper::voucherMap).collect(Collectors.toList()));
                ListenerRegisterable.register();
                WorldGuardHook.register();
                CommandRegisterable.register();
            }
        }.runTaskAsynchronously(this);
    }

    public static Pouches getInstance() {
        return instance;
    }
}
