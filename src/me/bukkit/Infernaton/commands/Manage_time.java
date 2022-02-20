package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.builder.CountDown;
import me.bukkit.Infernaton.handler.ChatHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Manage_time implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("manage_time") && sender instanceof Player) {
            Player player = (Player) sender;

            for (int nombre = 2;nombre>=1;nombre++){
                try {
                    player.getWorld().setTime(1000);
                    ChatHandler.broadcast("Day Time");
                    TimeUnit.MILLISECONDS.sleep(5000);
                    player.getWorld().setTime(13000);
                    ChatHandler.broadcast("Night Time");
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }
}