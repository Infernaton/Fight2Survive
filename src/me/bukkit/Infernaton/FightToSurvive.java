package me.bukkit.Infernaton;

import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.commands.DebugCommand;
import me.bukkit.Infernaton.commands.SpawnVillager;
import me.bukkit.Infernaton.handler.ConstantHandler;
import me.bukkit.Infernaton.listeners.PlayerListeners;
import me.bukkit.Infernaton.listeners.TradeMenuListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FightToSurvive extends JavaPlugin {

    ConstantHandler constH = new ConstantHandler();

    public ConstantHandler constH(){
        return constH;
    }

    @Override
    public void onEnable(){
        constH.setState(GState.WAITING);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListeners(this), this);
        pm.registerEvents(new TradeMenuListener(),this);
        getCommand("setPlayer").setExecutor(new DebugCommand());
        getCommand("mob").setExecutor(new SpawnVillager());
        getCommand("trade").setExecutor(new SpawnVillager());
        constH.setScoreboard(getServer().getScoreboardManager().getMainScoreboard());

        new Team("Red", constH.getScoreboard()).setTeamColor(ChatColor.RED);
        new Team("Blue", constH.getScoreboard()).setTeamColor(ChatColor.BLUE);
        new Team("Spectators", constH.getScoreboard()).setTeamColor(ChatColor.GRAY);
    }

    @Override
    public void onDisable(){ }
}
