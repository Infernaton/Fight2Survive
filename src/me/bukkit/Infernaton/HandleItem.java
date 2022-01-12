package me.bukkit.Infernaton;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandleItem {

    /**
     * Give the compass that help the player to move around or start a game
     * @param player to give that compass
     */
    public static void giveItemInInventory(Player player, ItemStack item, int slot){
        player.getInventory().setItem(slot, item);
        player.updateInventory();
    }

    public static ItemStack magicCompass(){
        return new ItemBuilder(Material.COMPASS).setName("§aNavigation").toItemStack();
    }
    public static ItemStack blueWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)11).setName("§1Equipe Bleu").toItemStack();
    }
    public static ItemStack redWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)14).setName("§4Equipe Rouge").toItemStack();
    }
}
