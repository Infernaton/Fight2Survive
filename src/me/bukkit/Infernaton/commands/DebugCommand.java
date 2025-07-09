package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.Store.StringHandler;

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
        if (!sender.isOp())
            return false;
        /*
         * On debug, make target player like he is waiting to play the game
         */
        if (cmd.getName().equalsIgnoreCase("setPlayer")) {
            setPlayer(sender, cmd, label, args);
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("getDoors")) {
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, StringHandler.getDoors());
                main.DH().setAllDoors();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("deleteDoors")) {
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, StringHandler.delDoors());
                main.DH().deleteAllDoors();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("getKey")) {
            ChatHandler.sendInfoMessage(sender, StringHandler.giveKey());
            main.HI().giveItemInInventory((Player) sender, main.HI().paperKey(), 1);
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("setVillagers")) {
            if (main.constH().isState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, StringHandler.setVill());
                main.MH().setAllPnj();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("killPnj")) {
            ChatHandler.sendInfoMessage(sender, StringHandler.killVill());
            main.MH().killPnj();
            return true;
        }

        return false;
    }

    private void setPlayer(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;
            main.HP().setPlayer(player);
            ChatHandler.sendMessage(sender, StringHandler.setPlayer("You", "are"));
        } else if (args.length == 1) {
            Player targetPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetPlayer == null) {
                ChatHandler.sendError(sender, StringHandler.notPlayer());
            } else {
                main.HP().setPlayer(targetPlayer);
                ChatHandler.sendMessage(sender, StringHandler.setPlayer(args[0], "is"));
            }
        } else {
            ChatHandler.sendError(sender, StringHandler.tooManyArgument());
            ChatHandler.sendCorrectUsage(sender, StringHandler.correctUsage(cmd.getName(), "<username>"));
        }
    }
}