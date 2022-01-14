package me.bukkit.Infernaton.handler;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HandlePlayerState {

    private static ConstantHandler constH = new ConstantHandler();

    /**
     * Reset the actual status of the player on the server
     * @param player the actual player
     */
    public static void resetPlayerState(Player player){
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.getInventory().clear();
        constH.getSpectators().add(player);
        givePotionEffect(player, PotionEffectType.SATURATION);
        givePotionEffect(player, PotionEffectType.DAMAGE_RESISTANCE);
        HandleItem.giveItemInInventory(player, HandleItem.magicCompass(),4);
    }
    public static void setPlayer(Player player){
        resetPlayerState(player);
        player.teleport(constH.getSpawnCoordinate());
    }

    public static void givePotionEffect(Player player, PotionEffectType potion){
        player.addPotionEffect(new PotionEffect(potion, 999999, 5));
    }
    public static void removeAllPotionEffect(Player player){
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }
}