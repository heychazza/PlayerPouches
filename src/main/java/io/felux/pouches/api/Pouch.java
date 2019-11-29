package io.felux.pouches.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Pouch {

    private String id;
    private ItemStack item;
    private boolean permission;
    private Set<PouchReward> pouchRewards;
    private List<String> blacklistedRegions;
    private List<String> blacklistedWorlds;

    private static Map<String, Pouch> vouchers;

    public Pouch(String id) {
        this.id = id;
    }

    public static Map<String, Pouch> getVouchers() {
        return vouchers;
    }

    public Pouch(String id, ItemStack item, boolean permission, Set<PouchReward> pouchRewards, List<String> blacklistedRegions, List<String> blacklistedWorlds) {
        this.id = id.toLowerCase();
        this.item = item;
        this.permission = permission;
        this.pouchRewards = pouchRewards;
        this.blacklistedRegions = blacklistedRegions;
        this.blacklistedWorlds = blacklistedWorlds;
    }

    public String getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean isPermission() {
        return permission;
    }

    public boolean hasPermission(Player player) {
        if (permission) return player.hasPermission("vouchers.use." + getId());
        return true;
    }

    public Set<PouchReward> getPouchRewards() {
        return pouchRewards;
    }

    public PouchReward getReward() {
        int random = ThreadLocalRandom.current().nextInt(1, 100);

        for (PouchReward pouchReward : pouchRewards) {
            if (pouchReward.getChance() <= random) {
                return pouchReward;
            }
        }

        return null;
    }

    public List<String> getBlacklistedRegions() {
        return blacklistedRegions;
    }

    public List<String> getBlacklistedWorlds() {
        return blacklistedWorlds;
    }
}
