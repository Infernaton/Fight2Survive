package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.World;
import org.bukkit.block.Block;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
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
        this.redBase = new Location(Bukkit.getWorld(worldName), main.getConfig().getDouble("coordinates.teamRed.spawnpoint.x"),  main.getConfig().getDouble("coordinates.teamRed.spawnpoint.y"),  main.getConfig().getDouble("coordinates.teamRed.spawnpoint.z"), 0f, 0f);
        this.blueBase = new Location(Bukkit.getWorld(worldName), main.getConfig().getDouble("coordinates.teamBlue.spawnpoint.x"),  main.getConfig().getDouble("coordinates.teamBlue.spawnpoint.y"),  main.getConfig().getDouble("coordinates.teamBlue.spawnpoint.z"), 0f, 0f);
        //System.out.println(spawn);
    }

    public String[] pnjName(){
        return new String[]{
                "Lumber Jack", "Didier", "Rodrigues de Pomero", "André de Pomero", "Jean-Pierre Fanguin", "Baruk, le diamantaire", "Fabala l'enchanteur", //Team Blue
                "Lumber Jack", "Didier", "Rodrigues de Pomero", "André de Pomero", "Jean-Pierre Fanguin", "Baruk, le diamantaire", "Fabala l'enchanteur"  //Team Red
        };
    }

    public List<Location> getAllPnj() {
        List<Location> pc = new ArrayList<>();
        World w = Bukkit.getWorld(worldName);
        pc.add(new Location(w, -48.5, 56.0, 51.5)); //1er zone (bois)
        pc.add(new Location(w, -117.0, 56.0, 134.0, -135f, 0f)); //2e zone (charbon)
        pc.add(new Location(w, -157.0, 56.0, 104.0, 0f, 0f)); //3e zone (or)
        pc.add(new Location(w, -159.5, 56.0, 105.5)); //Constant PNJ (or)
        pc.add(new Location(w, -103.0, 56.0, 161.0, 180f, 0f)); //4e zone (fer)
        pc.add(new Location(w, -41.0, 56.0, 181.0, 60f, 0f)); //5e zone (diamant)
        pc.add(new Location(w, -49.5, 44.0, 239.5, 120f, 0f)); //6e zone (lapis)

        pc.add(new Location(w, 55.5, 56.0, 51.5)); //1er zone (bois)
        pc.add(new Location(w, 118.0, 56.0, 134.0, 135f,0f)); //2e zone (charbon)
        pc.add(new Location(w, 158.0, 56.0, 136.0, -180f, 0f)); //3e zone (or)
        pc.add(new Location(w, 161.5, 56.0, 134.5)); //Constant PNJ (or)
        pc.add(new Location(w, 104.0, 56.0, 161.0, 180f, 0f)); //4e zone (fer)
        pc.add(new Location(w, 42.0, 56.0, 181.0, -60f, 0f)); //5e zone (diamant)
        pc.add(new Location(w, 54.5, 44.0, 239.5, 120f, 0f)); //6e zone (lapis)

        return pc;
    }

    public void setAllPnj() {
        List<Location> copiesPnjList = main.constH().getAllPnj();
        for (int i=0; i<copiesPnjList.size(); i++){
            main.MobsHandler().createVillager(copiesPnjList.get(i), pnjName()[i]);
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
