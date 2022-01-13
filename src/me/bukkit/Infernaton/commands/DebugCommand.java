package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.CountDownBuilder;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.HandlePlayerState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DebugCommand implements CommandExecutor {

    private FightToSurvive main;

    public DebugCommand(FightToSurvive main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setPlayer")){
            if (args.length == 0){
                ChatHandler.sendCorrectUsage(sender, "Usage: /setPlayer <username>");
            }
            else if (args.length == 1){
                try {
                    Player targetPlayer = Bukkit.getPlayerExact(args[0]);
                    System.out.println(targetPlayer);
                    HandlePlayerState.setPlayer(targetPlayer);
                    ChatHandler.sendMessage(sender, "§r" +args[0] + " §fis ready to play !");
                }catch (NullPointerException e){
                    ChatHandler.sendError(sender, "Player not found");
                }
            }
            else {
                ChatHandler.sendError(sender, "Too many argument");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("start")){
            if (main.constH().isState(GState.WAITING)){
                List<Player> redPlayers = main.constH().getRedTeam().getPlayers();
                List<Player> bluePlayers = main.constH().getBlueTeam().getPlayers();

                if (Bukkit.getScheduler().getPendingTasks().size() > 0) {
                    ChatHandler.sendError(sender, "CountDown already launch .");
                }
                //Compare if there the same numbers of players in each team
                else if (redPlayers.size() == bluePlayers.size() /*&& redPlayers.size() != 0*/) {
                    main.constH().setState(GState.STARTING);
                    CountDownBuilder.newCountDown(main, 10L, "§eStart the Game!");
                    System.out.println();
                } else {
                    ChatHandler.sendError(sender, "Not enough players.");
                }
            }else {
                ChatHandler.sendError(sender, "Party already started !");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("cancelStart")){
            if(main.constH().isState(GState.STARTING)){
                main.constH().setState(GState.WAITING);
                CountDownBuilder.stopAllCountdown(main);
                ChatHandler.broadcast("§3Launch canceled.");
            }
            else{
                ChatHandler.sendError(sender, "Any countdown are set right now.");
            }
            return true;
        }

        return false;
    }
}