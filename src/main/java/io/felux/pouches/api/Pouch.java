package io.felux.pouches.api;

import com.google.common.collect.Maps;
import io.felux.pouches.Pouches;
import io.felux.pouches.util.Common;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Pouch {
    private final Pouches POUCHES = Pouches.getInstance();

    private String id;
    private ItemStack item;
    private boolean permission;
    private long minAmount;
    private long maxAmount;
    private List<String> pouchRewards;
    private List<String> blacklistedRegions;
    private List<String> blacklistedWorlds;
    private static final Map<String, Pouch> pouches = Maps.newConcurrentMap();

    public Pouch(String id) {
        this.id = id;
    }

    public Pouch(String id, ItemStack item, boolean permission, Long minAmount, Long maxAmount, List<String> pouchRewards, List<String> blacklistedRegions, List<String> blacklistedWorlds) {
        this.id = id.toLowerCase();
        this.item = item;
        this.permission = permission;
        this.pouchRewards = pouchRewards;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.blacklistedRegions = blacklistedRegions;
        this.blacklistedWorlds = blacklistedWorlds;
    }

    public String getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean requiresPerm() {
        return permission;
    }

    public boolean hasPermission(Player player) {
        if (requiresPerm()) return player.hasPermission("pouches.use." + getId());
        return true;
    }

    public List<String> getPouchRewards() {
        return pouchRewards;
    }

    public List<String> getBlacklistedRegions() {
        return blacklistedRegions;
    }

    public List<String> getBlacklistedWorlds() {
        return blacklistedWorlds;
    }

    public long getAmount() {
        if (minAmount == maxAmount) return minAmount;
        return ThreadLocalRandom.current().nextLong(minAmount, maxAmount);
    }

    public void sendTitle(Player player, Long amount) {
        new BukkitRunnable() {
            String formattedNumber = NumberFormat.getIntegerInstance().format(amount);
            String numberStr = String.valueOf(amount);

            int numberWidth = numberStr.toCharArray().length;
            int number = 0;

            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("&a&l&n$");

                for (int i = 0; i <= number; ++i) {
                    stringBuilder.append(numberStr.charAt(i));
                }

                stringBuilder.append("&c&l&n&k");
                number++;
                stringBuilder.append(numberStr.substring(number));
                stringBuilder.append("&r");

                JSONObject titleObj = new JSONObject();
                titleObj.put("text", stringBuilder.toString());

                POUCHES.getTitle().sendTitle(player, Common.translate(titleObj.toJSONString()));

                JSONObject subTitleObj = new JSONObject();
                subTitleObj.put("text", "&7&o(( Opening pouch... ))");

                POUCHES.getTitle().sendSubtitle(player, Common.translate(subTitleObj.toJSONString()));
                System.out.println(" ");


                if (number == numberWidth) {
                    new BukkitRunnable() {
                        int blink = 6;

                        @Override
                        public void run() {
                            if (blink == 0) cancel();
                            boolean isEven = blink % 2 == 0;

                            JSONObject titleObj = new JSONObject();
                            titleObj.put("text", (isEven ? "&a" : "&c") + "&l$" + formattedNumber);
                            POUCHES.getTitle().sendTitle(player, Common.translate(titleObj.toJSONString()));

                            JSONObject subTitleObj = new JSONObject();
                            subTitleObj.put("text", "&7&o(( Congratulations! ))");
                            POUCHES.getTitle().sendSubtitle(player, Common.translate(subTitleObj.toJSONString()));

                            blink--;

                        }
                    }.runTaskTimer(POUCHES, 10, 3);
                    cancel();
                }
            }
        }.runTaskTimer(Pouches.getInstance(), 10, 10);
    }

    public static Map<String, Pouch> getPouches() {
        return pouches;
    }
}
