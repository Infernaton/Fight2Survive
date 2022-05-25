package me.bukkit.Infernaton.builder;

import java.lang.reflect.Field;

import me.bukkit.Infernaton.FightToSurvive;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.MerchantRecipe;
import net.minecraft.server.v1_8_R3.MerchantRecipeList;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class CustomVillager {
    private EntityVillager ev;
    private MerchantRecipeList list = new MerchantRecipeList();

    public CustomVillager(FightToSurvive context, Villager villager) {
        ev = ((CraftVillager) villager).getHandle();
    }

    public CustomVillager addRecipe(MerchantRecipe recipe) {
        ev.a(recipe);
        return this;
    }

    public boolean finish() {
        ev.o(true);
        return true;
    }
}