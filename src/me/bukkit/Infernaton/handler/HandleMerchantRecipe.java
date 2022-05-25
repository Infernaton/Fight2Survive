package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import net.minecraft.server.v1_8_R3.MerchantRecipe;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class HandleMerchantRecipe {

    private FightToSurvive main;

    public HandleMerchantRecipe(FightToSurvive context){
        this.main = context;
    }

    public MerchantRecipe withBukkitItem(ItemStack item1, ItemStack item2, ItemStack out){
        return new MerchantRecipe(CraftItemStack.asNMSCopy(item1), CraftItemStack.asNMSCopy(item2), CraftItemStack.asNMSCopy(out));
    }

    public MerchantRecipe tradingKey(ItemStack item1, ItemStack item2){
        return withBukkitItem(item1, item2, main.HI().paperKey());
    }
    public MerchantRecipe tradingKey(ItemStack item1){
        return withBukkitItem(item1, new ItemStack(Material.AIR), main.HI().paperKey());
    }
    public MerchantRecipe defaultTrade(){
        return withBukkitItem(new ItemStack(Material.AIR), new ItemStack(Material.AIR), main.HI().paperKey());
    }
}
