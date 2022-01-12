package me.bukkit.Infernaton;

import me.bukkit.Infernaton.listeners.PlayerListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class FightToSurvive extends JavaPlugin {

    private GState state;
    private final Location spawn = new Location(Bukkit.getWorld("Arene"), 0.5, 57, 1.5, 0f, 0f);
    private static Scoreboard scoreboard;

    public void setState(GState state){
        this.state = state;
    }
    public boolean isState(GState state){
        return this.state == state;
    }

    public Location getSpawnCoordinate(){
        return spawn;
    }

    public static Scoreboard getScoreboard(){
        return scoreboard;
    }

    public Team getRedTeam(){
        return Team.getTeamByName("Red");
    }
    public Team getBlueTeam(){
        return Team.getTeamByName("Blue");
    }
    public Team getSpectators(){
        return Team.getTeamByName("Spectators");
    }

    @Override
    public void onEnable(){
        setState(GState.WAITING);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListeners(this), this);

        scoreboard = getServer().getScoreboardManager().getMainScoreboard();

        new Team("Red").setTeamColor(ChatColor.RED);
        new Team("Blue").setTeamColor(ChatColor.BLUE);
        new Team("Spectators").setTeamColor(ChatColor.GRAY);
    }

    @Override
    public void onDisable(){ }
}
