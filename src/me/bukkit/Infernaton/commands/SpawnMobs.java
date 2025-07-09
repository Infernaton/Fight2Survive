package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.WaveHandler;
import me.bukkit.Infernaton.store.Mobs;
import me.bukkit.Infernaton.store.StringConfig;

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

        else if (cmd.getName().equalsIgnoreCase("set_villagers")) {
            if (FightToSurvive.isGameState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, StringConfig.setVill());
                Mobs.setAllPnj();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("kill_pnj")) {
            ChatHandler.sendInfoMessage(sender, StringConfig.killVill());
            Mobs.killPnj();
            return true;
        }
        return false;
    }
}
