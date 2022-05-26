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
        Inventory inv = Bukkit.createInventory(null, 45, "§7Equipe");
        inv.setItem(20, main.HI().blueWool());
        inv.setItem(24, main.HI().redWool());
        inv.setItem(13, main.HI().spectatorWool());
        inv.setItem(31, main.HI().gameStartWool());
        inv.setItem(44, main.HI().optionsWool());

        return inv;
    }
    public Inventory cancelStart(){
        Inventory inv = Bukkit.createInventory(null, 9, "§7Cancel Start");
        inv.setItem(4, main.HI().gameCancelWool());
        return inv;
    }

    public Inventory optionsInventory(){
        Inventory inv = Bukkit.createInventory(null, 45, "§7Options");
        inv.setItem(44, main.HI().returnWool());
        return inv;
    }
}