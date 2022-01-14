package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.CountDown;
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
                Player targetPlayer = Bukkit.getPlayerExact(args[0]);
                System.out.println(targetPlayer);
                if(targetPlayer == null){
                    ChatHandler.sendError(sender, "Player not found");
                }else{
                    main.HP().setPlayer(targetPlayer);
                    ChatHandler.sendMessage(sender, "§r" +args[0] + " §fis ready to play !");
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
                else if (redPlayers.size() == bluePlayers.size() && redPlayers.size() != 0) {
                    //Clear all players that attend to play
                    for(Player player: main.constH().getBlueTeam().getPlayers()){
                        main.HP().clear(player);
                    }
                    for (Player player : main.constH().getRedTeam().getPlayers()){
                        main.HP().clear(player);
                    }

                    main.constH().setState(GState.STARTING);
                    ChatHandler.broadcast("Initialize the countdown...");
                    CountDown.newCountDown(main, 10L);
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
                CountDown.stopAllCountdown(main);
                ChatHandler.broadcast("§3Launch canceled.");
            }
            else{
                ChatHandler.sendError(sender, "Any countdown aren't set right now.");
            }
            return true;
        }

        return false;
    }
}