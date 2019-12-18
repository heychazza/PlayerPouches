package io.felux.pouches;

import io.felux.pouches.hook.WorldGuardHook;
import io.felux.pouches.manager.FileManager;
import io.felux.pouches.manager.PouchManager;
import io.felux.pouches.maven.LibraryLoader;
import io.felux.pouches.maven.MavenLibrary;
import io.felux.pouches.maven.Repository;
import io.felux.pouches.registerable.CommandRegisterable;
import io.felux.pouches.registerable.ListenerRegisterable;
import io.felux.pouches.registerable.TitleRegisterable;
import io.felux.pouches.util.Lang;
import io.felux.pouches.util.LogUtil;
import io.felux.pouches.util.PouchMapper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

@MavenLibrary(groupId = "com.github.CodeMC.WorldGuardWrapper", artifactId = "worldguardwrapper", version = "master-5e50edd862-1", repo = @Repository(url = "https://jitpack.io"))
public class Pouches extends JavaPlugin {
    private static Pouches instance;
    private FileManager fileManager;
    private PouchManager pouchManager;

    public static Pouches getInstance() {
        return instance;
    }

    public PouchManager getPouchManager() {
        return pouchManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        long start = System.currentTimeMillis();
        saveDefaultConfig();
        LogUtil.getBanner();

        LogUtil.loading("libraries");
        LibraryLoader.loadAll(Pouches.class);
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

}
