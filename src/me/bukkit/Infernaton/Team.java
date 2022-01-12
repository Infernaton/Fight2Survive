package me.bukkit.Infernaton;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team{

    private static List<Team> allTeams = new ArrayList<>();

    private static HashMap<Player, Team> playerTeams = new HashMap<>();

    private Scoreboard scb = FightToSurvive.getScoreboard();

    private final String teamName;

    public Team(String teamName){
        this.teamName = teamName;
        allTeams.add(this);
        if (scb.getTeam(teamName) == null) {
            scb.registerNewTeam(teamName);
        }
    }

    public String getTeamName(){
        return teamName;
    }

    public void add(Player player){
        playerTeams.put(player, this);
        scb.getTeam(this.getTeamName()).addEntry(player.getName());
    }

    public void remove(Player player){
        if(hasTeam(player))
            playerTeams.remove(player);
    }

    public static boolean hasTeam(Player player){
        return playerTeams.containsKey(player);
    }

    public static Team getTeam(Player player){
        if (!hasTeam(player)) return null;

        return playerTeams.get(player);
    }
    public static Team getTeamByName(String name){
        for (Team t : allTeams){
            if (t.teamName.equalsIgnoreCase(name)) return t;
        }
        return null;
    }
    public static List<Team> getAllTeams(){
        return allTeams;
    }
    public void setTeamColor(ChatColor color){
        if (scb.getTeam(this.teamName) != null) {
            scb.getTeam(this.teamName).setPrefix(color.toString());
        }
    }
}