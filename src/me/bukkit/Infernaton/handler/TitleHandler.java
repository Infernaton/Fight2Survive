package me.bukkit.Infernaton.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TitleHandler {

    public static  void toAllPlayer(String title, String subtitle) {
        Bukkit.getOnlinePlayers().forEach(player -> sendTitle(player, title, subtitle));
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle);
    }
}
