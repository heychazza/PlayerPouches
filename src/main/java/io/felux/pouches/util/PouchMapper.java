package io.felux.pouches.util;

import de.tr7zw.itemnbtapi.NBTItem;
import io.felux.pouches.api.Pouch;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PouchMapper {
    public static Pouch voucherMap(FileConfiguration data) {
        Pouch pouch = new Pouch(data.getString("id", "invalid"));
        pouch.setItem(itemstackMap(data));
        pouch.setPermission(data.getBoolean("settings.permission", false));
        pouch.setMinAmount(data.getLong("settings.amount.min", 1000));
        pouch.setMaxAmount(data.getLong("settings.amount.max", 3000));
        pouch.setPouchRewards(data.getStringList("rewards"));
        pouch.setBlacklistedRegions(data.getStringList("settings.blacklist.regions"));
        pouch.setBlacklistedWorlds(data.getStringList("settings.blacklist.worlds"));

        pouch.setUnrevealedFirstFormat(data.getString("settings.title.unrevealed.first"));
        pouch.setUnrevealedSecondFormat(data.getString("settings.title.unrevealed.second"));
        pouch.setUnrevealedSubtitle(data.getString("settings.title.unrevealed.subtitle"));

        pouch.setRevealedFirstFormat(data.getString("settings.title.revealed.first"));
        pouch.setRevealedSecondFormat(data.getString("settings.title.revealed.second"));
        pouch.setRevealedTitle(data.getString("settings.title.revealed.title"));
        pouch.setRevealedSubtitle(data.getString("settings.title.revealed.subtitle"));

        pouch.setFormat(data.getBoolean("settings.format"));
        return pouch;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack itemstackMap(FileConfiguration data) {
        Material pouchMaterial = Material.matchMaterial(data.getString("item.material", "PAPER"));

        if (pouchMaterial == null)
            throw new RuntimeException("Material " + data.getString("item.material", "PAPER") + " is invalid!");
        ItemStack itemStack = new ItemStack(pouchMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();

        String voucherId = data.getString("id", "unknown");

        if (itemMeta != null) {
            itemMeta.setDisplayName(Common.translate(data.getString("item.name", "&b" + voucherId + " Pouch")));
            itemMeta.setLore(Common.translate(data.getStringList("item.lore")));

            if (data.getBoolean("item.glow", false)) {
                itemMeta.addEnchant(Enchantment.WATER_WORKER, 1, false);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
            }
        }

        itemStack.setItemMeta(itemMeta);
        itemStack.setDurability((short) data.getInt("item.data", 0));

        NBTItem nbt = new NBTItem(itemStack);
        nbt.setString("pouches-id", voucherId);

        return nbt.getItem();
    }
}
