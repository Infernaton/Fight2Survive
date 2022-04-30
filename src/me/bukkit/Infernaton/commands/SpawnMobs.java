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
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("mob_zombie") && commandSender instanceof Player) {
            main.MobsHandler().generateMobWave();
            return true;
        }
        return false;
    }
}
