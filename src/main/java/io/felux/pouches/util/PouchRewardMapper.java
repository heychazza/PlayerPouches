package io.felux.pouches.util;

import io.felux.pouches.api.PouchReward;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

public class PouchRewardMapper {
    public static Set<PouchReward> rewardMap(FileConfiguration data) {
        Set<PouchReward> pouchRewards = new HashSet<>();
        for (String rewards : data.getConfigurationSection("rewards").getKeys(false)) {
            System.out.println("rewards = " + rewards);
            pouchRewards.add(new PouchReward(rewards, data.getInt("rewards." + rewards + ".chance"), data.getStringList("rewards." + rewards + ".actions")));
        }
        return pouchRewards;
    }
}
