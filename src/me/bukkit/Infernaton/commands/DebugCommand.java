package me.bukkit.Infernaton.commands;

import com.google.gson.JsonArray;
import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.DoorHandler;
import me.bukkit.Infernaton.store.CustomItem;
import me.bukkit.Infernaton.store.Mobs;
import me.bukkit.Infernaton.store.StringConfig;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class DebugCommand implements CommandExecutor, TabCompleter {

    private final FightToSurvive main;

    public DebugCommand() {
        this.main = FightToSurvive.Instance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp() && !cmd.getName().equalsIgnoreCase("debug"))
            return false;

        switch (args[0]) {
            case "set":
                switch (args[1]) {
                    case "player":
                        /*
                         * On debug, make target player like he is waiting to play the game
                         */
                        if (args.length == 3)
                            setPlayer(sender, args[2]);
                        else if (args.length < 3)
                            setPlayer(sender);
                        else {
                            ChatHandler.sendError(sender, StringConfig.tooManyArgument());
                            ChatHandler.sendCorrectUsage(sender, StringConfig.correctUsage(cmd.getName(), "<username>"));
                        }
                        return true;
                    case "doors":
                        if (FightToSurvive.isGameState(GState.WAITING)) {
                            ChatHandler.sendInfoMessage(sender, StringConfig.getDoors());
                            DoorHandler.setAllDoors();
                        } else {
                            ChatHandler.sendCantWhilePlaying(sender);
                        }
                        return true;
                    case "villagers":
                        if (FightToSurvive.isGameState(GState.WAITING)) {
                            ChatHandler.sendInfoMessage(sender, StringConfig.setVill());
                            Mobs.setAllPnj();
                        } else {
                            ChatHandler.sendCantWhilePlaying(sender);
                        }
                        return true;
                    default:
                        return false;
                }
            case "get":
                switch (args[1]) {
                    case "key":
                        ChatHandler.sendInfoMessage(sender, StringConfig.giveKey());
                        CustomItem.giveItem((Player) sender, CustomItem.paperKey());
                        return true;
                    default:
                        return false;
                }
            case "remove":
                switch (args[1]) {
                    case "doors":
                        if (FightToSurvive.isGameState(GState.WAITING)) {
                            ChatHandler.sendInfoMessage(sender, StringConfig.delDoors());
                            DoorHandler.deleteAllDoors();
                        } else {
                            ChatHandler.sendCantWhilePlaying(sender);
                        }
                        return true;
                    case "villagers":
                        ChatHandler.sendInfoMessage(sender, StringConfig.killVill());
                        Mobs.killPnj();
                        return true;
                    default:
                        return false;
                }
            case "print":
                ChatHandler.sendInfoMessage(sender, "Nothing to debug ...");
                return true;
            default:
                return false;
        }
    }

    private void setPlayer(CommandSender sender) {
        Player player = (Player) sender;
        main.HP().setPlayer(player);
        ChatHandler.sendMessage(sender, StringConfig.setPlayer("You", "are"));
    }
    private void setPlayer(CommandSender sender, String playerName) {
        Player targetPlayer = Bukkit.getPlayerExact(playerName);
        if (targetPlayer == null) {
            ChatHandler.sendError(sender, StringConfig.notPlayer());
        } else {
            main.HP().setPlayer(targetPlayer);
            ChatHandler.sendMessage(sender, StringConfig.setPlayer(playerName, "is"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        Map<String, Map<String, List<String>>> mapping = new HashMap<>();

        Map<String, List<String>> setMapping = new HashMap<>();
        setMapping.put("doors", Collections.emptyList());
        setMapping.put("player", null);
        setMapping.put("villagers", Collections.emptyList());
        mapping.put("set", setMapping);

        Map<String, List<String>> getMapping = new HashMap<>();
        getMapping.put("key", Collections.emptyList());
        mapping.put("get", getMapping);

        Map<String, List<String>> deleteMapping = new HashMap<>();
        deleteMapping.put("doors", Collections.emptyList());
        deleteMapping.put("villagers", Collections.emptyList());
        mapping.put("remove", deleteMapping);

        mapping.put("print", null);

        if (args.length == 1) {
            return new ArrayList<>(mapping.keySet());
        } else if(args.length >= 2) {
            Map<String, List<String>> map = mapping.get(args[0]);
            if(args.length == 3)
                return map.get(args[1]);
            else if (map != null)
                return new ArrayList<>(map.keySet());
        }
        return Collections.emptyList();
    }
}