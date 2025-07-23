package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.DoorHandler;
import me.bukkit.Infernaton.store.CustomItem;
import me.bukkit.Infernaton.store.StringConfig;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {

    private final FightToSurvive main;

    public DebugCommand() {
        this.main = FightToSurvive.Instance();
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
            if (FightToSurvive.isGameState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, StringConfig.getDoors());
                DoorHandler.setAllDoors();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("deleteDoors")) {
            if (FightToSurvive.isGameState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, StringConfig.delDoors());
                DoorHandler.deleteAllDoors();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("getKey")) {
            ChatHandler.sendInfoMessage(sender, StringConfig.giveKey());
            CustomItem.giveItem((Player) sender, CustomItem.paperKey());
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("printDebug")) {
            ChatHandler.sendInfoMessage(sender, "Nothing to debug ...");
            return true;
        }

        return false;
    }

    private void setPlayer(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;
            main.HP().setPlayer(player);
            ChatHandler.sendMessage(sender, StringConfig.setPlayer("You", "are"));
        } else if (args.length == 1) {
            Player targetPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetPlayer == null) {
                ChatHandler.sendError(sender, StringConfig.notPlayer());
            } else {
                main.HP().setPlayer(targetPlayer);
                ChatHandler.sendMessage(sender, StringConfig.setPlayer(args[0], "is"));
            }
        } else {
            ChatHandler.sendError(sender, StringConfig.tooManyArgument());
            ChatHandler.sendCorrectUsage(sender, StringConfig.correctUsage(cmd.getName(), "<username>"));
        }
    }
}