package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

/**
 * Class which regroup each variable we need in our project
 */
public class ConstantHandler {

    private GState state;
    private FightToSurvive main;
    private Location spawn;
    private Location blueBase;
    private Location redBase;
    private Scoreboard scoreboard;
    private Location constantDoor;

    public ConstantHandler(FightToSurvive main){
        this.main = main;
        this.spawn = new Location(Bukkit.getWorld("Arene"), main.getConfig().getDouble("coordinates.lobby.x"),  main.getConfig().getDouble("coordinates.lobby.y"),  main.getConfig().getDouble("coordinates.lobby.z"), 0f, 0f);
        this.constantDoor = new Location(Bukkit.getWorld("Arene"), main.getConfig().getDouble("coordinates.doorCoord.constant.x"),  main.getConfig().getDouble("coordinates.doorCoord.constant.y"),  main.getConfig().getDouble("coordinates.doorCoord.constant.z"), 0f, 0f);
        this.redBase = new Location(Bukkit.getWorld("Arene"), main.getConfig().getDouble("coordinates.teamRed.x"),  main.getConfig().getDouble("coordinates.teamRed.y"),  main.getConfig().getDouble("coordinates.teamRed.z"), 0f, 0f);
        this.blueBase = new Location(Bukkit.getWorld("Arene"), main.getConfig().getDouble("coordinates.teamBlue.x"),  main.getConfig().getDouble("coordinates.teamBlue.y"),  main.getConfig().getDouble("coordinates.teamBlue.z"), 0f, 0f);
        System.out.println(spawn);
    }

    public void setState(GState state){
        this.state = state;
    }
    public boolean isState(GState state){
        return this.state == state;
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