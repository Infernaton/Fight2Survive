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

    public static void tickTimerSound() {
        toPlayers(Constants.getAllTeamsPlayer(), Sound.ORB_PICKUP, 15,15);
    }

    public static void selectingMenu(Player player) {
        toPlayer(player, Sound.LEVEL_UP, 15,15);
    }

    public static void selectingOptions(Player player) {
        toPlayer(player, Sound.CLICK, 15,15);
    }

    public static void ErrorSound(Player player) {
        toPlayer(player, Sound.NOTE_BASS, 30,1);
    }

    public static void openDoor() { toPlayers(Constants.getAllTeamsPlayer(), Sound.CHEST_OPEN, 30, 30);}
}
