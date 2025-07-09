package me.bukkit.Infernaton.store;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InterfaceMenu {

    private static Map<Integer, ItemStack> createException(int pos, ItemStack item) {
        Map<Integer, ItemStack> except = new HashMap<>();
        except.put(pos, item);
        return except;
    }

    private static Inventory separatorLine(Inventory inv, int firstCell, Map<Integer, ItemStack> except) {
        for (int i = firstCell; i < firstCell + 9; i++) {
            inv.setItem(i, except.containsKey(i) ? except.get(i) : CustomItem.separator());
        }
        return inv;
    }

    public static Inventory selectTeam() {
        Inventory inv = Bukkit.createInventory(null, 45, StringConfig.teamInventory());
        inv.setItem(20, CustomItem.blueWool());
        inv.setItem(24, CustomItem.redWool());
        inv.setItem(13, CustomItem.spectatorWool());

        Map<Integer, ItemStack> except = new HashMap<>();
        except.put(44, CustomItem.optionsWool());
        except.put(40, CustomItem.gameStartWool());

        separatorLine(inv, 36, except);

        return inv;
    }

    public static Inventory cancelStart() {
        Inventory inv = Bukkit.createInventory(null, 9, StringConfig.cancelInventory());
        separatorLine(inv, 0, createException(4, CustomItem.gameCancelWool()));
        return inv;
    }

    public static Inventory optionsInventory() {
        Inventory inv = Bukkit.createInventory(null, 45, StringConfig.optionInventory());

        separatorLine(inv, 36, createException(44, CustomItem.returnWool()));
        return inv;
    }
}