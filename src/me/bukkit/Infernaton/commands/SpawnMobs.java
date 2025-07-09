package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.handler.WaveHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnMobs implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp())
            return false;

        if (cmd.getName().equalsIgnoreCase("mob_zombie") && sender instanceof Player) {
            WaveHandler.Instance().generateMobWave();
            return true;
        }
        return false;
    }
}
