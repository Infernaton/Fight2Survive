package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {

    private final FightToSurvive main;

    public DebugCommand(FightToSurvive main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        /*
            On debug, make target player like he is waiting to play the game
         */
        if (cmd.getName().equalsIgnoreCase("setPlayer")) {
            if (args.length == 0) {
                Player player = (Player) sender;
                main.HP().setPlayer(player);
                ChatHandler.sendMessage(sender, "§rYou §fare ready to play !");
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
                ChatHandler.sendCorrectUsage(sender, "Usage: /setPlayer <username>");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("getDoors")) {
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, "Reset all doors...");

                main.DH().setAllDoors();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("deleteDoors")) {
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, "Deleting all doors...");
                main.DH().deleteAllDoors();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }


        else if (cmd.getName().equalsIgnoreCase("getKey")){
            ChatHandler.sendInfoMessage(sender, "Giving the Key !");
            main.HI().giveItemInInventory((Player) sender, main.HI().paperKey(), 1);
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("setVillagers")){
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, "Spawning all Villagers...");
                main.MH().setAllPnj();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("killPnj")){
            ChatHandler.sendInfoMessage(sender, "Killing all Villagers...");
            main.MH().killPnj();
            return true;
        }

        return false;
    }
}