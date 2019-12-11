package io.felux.pouches.registerable;

import io.felux.pouches.Pouches;
import io.felux.pouches.title.Title;
import io.felux.pouches.title.version.*;
import io.felux.pouches.util.Common;
import org.bukkit.Bukkit;

public class TitleRegisterable {
    private static Title title;

    public static Title getTitle() {
        return title;
    }

    public static void register() {
        String version = Common.getVersion();
        if (version == null) return;
        switch (version) {
            case "v1_8_R3":
                title = new Title_v1_8_R3();
                break;
            case "v1_9_R2":
                title = new Title_v1_9_R2();
                break;
            case "v1_10_R1":
                title = new Title_v1_10_R1();
                break;
            case "v1_11_R1":
                title = new Title_v1_11_R1();
                break;
            case "v1_12_R1":
                title = new Title_v1_12_R1();
                break;
            case "v1_13_R2":
                title = new Title_v1_13_R2();
                break;
            case "v1_14_R1":
                title = new Title_v1_14_R1();
                break;
            case "v1_15_R1":
                title = new Title_v1_15_R1();
                break;
            default:
                Pouches.getInstance().getLogger().warning("Invalid version (" + version + ") detected!");
                Bukkit.getServer().getPluginManager().disablePlugin(Pouches.getInstance());
                break;
        }
    }
}
