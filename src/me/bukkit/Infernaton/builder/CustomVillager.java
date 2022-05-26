package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class CustomVillager{
    private EntityVillager ev;
    private MerchantRecipeList list = new MerchantRecipeList();

    public CustomVillager(FightToSurvive context, Villager villager) {
        ev = ((CraftVillager) villager).getHandle();
    }

    public CustomVillager addRecipe(MerchantRecipe recipe) {
        list.add(recipe);
        return this;
    }

    public void finish() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.set("Offers", list.a());
        nbt.setByte("NoAI", (byte) 1);

        ev.a(nbt);
    }
}