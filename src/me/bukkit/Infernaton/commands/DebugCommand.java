package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.CountDown;
import me.bukkit.Infernaton.builder.DayNightCycle;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.FinalPhaseHandler;
import me.bukkit.Infernaton.handler.MOB;
import me.bukkit.Infernaton.listeners.DoorListeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static me.bukkit.Infernaton.handler.ConstantHandler.worldName;

public class DebugCommand implements CommandExecutor {

    private final FightToSurvive main;
    private final DoorListeners setDoors;

    public DebugCommand(FightToSurvive main) {
        this.main = main;
        setDoors = new DoorListeners(this.main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setPlayer")) {
            if (args.length == 0) {
                ChatHandler.sendCorrectUsage(sender, "Usage: /setPlayer <username>");
            } else if (args.length == 1) {
                Player targetPlayer = Bukkit.getPlayerExact(args[0]);
                System.out.println(targetPlayer);
                if (targetPlayer == null) {
                    ChatHandler.sendError(sender, "Player not found");
                } else {
                    main.HP().setPlayer(targetPlayer);
                    ChatHandler.sendMessage(sender, "§r" + args[0] + " §fis ready to play !");
                }
            } else {
                ChatHandler.sendError(sender, "Too many argument");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("mob_villager")) {
            Location location1 = new Location(Bukkit.getWorld(worldName), -48.500, 56, 48);
            Location location2 = new Location(Bukkit.getWorld(worldName), 55.500, 56, 48);
            MOB.createVillager(location1,"Lumber_Jack");
            MOB.createVillager(location2,"Lumber_Jack");
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("start")) {
            if (main.constH().isState(GState.WAITING)) {
                List<Player> redPlayers = main.constH().getRedTeam().getPlayers();
                List<Player> bluePlayers = main.constH().getBlueTeam().getPlayers();

                if (Bukkit.getScheduler().getPendingTasks().size() > 0) {
                    ChatHandler.sendError(sender, "CountDown already launch .");
                }
                //Compare if there the same numbers of players in each team
                else if (redPlayers.size() == bluePlayers.size() && redPlayers.size() != 0) {
                    //Clear all players that attend to play
                    redPlayers.addAll(bluePlayers); //All players in one variable
                    for(Player player: redPlayers){
                        main.HP().clear(player);
                    }
                    main.constH().setState(GState.STARTING);

                    ChatHandler.sendInfoMessage(sender, "Initialize the countdown...");
                    CountDown.newCountDown(main, 10L);
                    setDoors.setAllDoors();
                } else {
                    ChatHandler.sendError(sender, "Not enough players.");
                }
            } else {
                ChatHandler.sendError(sender, "Party already started !");

            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("cancelStart")) {
            if (main.constH().isState(GState.STARTING)) {
                main.constH().setState(GState.WAITING);
                CountDown.stopAllCountdown(main);
                ChatHandler.sendMessageListPlayer(main.constH().getAllTeamsPlayer(), "Launch canceled.");
            } else {
                ChatHandler.sendError(sender, "Any countdown aren't set right now.");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("reset")){
            if (main.constH().isState(GState.PLAYING)){
                List<Player> players = main.constH().getAllTeamsPlayer();
                ChatHandler.sendMessageListPlayer(players, "Canceling the game");

                for (Player player: players) {
                    main.HP().setPlayer(player);
                }
                main.constH().setState(GState.WAITING);
                setDoors.setAllDoors();
            } else {
                ChatHandler.sendError(sender, "Any game is playing right now.");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("manage_time")) {
            final Player player = (Player) sender;
            DayNightCycle day = new DayNightCycle(main);
        }

        else if (cmd.getName().equalsIgnoreCase("getDoors")) {
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, "Reset all doors...");

                setDoors.setAllDoors();
            } else {
                ChatHandler.sendError(sender, "Can't perform this command while the game is pending.");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("deleteDoors")) {
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, "Deleting all doors...");
                DoorListeners setDoors = new DoorListeners(this.main);
                setDoors.deleteAllDoors();
            } else {
                ChatHandler.sendError(sender, "Can't perform this command while the game is pending.");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("forceFinal")){
            if(main.constH().isState(GState.PLAYING) || main.constH().isState(GState.WAITING)){
                new FinalPhaseHandler(main).on();
            }
            else {
                ChatHandler.sendError(sender, "Can't perform this command while any game is started");
            }
            return true;
        }

        return false;
    }
}