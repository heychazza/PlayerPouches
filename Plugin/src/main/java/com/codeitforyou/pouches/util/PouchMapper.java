package com.codeitforyou.pouches.util;

import com.codeitforyou.lib.api.general.StringUtil;
import com.codeitforyou.lib.api.nbt.NBT;
import com.codeitforyou.lib.api.xseries.XMaterial;
import com.codeitforyou.pouches.api.Pouch;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

public class PouchMapper {
    public static Pouch voucherMap(FileConfiguration data) {
        Pouch pouch = new Pouch(data.getString("id", "invalid"));
        pouch.setItem(itemstackMap(data));
        pouch.setPermission(data.getBoolean("settings.permission", false));

        pouch.setMinAmount(data.getLong("settings.amount.min", 1000));
        pouch.setMaxAmount(data.getLong("settings.amount.max", 3000));
        pouch.setRewards(data.getStringList("rewards"));

        pouch.setBlacklistedRegions(data.getStringList("settings.blacklist.region"));
        pouch.setBlacklistedWorlds(data.getStringList("settings.blacklist.world"));

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
        if (data == null) return null;
        XMaterial pouchMaterial = XMaterial.matchXMaterial(data.getString("item.material", "PAPER")).orElse(null);

        if (pouchMaterial == null)
            throw new RuntimeException("Material " + data.getString("item.material", "PAPER") + " is invalid!");

        ItemStack itemStack = new ItemStack(pouchMaterial.parseMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();

        String voucherId = data.getString("id", "unknown");

        if (itemMeta != null) {
            itemMeta.setDisplayName(StringUtil.translate(data.getString("item.name", "&b" + voucherId + " Pouch")));
            itemMeta.setLore(StringUtil.translate(data.getStringList("item.lore")));

            if (data.getBoolean("item.glow", false)) {
                itemMeta.addEnchant(Enchantment.WATER_WORKER, 1, false);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
            }
        }

        itemStack.setItemMeta(itemMeta);
        itemStack.setDurability((short) data.getInt("item.data", 0));

        if (data.getString("item.texture") != null && !data.getString("item.texture").isEmpty()) {
            if (XMaterial.PLAYER_HEAD.isOneOf(Collections.singletonList(data.getString("item.material", "PAPER")))) {
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                SkullUtils.getSkullByValue(skullMeta, data.getString("item.texture", ""));
                itemStack.setItemMeta(skullMeta);
            }
        }

        NBT nbt = NBT.get(itemStack);
        if (nbt != null) {
            nbt.setString("pouches-id", voucherId);
            return nbt.apply(itemStack);
        }

        return null;
    }
}
