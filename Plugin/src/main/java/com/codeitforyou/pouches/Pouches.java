package com.codeitforyou.pouches;

import com.codeitforyou.lib.api.actions.ActionManager;
import com.codeitforyou.pouches.hook.WorldGuardHook;
import com.codeitforyou.pouches.manager.FileManager;
import com.codeitforyou.pouches.manager.PouchManager;
import com.codeitforyou.pouches.maven.LibraryLoader;
import com.codeitforyou.pouches.maven.MavenLibrary;
import com.codeitforyou.pouches.maven.Repository;
import com.codeitforyou.pouches.registerable.CommandRegisterable;
import com.codeitforyou.pouches.registerable.ListenerRegisterable;
import com.codeitforyou.pouches.registerable.TitleRegisterable;
import com.codeitforyou.pouches.util.Lang;
import com.codeitforyou.pouches.util.PouchMapper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

@MavenLibrary(groupId = "com.github.CodeMC.WorldGuardWrapper", artifactId = "worldguardwrapper", version = "master-5e50edd862-1", repo = @Repository(url = "https://jitpack.io"))
public class Pouches extends JavaPlugin {
    private static Pouches instance;
    private FileManager fileManager;
    private PouchManager pouchManager;
    private ActionManager actionManager;

    public static Pouches getInstance() {
        return instance;
    }

    public PouchManager getPouchManager() {
        return pouchManager;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        actionManager = new ActionManager(this);
        actionManager.addDefaults();

        saveDefaultConfig();

        LibraryLoader.loadAll(Pouches.class);
        TitleRegisterable.register();

        loadPouches();

        CommandRegisterable.register();
        ListenerRegisterable.register();
        WorldGuardHook.register();
    }

    public void loadPouches() {
        fileManager = new FileManager(instance);
        pouchManager = new PouchManager(fileManager.getPouchConfigs().stream().map(PouchMapper::voucherMap).collect(Collectors.toList()));
        Lang.init(this);
    }

}
