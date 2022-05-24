package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.OpenMenuTrade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InterfaceHandler {

    private FightToSurvive main;

    public InterfaceHandler(FightToSurvive main){
        this.main = main;
    }

    public OpenMenuTrade tradeKey(ItemStack trade){
        return tradeKey(trade, new ItemStack(Material.AIR));
    }
    public OpenMenuTrade tradeKey(ItemStack first, ItemStack second){
        return new OpenMenuTrade("Key").addTrade(first, second, main.HI().paperKey());
    }

    public Inventory selectTeam(){
        Inventory inv = Bukkit.createInventory(null, 27, "§7Equipe");

        inv.setItem(11, main.HI().blueWool());
        inv.setItem(15, main.HI().redWool());
        inv.setItem(22, main.HI().spectatorWool());

        return inv;
    }
}