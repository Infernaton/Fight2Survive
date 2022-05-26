package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HandlePlayerState {

    private FightToSurvive main;

    public HandlePlayerState(FightToSurvive main){
        this.main = main;
    }

    public void clear(Player player){
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
     * @param player the actual player
     */
    public void resetPlayerState(Player player){
        /**
         * reset potions
         * reset armure
         */
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);
        player.setHealth(20);

        clear(player);

        main.constH().getSpectators().add(player);
        removeAllPotionEffect(player);
        givePotionEffect(player, PotionEffectType.SATURATION);
        givePotionEffect(player, PotionEffectType.DAMAGE_RESISTANCE);
        main.HI().giveItemInInventory(player, main.HI().magicCompass(),4);
    }

    public void setPlayer(Player player){
        resetPlayerState(player);
        player.teleport(main.constH().getSpawnCoordinate());
    }

    public void givePotionEffect(Player player, PotionEffectType potion){
        player.addPotionEffect(new PotionEffect(potion, 999999, 5));
    }
    public void removeAllPotionEffect(Player player){
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }
}