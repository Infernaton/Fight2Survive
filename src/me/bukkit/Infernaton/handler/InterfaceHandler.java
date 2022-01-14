package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class InterfaceHandler {

    private FightToSurvive main;

    public InterfaceHandler(FightToSurvive main){
        this.main = main;
    }

    public Inventory selectTeam(){
        Inventory inv = Bukkit.createInventory(null, 27, "§7Equipe");

        inv.setItem(11, main.HI().blueWool());
        inv.setItem(15, main.HI().redWool());
        inv.setItem(22, main.HI().spectatorWool());

        return inv;
    }
}