package io.felux.pouches;

import io.felux.pouches.hook.WorldGuardHook;
import io.felux.pouches.manager.FileManager;
import io.felux.pouches.manager.PouchManager;
import io.felux.pouches.nms.Title;
import io.felux.pouches.registerable.CommandRegisterable;
import io.felux.pouches.registerable.ListenerRegisterable;
import io.felux.pouches.util.Lang;
import io.felux.pouches.util.LogUtil;
import io.felux.pouches.util.PouchMapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.stream.Collectors;

public class Pouches extends JavaPlugin {
    private static Pouches instance;
    private static Title title;
    private FileManager fileManager;
    private PouchManager pouchManager;

    private static void setupTitle() {
        try {
            String packageName = Title.class.getPackage().getName();
            String internalsName = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            System.out.println("Found " + (packageName + ".version." + internalsName + "." + internalsName));
            title = (Title) Class.forName(packageName + ".version." + internalsName + "." + internalsName).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Title could not find a valid implementation for this server version.");
        }
    }

    public static Title getTitle() {
        return title;
    }

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
        setupTitle();
//        TitleRegisterable.register();

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
