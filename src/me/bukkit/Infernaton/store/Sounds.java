package me.bukkit.Infernaton.store;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class Sounds {

    private static void toPlayers(List<Player> playerList, Sound sound, float volume, float pitch) {
        playerList.forEach(player -> toPlayer(player, sound, volume, pitch));
    }

    private static void toWorld(Location location, Sound sound, float volume, float pitch){
        Bukkit.getWorld(CoordStorage.worldName).playSound(location, sound, volume, pitch);
    }

    private static void toPlayer(Player p, Sound sound, float volume, float pitch) {
        p.playSound(p.getLocation(), sound, volume, pitch);
    }

    public static void finalPhaseSound() {
        toPlayers(Constants.getAllTeamsPlayer(), Sound.ENDERDRAGON_GROWL, 30,30);
    }
}
