package me.bukkit.Infernaton;

import me.bukkit.Infernaton.commands.DebugCommand;
import me.bukkit.Infernaton.listeners.PlayerListeners;
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
        getCommand("setPlayer").setExecutor(new DebugCommand());

        constH.setScoreboard(getServer().getScoreboardManager().getMainScoreboard());

        new Team("Red").setTeamColor(ChatColor.RED);
        new Team("Blue").setTeamColor(ChatColor.BLUE);
        new Team("Spectators").setTeamColor(ChatColor.GRAY);
    }

    @Override
    public void onDisable(){ }
}
