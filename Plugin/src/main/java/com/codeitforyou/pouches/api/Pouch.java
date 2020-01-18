package com.codeitforyou.pouches.api;

import com.codeitforyou.pouches.Pouches;
import com.codeitforyou.pouches.nbt.NBT;
import com.codeitforyou.pouches.registerable.TitleRegisterable;
import com.codeitforyou.pouches.util.Common;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Pouch {
    private static final Map<String, Pouch> pouches = Maps.newConcurrentMap();
    private static List<UUID> currentPouches = new ArrayList<>();
    private final Pouches POUCHES = Pouches.getInstance();
    private String id;
    private ItemStack item;
    private boolean permission;
    private long minAmount;
    private long maxAmount;
    private List<String> rewards;
    private List<String> blacklistedRegions;
    private List<String> blacklistedWorlds;
    private String unrevealedFirstFormat = "&c&l&n&k";
    private String unrevealedSecondFormat = "&a&l&n";
    private String unrevealedSubtitle = "&7&o(( Opening pouch... ))";
    private String revealedFirstFormat = "&c&l";
    private String revealedSecondFormat = "&a&l";
    private String revealedTitle = "%color%%amount%";
    private String revealedSubtitle = "&7&o(( Congratulations! ))";
    private boolean format = true;

    public Pouch(String id) {
        this.id = id;
    }

    public Pouch(String id, ItemStack item) {
        this.id = id;
        this.item = item;
    }

    public Pouch(String id, ItemStack item, boolean permission, Long minAmount, Long maxAmount, List<String> rewards, List<String> blacklistedRegions, List<String> blacklistedWorlds) {
        this.id = id;
        this.item = item;
        this.permission = permission;
        this.rewards = rewards;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.blacklistedRegions = blacklistedRegions;
        this.blacklistedWorlds = blacklistedWorlds;
    }

    public static Pouch getPouch(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return null;
        NBT nbt = NBT.get(itemStack);
        if (nbt != null && nbt.hasNBTData() && nbt.hasKey("pouches-id") && Pouches.getInstance().getPouchManager().getPouch(nbt.getString("pouches-id")) != null)
            return Pouches.getInstance().getPouchManager().getPouch(nbt.getString("pouches-id"));

        return null;
    }

    public static List<UUID> getCurrentPouches() {
        return currentPouches;
    }

    public static Map<String, Pouch> getPouches() {
        return pouches;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public void setMinAmount(long minAmount) {
        this.minAmount = minAmount;
    }

    public void setMaxAmount(long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setFormat(boolean format) {
        this.format = format;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public boolean requiresPerm() {
        return permission;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public void setRewards(List<String> rewards) {
        this.rewards = rewards;
    }

    public List<String> getBlacklistedRegions() {
        return blacklistedRegions;
    }

    public void setBlacklistedRegions(List<String> blacklistedRegions) {
        this.blacklistedRegions = blacklistedRegions;
    }

    public List<String> getBlacklistedWorlds() {
        return blacklistedWorlds;
    }

    public void setBlacklistedWorlds(List<String> blacklistedWorlds) {
        this.blacklistedWorlds = blacklistedWorlds;
    }

    public String getUnrevealedFirstFormat() {
        return unrevealedFirstFormat;
    }

    public void setUnrevealedFirstFormat(String unrevealedFirstFormat) {
        this.unrevealedFirstFormat = unrevealedFirstFormat;
    }

    public String getUnrevealedSecondFormat() {
        return unrevealedSecondFormat;
    }

    public void setUnrevealedSecondFormat(String unrevealedSecondFormat) {
        this.unrevealedSecondFormat = unrevealedSecondFormat;
    }

    public String getUnrevealedSubtitle() {
        return unrevealedSubtitle;
    }

    public void setUnrevealedSubtitle(String unrevealedSubtitle) {
        this.unrevealedSubtitle = unrevealedSubtitle;
    }

    public String getRevealedFirstFormat() {
        return revealedFirstFormat;
    }

    public void setRevealedFirstFormat(String revealedFirstFormat) {
        this.revealedFirstFormat = revealedFirstFormat;
    }

    public String getRevealedSecondFormat() {
        return revealedSecondFormat;
    }

    public void setRevealedSecondFormat(String revealedSecondFormat) {
        this.revealedSecondFormat = revealedSecondFormat;
    }

    public String getRevealedTitle() {
        return revealedTitle;
    }

    public void setRevealedTitle(String revealedTitle) {
        this.revealedTitle = revealedTitle;
    }

    public String getRevealedSubtitle() {
        return revealedSubtitle;
    }

    public void setRevealedSubtitle(String revealedSubtitle) {
        this.revealedSubtitle = revealedSubtitle;
    }

    public boolean requireFormat() {
        return format;
    }

    public long getAmount() {
        if (minAmount == maxAmount) return minAmount;
        return ThreadLocalRandom.current().nextLong(minAmount, maxAmount);
    }

    public boolean hasPermission(Player player) {
        if (requiresPerm()) return player.hasPermission("pouches.use." + getId().toLowerCase());
        return true;
    }

    public void sendTitle(Player player, Long amount) {
        new BukkitRunnable() {
            String formattedNumber = NumberFormat.getIntegerInstance().format(amount);
            String numberStr = String.valueOf(amount);

            int numberWidth = numberStr.toCharArray().length;
            int number = numberWidth;

            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();

                String unrevealed = number == 0 ? "" : numberStr.substring(0, number);
                String revealed = number == 0 ? numberStr : numberStr.split(unrevealed, 2)[1];

                stringBuilder.append(getUnrevealedFirstFormat());
                stringBuilder.append(unrevealed);

                stringBuilder.append(getUnrevealedSecondFormat());
                number--;
                stringBuilder.append(revealed);
                stringBuilder.append("&r");

                JSONObject titleObj = new JSONObject();
                titleObj.put("text", stringBuilder.toString());

                TitleRegisterable.getTitle().sendTitle(player, Common.translate(titleObj.toJSONString()));

                JSONObject subTitleObj = new JSONObject();
                subTitleObj.put("text", getUnrevealedSubtitle());

                TitleRegisterable.getTitle().sendSubtitle(player, Common.translate(subTitleObj.toJSONString()));

                if (number == -1) {
                    new BukkitRunnable() {
                        int blink = 10;

                        @Override
                        public void run() {
                            if (blink == 0) {
                                runRewards(player, amount);
                                getCurrentPouches().remove(player.getUniqueId());
                                cancel();
                            }
                            boolean isEven = blink % 2 == 0;

                            JSONObject titleObj = new JSONObject();
                            titleObj.put("text", (isEven ? getRevealedFirstFormat() : getRevealedSecondFormat()) + getRevealedTitle().replace("%amount%", requireFormat() ? formattedNumber : numberStr));
                            TitleRegisterable.getTitle().sendTitle(player, Common.translate(titleObj.toJSONString()));

                            JSONObject subTitleObj = new JSONObject();
                            subTitleObj.put("text", getRevealedSubtitle());
                            TitleRegisterable.getTitle().sendSubtitle(player, Common.translate(subTitleObj.toJSONString()));

                            blink--;
                        }
                    }.runTaskTimer(POUCHES, 10, 3);
                    cancel();
                }
            }
        }.runTaskTimer(Pouches.getInstance(), 10, 10);
    }

    private void runRewards(Player p, Long amount) {
        List<String> strings = getRewards();
        for (int i = 0, stringsSize = strings.size(); i < stringsSize; i++) {
            String msg = strings.get(i);
            boolean singleAction = !msg.contains(" ");
            String actionPrefix = singleAction ? msg : msg.split(" ", 2)[0].toUpperCase();
            String actionData = singleAction ? "" : msg.split(" ", 2)[1];
            actionData = Common.translate(actionData);
            actionData = actionData.replace("%player%", p.getName());
            actionData = actionData.replace("%amount%", amount + "");
            actionData = Common.parse(p, actionData);

            switch (actionPrefix) {
                case "[CONSOLE]":
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actionData);
                    break;
                case "[PLAYER]":
                    p.performCommand(actionData);
                    break;
                case "[BROADCAST]":
                    Bukkit.broadcastMessage(actionData);
                    break;
                case "[MESSAGE]":
                    p.sendMessage(actionData);
                    break;
                case "[CHAT]":
                    p.chat(actionData);
            }
        }
    }
}
