package me.bukkit.Infernaton.store;

import net.minecraft.server.v1_8_R3.MerchantRecipe;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Store all the trade from villager in the game
 */
public class CustomMerchantRecipe {

    public static MerchantRecipe withBukkitItem(ItemStack item1, ItemStack item2, ItemStack out, int uses,
            int maxUses) {
        return new MerchantRecipe(CraftItemStack.asNMSCopy(item1), CraftItemStack.asNMSCopy(item2),
                CraftItemStack.asNMSCopy(out), uses, maxUses);
    }

    public static MerchantRecipe withBukkitItem(ItemStack item1, ItemStack item2, ItemStack out) {
        return new MerchantRecipe(CraftItemStack.asNMSCopy(item1), CraftItemStack.asNMSCopy(item2),
                CraftItemStack.asNMSCopy(out));
    }

    public static MerchantRecipe tradingKey(ItemStack item1, ItemStack item2) {
        return withBukkitItem(item1, item2, CustomItem.paperKey(), 0, 1);
    }

    public static MerchantRecipe tradingKey(ItemStack item1) {
        return withBukkitItem(item1, new ItemStack(Material.AIR), CustomItem.paperKey(), 0, 1);
    }

    public static MerchantRecipe defaultTrade() {
        return withBukkitItem(new ItemStack(Material.DIRT), new ItemStack(Material.AIR), new ItemStack(Material.DIRT));
    }
}
