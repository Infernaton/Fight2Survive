package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.FinalPhaseHandler;
import me.bukkit.Infernaton.handler.store.StringConfig;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommand implements CommandExecutor {

    private final FightToSurvive main;

    public PartyCommand() {
        this.main = FightToSurvive.Instance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (!sender.isOp())
            return false;

        if (cmd.getName().equalsIgnoreCase("start")) {
            if (sender instanceof Player)
                main.onStarting((Player) sender);
            else
                ChatHandler.sendError(sender, StringConfig.cantFromConsole());
            return true;
        }

        /*
         * Just after the starting command, and before the game actually start, we can
         * cancel that countdown
         */
        else if (cmd.getName().equalsIgnoreCase("cancelStart")) {
            if (FightToSurvive.isGameState(GState.STARTING))
                main.cancelStart();
            else
                ChatHandler.sendError(sender, StringConfig.noCountdown());
            return true;
        } else if (cmd.getName().equalsIgnoreCase("reset")) {
            if (FightToSurvive.isGameState(GState.PLAYING))
                main.cancel();
            else
                ChatHandler.sendError(sender, StringConfig.noGame());
            return true;
        } else if (cmd.getName().equalsIgnoreCase("forceFinal")) {
            if (FightToSurvive.isGameState(GState.PLAYING) || FightToSurvive.isGameState(GState.WAITING))
                FinalPhaseHandler.Instance().activate();
            else
                ChatHandler.sendCantWhilePlaying(sender);
            return true;
        } else if (cmd.getName().equalsIgnoreCase("endgame")) {
            main.finish();
            return true;
        }

        return false;
    }
}
