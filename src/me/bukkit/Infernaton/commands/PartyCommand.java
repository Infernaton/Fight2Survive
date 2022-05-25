package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.CountDown;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.InterfaceHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommand implements CommandExecutor {

    private final FightToSurvive main;
    private InterfaceHandler IH;

    public PartyCommand(FightToSurvive main) {
        this.main = main;
        this.IH = new InterfaceHandler(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (cmd.getName().equalsIgnoreCase("start")) {
            if (sender instanceof Player) main.onStarting((Player) sender);
            else ChatHandler.sendError(sender, "No Starting from the console");
            return true;
        }

        /*
            Just after the starting command, and before the game actually start, we can cancel that countdown
         */
        else if (cmd.getName().equalsIgnoreCase("cancelStart")) {
            if (main.constH().isState(GState.STARTING)) {
                main.cancelStart();
            } else {
                ChatHandler.sendError(sender, "Any countdown aren't set right now.");
            }
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("reset")){
            if (main.constH().isState(GState.PLAYING)){
                ChatHandler.sendMessageListPlayer(main.constH().getAllTeamsPlayer(), "Canceling the game");
                main.cancel();
            } else {
                ChatHandler.sendError(sender, "Any game is playing right now.");
            }
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("forceFinal")){
            if(main.constH().isState(GState.PLAYING) || main.constH().isState(GState.WAITING)){
                main.FP().on();
            }
            else {
                ChatHandler.sendError(sender, "Can't perform this command while any game is started");
            }
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("endgame")){
            main.finish();
            return true;
        }

        return false;
    }
}
