package me.bukkit.Infernaton;

import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.commands.DebugCommand;
import me.bukkit.Infernaton.commands.SpawnVillager;
import me.bukkit.Infernaton.handler.ConstantHandler;
import me.bukkit.Infernaton.listeners.BlockListener;
import me.bukkit.Infernaton.listeners.PlayerListeners;
import me.bukkit.Infernaton.listeners.TradeMenuListener;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FightToSurvive extends JavaPlugin {

    ConstantHandler constH = new ConstantHandler();

    public ConstantHandler constH(){
        return constH;
    }

    public void enableCommand(String[] commandsName, CommandExecutor executor){
        for(String command: commandsName){
            getCommand(command).setExecutor(executor);
        }
    }

    @Override
    public void onEnable(){
        constH.setState(GState.WAITING);
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerListeners(this), this);
        pm.registerEvents(new TradeMenuListener(),this);
        pm.registerEvents(new BlockListener(this), this);

        String[] debugCommand = {"setPlayer", "start", "cancelStart"};
        enableCommand(debugCommand, new DebugCommand(this));

        String[] spawnCommand = {"mob_villager", "trade"};
        enableCommand(spawnCommand, new SpawnVillager());
        constH.setScoreboard(getServer().getScoreboardManager().getMainScoreboard());

        new Team("Red", constH.getScoreboard()).setTeamColor(ChatColor.RED);
        new Team("Blue", constH.getScoreboard()).setTeamColor(ChatColor.BLUE);
        new Team("Spectators", constH.getScoreboard()).setTeamColor(ChatColor.GRAY);
    }

    @Override
    public void onDisable(){ }
}
