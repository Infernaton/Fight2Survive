package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which regroup each variable we need in our project
 */
public class ConstantHandler {

    private GState state;
    private final FightToSurvive main;
    private final Location spawn;
    private final Location blueBase;
    private final Location redBase;
    private final Location constantDoor;
    private Scoreboard scoreboard;
    final static public String worldName = "Arene";

    public ConstantHandler(FightToSurvive main){
        this.main = main;
        this.spawn = new Location(Bukkit.getWorld(worldName), main.getConfig().getDouble("coordinates.lobby.x"),  main.getConfig().getDouble("coordinates.lobby.y"),  main.getConfig().getDouble("coordinates.lobby.z"), 0f, 0f);
        this.constantDoor = new Location(Bukkit.getWorld(worldName), main.getConfig().getDouble("coordinates.doorCoord.constant.x"),  main.getConfig().getDouble("coordinates.doorCoord.constant.y"),  main.getConfig().getDouble("coordinates.doorCoord.constant.z"), 0f, 0f);
        this.redBase = new Location(Bukkit.getWorld(worldName), main.getConfig().getDouble("coordinates.teamRed.spawnpoint.x"),  main.getConfig().getDouble("coordinates.teamRed.y"),  main.getConfig().getDouble("coordinates.teamRed.z"), 0f, 0f);
        this.blueBase = new Location(Bukkit.getWorld(worldName), main.getConfig().getDouble("coordinates.teamBlue.spawnpoint.x"),  main.getConfig().getDouble("coordinates.teamBlue.y"),  main.getConfig().getDouble("coordinates.teamBlue.z"), 0f, 0f);
        System.out.println(spawn);
    }

    public List<Location> getAllCopiesDoors() {
        List<Location> locations = new ArrayList<>();
        String path = "coordinates.doorCoord.copies";
        for (String key : main.getConfig().getConfigurationSection(path).getKeys(false)) {
            Location door = new Location(Bukkit.getWorld(worldName),
                    main.getConfig().getDouble(path + "." + key + ".x"),
                    main.getConfig().getDouble(path + "." + key + ".y"),
                    main.getConfig().getDouble(path + "." + key + ".z")
            );
            locations.add(door);
        }
        return locations;
    }
    public Location getCurrentCoordPnj(Team currentTeam,int currentIndex){
        String path = "coordinates.team"+currentTeam.getTeamName()+".pnjCoord."+ (currentIndex+1);
        ChatHandler.broadcast(path);
        ConfigurationSection configurationSection = main.getConfig().getConfigurationSection(path);
        if (configurationSection == null) return null;

        return new Location(Bukkit.getWorld(worldName),
                main.getConfig().getDouble(path + ".x"),
                main.getConfig().getDouble(path + ".y"),
                main.getConfig().getDouble(path + ".z")
        );
    }

    public void setState(GState state){
        this.state = state;
    }
    public boolean isState(GState state){
        return this.state == state;
    }
    public GState getState(){
        return this.state;
    }
    public Location getSpawnCoordinate(){
        return spawn;
    }
    public Location getRedBase(){
        return redBase;
    }
    public Location getBlueBase(){
        return blueBase;
    }
    public Location getDoorConstantCoord(){
        return this.constantDoor;
    }
    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }
    public void setScoreboard(Scoreboard scoreboard){
        this.scoreboard = scoreboard;
    }

    public Team getRedTeam(){
        return Team.getTeamByName("Red");
    }
    public Team getBlueTeam(){
        return Team.getTeamByName("Blue");
    }

    public List<Player> getAllTeamsPlayer(){
        List<Player> allPlayers = this.getBlueTeam().getPlayers();
        allPlayers.addAll(this.getRedTeam().getPlayers());
        return allPlayers;
    }

    public Team getSpectators(){
        return Team.getTeamByName("Spectators");
    }
}
