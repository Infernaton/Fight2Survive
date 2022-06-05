package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterfaceHandler {

    private FightToSurvive main;

    public InterfaceHandler(FightToSurvive main){
        this.main = main;
    }

    private Map<Integer, ItemStack> createException(int pos, ItemStack item){
        Map<Integer, ItemStack> except = new HashMap<>();
        except.put(pos, item);
        return except;
    }
    private Map<Integer, ItemStack> createException(Map<Integer, ItemStack> except, int pos, ItemStack item){
        except.put(pos, item);
        return except;
    }
    private Inventory separatorLine(Inventory inv, int firstCell, Map<Integer, ItemStack> except){
        for(int i=firstCell; i< firstCell+9; i++){
            if (except.containsKey(i)){
                inv.setItem(i, except.get(i));
            }else {
                inv.setItem(i, main.HI().separator());
            }
        }
        return inv;
    }
    public Inventory customBlueWool(){
        ItemStack stackblue = new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getData());
        ItemMeta metablue = stackblue.getItemMeta();
        ArrayList<String> loreblue = new ArrayList<String>();
        loreblue.add("O holy stone");
        metablue.setLore(loreblue);
        stackblue.setItemMeta(metablue);
        return (Inventory) stackblue;
    }
    public Inventory customRedWool(){
        ItemStack stackred = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
        ItemMeta metared = stackred.getItemMeta();
        ArrayList<String> lorered = new ArrayList<String>();
        lorered.add("O holy stone");
        metared.setLore(lorered);
        stackred.setItemMeta(metared);
        return (Inventory) stackred;
    }
    public Inventory customSpectatorWool(){
        ItemStack stackspectator = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
        ItemMeta metaspectator = stackspectator.getItemMeta();
        ArrayList<String> lorespectator = new ArrayList<String>();
        lorespectator.add("O holy stone");
        metaspectator.setLore(lorespectator);
        stackspectator.setItemMeta(metaspectator);
        return (Inventory) stackspectator;
    }




    public Inventory selectTeam(){
        Inventory inv = Bukkit.createInventory(null, 45, main.stringH().teamInventory());
        inv.setItem(20, (ItemStack) customBlueWool());
        inv.setItem(24, (ItemStack) customRedWool());
        inv.setItem(13, (ItemStack) customSpectatorWool());

        separatorLine(inv, 36, createException(createException(40, main.HI().gameStartWool()), 44, main.HI().optionsWool()));

        return inv;
    }

    public Inventory cancelStart(){
        Inventory inv = Bukkit.createInventory(null, 9, main.stringH().cancelInventory());
        separatorLine(inv, 0, createException(4, main.HI().gameCancelWool()));
        return inv;
    }

    public Inventory optionsInventory(){
        Inventory inv = Bukkit.createInventory(null, 45, main.stringH().optionInventory());

        separatorLine(inv, 36, createException(44, main.HI().returnWool()));
        return inv;
    }
}