package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SpawnMobs implements CommandExecutor {

    final private FightToSurvive main;
    public SpawnMobs(FightToSurvive fightToSurvive) {
        this.main  = fightToSurvive;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) return false;

        if (cmd.getName().equalsIgnoreCase("mob_zombie") && sender instanceof Player) {
            main.MH().generateMobWave();
            return true;
        }
        return false;
    }
}
