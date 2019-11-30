package io.felux.pouches;

import io.felux.pouches.hook.WorldGuardHook;
import io.felux.pouches.manager.FileManager;
import io.felux.pouches.manager.ListenerManager;
import io.felux.pouches.manager.PouchManager;
import io.felux.pouches.title.*;
import io.felux.pouches.util.PouchMapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;

public class Pouches extends JavaPlugin {
    private FileManager fileManager;
    private PouchManager pouchManager;
    private static Pouches instance;

    public FileManager getFileManager() {
        return fileManager;
    }

    public PouchManager getPouchManager() {
        return pouchManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        setupTitle();
        new BukkitRunnable() {
            @Override
            public void run() {
                fileManager = new FileManager(instance);
                pouchManager = new PouchManager(fileManager.getPouchConfigs().stream().map(PouchMapper::voucherMap).collect(Collectors.toList()));
                ListenerManager.register();
                WorldGuardHook.register();
            }
        }.runTaskAsynchronously(this);
    }

    private Title title;

    public Title getTitle() {
        return title;
    }

    private void setupTitle() {
        String version = null;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            getLogger().warning("Invalid version detected!");
            getServer().getPluginManager().disablePlugin(this);
        }

        if (version == null) return;
        this.getLogger().info("Your server is running version " + version);
        switch (version) {
            case "v1_8_R3":
                this.title = new Title_v1_8_R3();
                break;
            case "v1_9_R2":
                this.title = new Title_v1_9_R2();
                break;
            case "v1_10_R1":
                this.title = new Title_v1_10_R1();
                break;
            case "v1_11_R1":
                this.title = new Title_v1_11_R1();
                break;
            case "v1_12_R1":
                this.title = new Title_v1_12_R1();
                break;
            default:
                getLogger().warning("Invalid version (" + version + ") detected!");
                getServer().getPluginManager().disablePlugin(this);
                break;
        }
    }

    public static Pouches getInstance() {
        return instance;
    }
}
