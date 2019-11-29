package io.felux.pouches.util;

import io.felux.pouches.api.Pouch;
import io.felux.pouches.nbt.NBT;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PouchMapper {
    public static Pouch voucherMap(FileConfiguration data) {
        return new Pouch(data.getString("id", "invalidid"),
                itemstackMap(data),
                data.getBoolean("settings.permission", false),
                PouchRewardMapper.rewardMap(data),
                data.getStringList("settings.blacklist.regions"),
                data.getStringList("settings.blacklist.worlds"));
    }

    public static ItemStack itemstackMap(FileConfiguration data) {
        Material pouchMaterial = Material.matchMaterial(data.getString("item.material", "PAPER"));

        if(pouchMaterial == null) throw new RuntimeException("Material " + data.getString("item.material", "PAPER") + " is invalid!");
        ItemStack itemStack = new ItemStack(pouchMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();

        String voucherId = data.getString("id", "unknown");

        itemMeta.setDisplayName(Common.translate(data.getString("item.name", "&b" + voucherId + " Voucher")));
        itemMeta.setLore(Common.translate(data.getStringList("item.lore")));

        if (data.getBoolean("item.glow", false)) {
            itemMeta.addEnchant(Enchantment.WATER_WORKER, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        }

        itemStack.setItemMeta(itemMeta);
        itemStack.setDurability((short) data.getInt("item.data", 0));

        NBT nbt = NBT.get(itemStack);
        nbt.setString("voucherid", voucherId);

        return nbt.apply(itemStack);
    }
}
