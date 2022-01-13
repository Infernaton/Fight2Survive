package me.bukkit.Infernaton;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OpenMenuTrade{
    private final String invName;
    private final MerchantRecipeList recipe = new MerchantRecipeList();
    public OpenMenuTrade(String name){
        this.invName = name;

    }

    public OpenMenuTrade addTrade(ItemStack one, ItemStack two){
        recipe.add(new MerchantRecipe(CraftItemStack.asNMSCopy(one),CraftItemStack.asNMSCopy(two)));
        return this;
    }
    public OpenMenuTrade addTrade(ItemStack one, ItemStack two, ItemStack three){
        recipe.add(new MerchantRecipe(CraftItemStack.asNMSCopy(one),CraftItemStack.asNMSCopy(two),CraftItemStack.asNMSCopy(three)));
        return this;
    }
    public void openTrade(Player player){
        final EntityHuman human = ((CraftPlayer)player).getHandle();
        human.openTrade(new IMerchant() {
            @Override
            public void a_(EntityHuman entityHuman) {

            }

            @Override
            public EntityHuman v_() {
                return human;
            }

            @Override
            public MerchantRecipeList getOffers(EntityHuman entityHuman) {
                return recipe;
            }

            @Override
            public void a(MerchantRecipe merchantRecipe) {

            }

            @Override
            public void a_(net.minecraft.server.v1_8_R3.ItemStack itemStack) {

            }

            @Override
            public IChatBaseComponent getScoreboardDisplayName() {
                return IChatBaseComponent.ChatSerializer.a(invName);
            }

        });
    }
}
