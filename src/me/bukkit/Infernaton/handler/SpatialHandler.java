package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SpatialHandler {

    private GState state;
    private final FightToSurvive main;
    static public String worldName;

    public SpatialHandler(FightToSurvive main){
        this.main = main;
        worldName = getWorldName();
    }

    public String getWorldName(){
        return main.stringH().worldName();
    }

    public Location[] getSpawnApplePoint(){
        return new Location[]{
                new Location(Bukkit.getWorld(worldName), 168.5, 61.5, 130.5),
                new Location(Bukkit.getWorld(worldName), -167.5, 61.5, 110.5)
        };
    }
}
