package me.bukkit.Infernaton.handler;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterfaceHandler {
    private Inventory setMutlipleItem(Inventory inv, HashMap<Integer, ItemStack> itemList){

        for (Map.Entry<Integer, ItemStack> entry : itemList.entrySet()){
            inv.setItem(entry.getKey(), entry.getValue());
        }
        return inv;
    }
    public Inventory selectTeam(){
        Inventory inv = Bukkit.createInventory(null, 27, "§7Equipe");

        inv.setItem(11, HandleItem.blueWool());
        inv.setItem(15, HandleItem.redWool());
        inv.setItem(22, HandleItem.spectatorWool());

        return inv;
    }
}
