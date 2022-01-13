package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.handler.HandlePlayerState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("setPlayer")){
            if (args.length == 0){
                sender.sendMessage("§2Usage: /setPlayer <username>");
            }
            else if (args.length == 1){
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                HandlePlayerState.setPlayer(targetPlayer);
            }
            else {
                sender.sendMessage("§2Too many argument");
            }
            return true;
        }
        return false;
    }
}
