package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InterfaceHandler {

    private FightToSurvive main;

    public InterfaceHandler(FightToSurvive main){
        this.main = main;
    }

    public Inventory selectTeam(){
        Inventory inv = Bukkit.createInventory(null, 45, main.stringH().teamInventory());
        inv.setItem(20, main.HI().blueWool());
        inv.setItem(24, main.HI().redWool());
        inv.setItem(13, main.HI().spectatorWool());
        inv.setItem(40, main.HI().gameStartWool());
        inv.setItem(44, main.HI().optionsWool());

        return inv;
    }
    public Inventory cancelStart(){
        Inventory inv = Bukkit.createInventory(null, 9, main.stringH().cancelInventory());
        for (int i=0; i <inv.getSize(); i++){
            if (i==4){
                inv.setItem(i, main.HI().gameCancelWool());
            }else {
                inv.setItem(i, main.HI().separator());
            }
        }
        return inv;
    }

    public Inventory optionsInventory(){
        Inventory inv = Bukkit.createInventory(null, 45, main.stringH().optionInventory());
        inv.setItem(44, main.HI().returnWool());
        return inv;
    }
}