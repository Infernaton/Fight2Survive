package me.bukkit.Infernaton;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandlePlayerState {

    /**
     * Reset the actual status of the player on the server
     * @param player the actual player
     */
    public static void resetPlayerState(Player player){
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.getInventory().clear();
        HandleItem.giveItem(player, HandleItem.magicCompass(),4);
    }
}