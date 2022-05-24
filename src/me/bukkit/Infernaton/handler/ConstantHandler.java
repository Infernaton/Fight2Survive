package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.World;
import org.bukkit.block.Block;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.material.Directional;
import org.bukkit.scoreboard.Scoreboard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        this.redBase = new Location(Bukkit.getWorld(worldName), main.getConfig().getDouble("coordinates.teamRed.spawnpoint.x"),  main.getConfig().getDouble("coordinates.teamRed.spawnpoint.y"),  main.getConfig().getDouble("coordinates.teamRed.spawnpoint.z"), 0f, 0f);
        this.blueBase = new Location(Bukkit.getWorld(worldName), main.getConfig().getDouble("coordinates.teamBlue.spawnpoint.x"),  main.getConfig().getDouble("coordinates.teamBlue.spawnpoint.y"),  main.getConfig().getDouble("coordinates.teamBlue.spawnpoint.z"), 0f, 0f);
        //System.out.println(spawn);
    }

    public Map<String, Location> getAllPnj() {
        Map<String, Location> pc = new HashMap<String, Location>();
        World w = Bukkit.getWorld(worldName);
        pc.put("Lumber Jack", new Location(w, -48.5, 56.0, 51.5));
        pc.put("Didier", new Location(w, -117.0, 56.0, 134.0, 50.0F, 0f));
        pc.put("Jean-Pierre Fanguin", new Location(w, -157.0, 56.0, 104.0));
        pc.put("Rodrigues de pomero", new Location(w, -103.0, 56.0, 161.0));
        pc.put("Baruk le diamantiare", new Location(w, -41.0, 56.0, 181.0));
        pc.put("Fabala l'enchanteur", new Location(w, -49.0, 44.0, 239.0));

        pc.put("Lumber Jack", new Location(w, 55.5, 56.0, 51.5));
        pc.put("Didier", new Location(w, 118.0, 56.0, 134.0));
        pc.put("Jean-Pierre Fanguin", new Location(w, 158.0, 56.0, 136.0));
        pc.put("Rodrigues de pomero", new Location(w, 104.0, 56.0, 161.0));
        pc.put("Baruk le diamantiare", new Location(w, 42.0, 56.0, 181.0));
        pc.put("Fabala l'enchanteur", new Location(w, 54.0, 44.0, 239.0));

        return pc;
    }

    public void setAllPnj() {
        Map<String, Location> copiesPnjList = main.constH().getAllPnj();
        for (Map.Entry<String, Location> me : copiesPnjList.entrySet()) {
            Location loc = me.getValue();
            loc.setYaw(loc.getYaw());
            MobsHandler.createVillager(loc, me.getKey());
            ChatHandler.broadcast(me.getValue().getYaw() + " "+ me.getKey());
        }
    }

    public void killPnj(){
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (e instanceof Villager) {
                e.remove();
            }
        }
    }


    //Get all block around target location (mostly use around players) by radius
    public List<Block> sphereAround(Location location, int radius) {
        List<Block> sphere = new ArrayList<>();
        Block center = location.getBlock();
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    Block b = center.getRelative(x, y, z);
                    if(center.getLocation().distance(b.getLocation()) <= radius) {
                        sphere.add(b);
                    }
                }
            }
        }
        return sphere;
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
    public Location getCurrentCoordPnj(Team currentTeam, int currentIndex){
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
    public Team getSpectators(){
        return Team.getTeamByName("Spectators");
    }

    public List<Player> getAllTeamsPlayer(){
        List<Player> allPlayers = this.getBlueTeam().getPlayers();
        allPlayers.addAll(this.getRedTeam().getPlayers());
        return allPlayers;
    }
    public List<Player> getAllPlayers(){
        List<Player> allPlayers = this.getBlueTeam().getPlayers();
        allPlayers.addAll(this.getRedTeam().getPlayers());
        allPlayers.addAll(this.getSpectators().getPlayers());
        return allPlayers;
    }

    public Location getBaseLocation(Team team) {
        switch (team.getTeamName()){
            case "Red":
                return getRedBase();
            case "Blue":
                return getBlueBase();
            default:
                return getSpawnCoordinate();
        }
    }

    /**
     * Loop all blocks with the copiesDoors list and mainDoor locations
     *  Cloning mainDoor blocks type with the mainDoor location
     *  to the copiesDoors list.
     */
    public void setAllDoors() {
        Location mainDoor = main.constH().getDoorConstantCoord();
        List<Location> copiesDoorsList = main.constH().getAllCopiesDoors();
        for (Location copiesDoors : copiesDoorsList) {
            for (double x = -1; x <= 1; x++) {
                for (double y = -1; y <= 1; y++) {
                    for (double z = -1; z <= 1; z++) {
                        Block mainBlock = new Location(Bukkit.getWorld(worldName), mainDoor.getBlockX() + x, mainDoor.getBlockY() + y, mainDoor.getBlockZ() + z).getBlock();
                        Block copiesBlock = new Location(Bukkit.getWorld(worldName), copiesDoors.getBlockX() + x, copiesDoors.getBlockY() + y, copiesDoors.getBlockZ() + z).getBlock();
                        copiesBlock.setType(mainBlock.getType());
                    }
                }
            }
        }
    }
    /**
     * Loop all blocks with the copiesDoors list locations
     * Change blocks type to AIR block
     */
    public void deleteAllDoors() {
        List<Location> copiesDoorsList = main.constH().getAllCopiesDoors();
        for (Location copiesDoors : copiesDoorsList) {
            for (double x = -1; x <= 1; x++) {
                for (double y = -1; y <= 1; y++) {
                    for (double z = -1; z <= 1; z++) {
                        Block copiesBlock = new Location(Bukkit.getWorld(worldName), copiesDoors.getBlockX() + x, copiesDoors.getBlockY() + y, copiesDoors.getBlockZ() + z).getBlock();
                        copiesBlock.setType(Material.AIR);
                    }
                }
            }
        }
    }
}
