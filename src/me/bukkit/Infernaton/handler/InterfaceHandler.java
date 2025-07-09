package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.store.StringConfig;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InterfaceHandler {

    private FightToSurvive main;

    public InterfaceHandler(FightToSurvive main) {
        this.main = main;
    }

    private Map<Integer, ItemStack> createException(int pos, ItemStack item) {
        Map<Integer, ItemStack> except = new HashMap<>();
        except.put(pos, item);
        return except;
    }

    private Inventory separatorLine(Inventory inv, int firstCell, Map<Integer, ItemStack> except) {
        for (int i = firstCell; i < firstCell + 9; i++) {
            inv.setItem(i, except.containsKey(i) ? except.get(i) : main.HI().separator());
        }
        return inv;
    }

    public Inventory selectTeam() {
        Inventory inv = Bukkit.createInventory(null, 45, StringConfig.teamInventory());
        inv.setItem(20, main.HI().blueWool());
        inv.setItem(24, main.HI().redWool());
        inv.setItem(13, main.HI().spectatorWool());

        Map<Integer, ItemStack> except = new HashMap<>();
        except.put(44, main.HI().optionsWool());
        except.put(40, main.HI().gameStartWool());

        separatorLine(inv, 36, except);

        return inv;
    }

    public Inventory cancelStart() {
        Inventory inv = Bukkit.createInventory(null, 9, StringConfig.cancelInventory());
        separatorLine(inv, 0, createException(4, main.HI().gameCancelWool()));
        return inv;
    }

    public Inventory optionsInventory() {
        Inventory inv = Bukkit.createInventory(null, 45, StringConfig.optionInventory());

        separatorLine(inv, 36, createException(44, main.HI().returnWool()));
        return inv;
    }
}