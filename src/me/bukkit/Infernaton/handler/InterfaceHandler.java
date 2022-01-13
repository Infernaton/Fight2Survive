package me.bukkit.Infernaton.handler;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class InterfaceHandler {
    public Inventory selectTeam(){
        Inventory inv = Bukkit.createInventory(null, 27, "§7Equipe");

        inv.setItem(11, HandleItem.blueWool());
        inv.setItem(15, HandleItem.redWool());
        inv.setItem(22, HandleItem.spectatorWool());

        return inv;
    }
}
