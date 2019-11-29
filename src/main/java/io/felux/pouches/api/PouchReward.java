package io.felux.pouches.api;

import java.util.List;

public class PouchReward {

    private String id;
    private int chance;
    private List<String> rewards;

    public PouchReward(String id, int chance, List<String> rewards) {
        this.id = id;
        this.chance = chance;
        this.rewards = rewards;
    }

    public String getId() {
        return id;
    }

    public int getChance() {
        return chance;
    }

    public List<String> getRewards() {
        return rewards;
    }
}
