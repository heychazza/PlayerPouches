package io.felux.pouches.manager;

import io.felux.pouches.Pouches;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final JavaPlugin POUCHES = JavaPlugin.getProvidingPlugin(Pouches.class);

    private List<FileConfiguration> pouchData;

    public List<FileConfiguration> getPouchConfigs() {
        return pouchData;
    }

    public FileManager(Pouches pouches) {
        pouchData = new ArrayList<>();
        File voucherFolder = new File(pouches.getDataFolder(), "pouch");

        File[] files;
        if (!voucherFolder.exists()) {
            if (voucherFolder.mkdirs())
                files = voucherFolder.listFiles();
        }

        files = voucherFolder.listFiles();

        if (files != null) {
            pouchData = map(files);
        }
    }

    public static List<FileConfiguration> map(File[] files) {
        List<FileConfiguration> fileData = new ArrayList<>();

        for (File file : files) {
            fileData.add(YamlConfiguration.loadConfiguration(file));
        }
        return fileData;
    }
}
