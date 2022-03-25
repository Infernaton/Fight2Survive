package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.CountDown;
import me.bukkit.Infernaton.builder.DayNightCycle;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.MobsHandler;
import me.bukkit.Infernaton.listeners.DoorListeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            }
            else if (args.length == 1) {
                Player targetPlayer = Bukkit.getPlayerExact(args[0]);
                System.out.println(targetPlayer);
                if (targetPlayer == null) {
                    ChatHandler.sendError(sender, "Player not found");
                } else {
                    main.HP().setPlayer(targetPlayer);
                    ChatHandler.sendMessage(sender, "§r" + args[0] + " §fis ready to play !");
                }
            }
            else {
                ChatHandler.sendError(sender, "Too many argument");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("mob_villager")) {
            Location location1 = main.constH().getCurrentCoordPnj(Team.getTeamByName("Red"), 0);//new Location(Bukkit.getWorld(worldName), -48.500, 56, 50);
            Location location2 = main.constH().getCurrentCoordPnj(Team.getTeamByName("Blue"), 0);//new Location(Bukkit.getWorld(worldName), 55.500, 56, 50);
            if(location1 != null) MobsHandler.createVillager(location1,"Lumber_Jack");
            if(location2 != null) MobsHandler.createVillager(location2,"Lumber_Jack");


            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("start")) {
            if (sender instanceof Player) main.onStarting((Player) sender);
            else ChatHandler.sendError(sender, "No Starting from the console");
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
                main.cancel();
            } else {
                ChatHandler.sendError(sender, "Any game is playing right now.");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("manage_time")) {
            new DayNightCycle(main);
        }

        else if (cmd.getName().equalsIgnoreCase("getDoors")) {
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, "Reset all doors...");

                main.constH().setAllDoors();
            } else {
                ChatHandler.sendError(sender, "Can't perform this command while the game is pending.");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("deleteDoors")) {
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, "Deleting all doors...");
                DoorListeners setDoors = new DoorListeners(this.main);
                main.constH().deleteAllDoors();
            } else {
                ChatHandler.sendError(sender, "Can't perform this command while the game is pending.");
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
        /*
        else if (cmd.getName().equalsIgnoreCase("mob_zombie")){
            if(main.constH().isState(GState.PLAYING) && main.constH().isState(GStateDayNight.NIGHT)){

            }
        }*/

        return false;
    }
}