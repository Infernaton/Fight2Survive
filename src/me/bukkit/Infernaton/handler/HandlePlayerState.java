package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.store.Constants;
import me.bukkit.Infernaton.store.CoordStorage;
import me.bukkit.Infernaton.store.CustomItem;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HandlePlayerState {

    public static void clear(Player player) {
        player.getInventory().clear();
        player.setExp(0f);
        player.setLevel(0);

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    /**
     * Reset the actual status of the player on the server
     * 
     * @param player the actual player
     */
    private static void resetPlayerState(Player player) {
        /**
         * reset potions
         * reset armor
         */
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);
        player.setHealth(20);

        Constants.addDefaultTeam(player);
        clear(player);

        removeAllPotionEffect(player);
        givePotionEffect(player, PotionEffectType.SATURATION);
        givePotionEffect(player, PotionEffectType.DAMAGE_RESISTANCE);
        CustomItem.setItemInInventory(player, CustomItem.magicCompass(), 4);
    }

    /**
     * Set the current player ready to play the game
     * If the game is already started, set it in the spectator team
     * @param player the current player
     */
    public static void setPlayer(Player player) {
        changeZone(player, CoordStorage.getSpawnCoordinate(), true);
    }

    public static void changeZone(Player player, Location location, boolean resetState) {
        if (resetState) resetPlayerState(player);
        player.teleport(location);
    }

    public static void givePotionEffect(Player player, PotionEffectType potion) {
        player.addPotionEffect(new PotionEffect(potion, 999999, 5));
    }

    public static void removeAllPotionEffect(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }

    public static void giveStarterPack(Player player) {
        player.getInventory().addItem(CustomItem.woodAxe(), new ItemStack(Material.COOKED_BEEF, 10));
    }

    public static boolean isPlayerInPlayableTeam(Player player) {
        return Constants.getAllTeamsPlayer().contains(player);
    }
}