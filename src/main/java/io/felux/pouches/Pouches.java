package io.felux.pouches;

import io.felux.pouches.manager.EventManager;
import io.felux.pouches.manager.FileManager;
import io.felux.pouches.manager.PouchManager;
import io.felux.pouches.util.PouchMapper;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;

public class Pouches extends JavaPlugin {
    private FileManager fileManager;
    private PouchManager pouchManager;

    public FileManager getFileManager() {
        return fileManager;
    }

    public PouchManager getPouchManager() {
        return pouchManager;
    }

    private static Pouches instance;

    @Override
    public void onEnable() {
        instance = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                fileManager = new FileManager(instance);
                pouchManager = new PouchManager(fileManager.getPouchConfigs().stream().map(PouchMapper::voucherMap).collect(Collectors.toList()));
                EventManager.register();
            }
        }.runTaskAsynchronously(this);
    }

    public static Pouches getInstance() {
        return instance;
    }
}
