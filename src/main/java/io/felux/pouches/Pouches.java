package io.felux.pouches;

import io.felux.pouches.hook.WorldGuardHook;
import io.felux.pouches.manager.FileManager;
import io.felux.pouches.manager.PouchManager;
import io.felux.pouches.registerable.CommandRegisterable;
import io.felux.pouches.registerable.ListenerRegisterable;
import io.felux.pouches.registerable.TitleRegisterable;
import io.felux.pouches.util.Lang;
import io.felux.pouches.util.LogUtil;
import io.felux.pouches.util.PouchMapper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

import static io.felux.pouches.util.LogUtil.getBanner;

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
        long start = System.currentTimeMillis();
        saveDefaultConfig();
        getBanner();

        LogUtil.loading("libraries");
        TitleRegisterable.register();

        LogUtil.loading("pouches");
        loadPouches();

        LogUtil.loading("commands");
        CommandRegisterable.register();

        LogUtil.loading("listeners");
        ListenerRegisterable.register();

        LogUtil.loading("hooks");
        WorldGuardHook.register();

        LogUtil.sendTranslated(" ");
        getLogger().info("Successfully enabled in " + (System.currentTimeMillis() - start) + "ms.");
    }

    public void loadPouches() {
        fileManager = new FileManager(instance);
        pouchManager = new PouchManager(fileManager.getPouchConfigs().stream().map(PouchMapper::voucherMap).collect(Collectors.toList()));
        Lang.init(this);
    }

    public static Pouches getInstance() {
        return instance;
    }

}
