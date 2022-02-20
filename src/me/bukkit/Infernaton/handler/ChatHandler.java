package me.bukkit.Infernaton.handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * To manage the message sends by the plugin
 */
public class ChatHandler {

    private static String starter(){
        return ChatColor.GRAY + "[FightToSurvive] " + ChatColor.WHITE;
    }

    public static void toAllPlayer(String msg){
        sendMessageListPlayer(new ArrayList<>(Bukkit.getOnlinePlayers()), msg);
    }

    public static void sendMessageListPlayer(List<Player> players, String msg){
        for (Player player : players) {
            sendInfoMessage(player, starter() + msg);
        }
    }

    public static void broadcast(String msg){
        Bukkit.broadcastMessage(starter() + msg);
    }

    public static void sendMessage(CommandSender sender, String msg){
        sender.sendMessage(starter() + msg);
    }

    public static void sendMessage(Player player, String msg){
        player.sendMessage(starter() + msg);
    }

    public static void sendError(CommandSender sender, String msg){
        sender.sendMessage(starter() + "§4" + msg);
    }

    public static void sendCorrectUsage(CommandSender sender, String msg){
        sender.sendMessage(starter() + "§c" + msg);
    }

    public static void sendInfoMessage(CommandSender sender, String msg){
        sender.sendMessage(starter() + "§3" + msg);
    }
}