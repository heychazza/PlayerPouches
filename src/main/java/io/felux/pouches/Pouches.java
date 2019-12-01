package io.felux.pouches;

import io.felux.pouches.hook.WorldGuardHook;
import io.felux.pouches.manager.FileManager;
import io.felux.pouches.manager.PouchManager;
import io.felux.pouches.registerable.CommandRegisterable;
import io.felux.pouches.registerable.ListenerRegisterable;
import io.felux.pouches.util.Common;
import io.felux.pouches.util.Lang;
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
                handleReload();
                ListenerRegisterable.register();
                WorldGuardHook.register();
                CommandRegisterable.register();
            }
        }.runTaskAsynchronously(this);
    }

    public void handleReload() {
        reloadConfig();
        fileManager = new FileManager(instance);
        pouchManager = new PouchManager(fileManager.getPouchConfigs().stream().map(PouchMapper::voucherMap).collect(Collectors.toList()));
        Lang.init(this);
    }

    public static Pouches getInstance() {
        return instance;
    }

}
