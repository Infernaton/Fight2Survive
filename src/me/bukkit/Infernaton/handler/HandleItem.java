package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandleItem {

    private FightToSurvive main;

    public HandleItem(FightToSurvive main){
        this.main = main;
    }

    /**
     * Give the compass that help the player to move around or start a game
     * @param player to give that compass
     */
    public void giveItemInInventory(Player player, ItemStack item, int slot){
        player.getInventory().setItem(slot, item);
        player.updateInventory();
    }

    public ItemStack magicCompass(){
        return new ItemBuilder(Material.COMPASS).setName("§aNavigation").toItemStack();
    }
    public ItemStack blueWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)11).setName("§1Equipe Bleu").toItemStack();
    }
    public ItemStack redWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)14).setName("§4Equipe Rouge").toItemStack();
    }
    public ItemStack spectatorWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)7).setName("§7Spectateur").toItemStack();
    }

    public ItemStack paperKey(){
        return new ItemBuilder(Material.PAPER, 1, (byte)1).setName("KEY").toItemStack();
    }
}