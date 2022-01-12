package me.bukkit.Infernaton;

import me.bukkit.Infernaton.listeners.PlayerListeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class FightToSurvive extends JavaPlugin {

    private GState state;
    private List<Player> redPlayers = new ArrayList<>();
    private List<Player> bluePlayers = new ArrayList<>();
    private final Location spawn = new Location(Bukkit.getWorld("Arene"), 0.5, 57, 1.5, 0f, 0f);

    public void setState(GState state){
        this.state = state;
    }
    public boolean isState(GState state){
        return this.state == state;
    }

    public List<Player> getRedPlayers(){
        return redPlayers;
    }
    public List<Player> getBluePlayers(){
        return bluePlayers;
    }

    public Location getSpawnCoordinate(){
        return spawn;
    }

    @Override
    public void onEnable(){
        setState(GState.WAITING);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListeners(this), this);
    }

    @Override
    public void onDisable(){ }
}
