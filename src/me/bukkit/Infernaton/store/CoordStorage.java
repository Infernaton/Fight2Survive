package me.bukkit.Infernaton.store;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.Team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CoordStorage {

    public final static String worldName = StringConfig.worldName();

    // #region Coordinate
    public static Location[] getSpawnApplePoint() {
        return new Location[] {
                new Location(Bukkit.getWorld(worldName), 168.5, 61.5, 130.5),
                new Location(Bukkit.getWorld(worldName), -167.5, 61.5, 110.5)
        };
    }

    public static List<Location> getAllPnjLocation() {
        List<Location> pc = new ArrayList<>();
        World w = Bukkit.getWorld(worldName);
        pc.add(new Location(w, -48.5, 56.0, 51.5)); // 1er zone (bois)
        pc.add(new Location(w, -117.0, 56.0, 134.0, -135f, 0f)); // 2e zone (charbon)
        // pc.add(new Location(w, -157.0, 56.0, 104.0, 0f, 0f)); //Constant PNJ (or)
        pc.add(new Location(w, -159.5, 56.0, 105.5)); // 3e zone (or)
        pc.add(new Location(w, -103.0, 56.0, 161.0, 180f, 0f)); // 4e zone (fer)
        pc.add(new Location(w, -41.0, 56.0, 181.0, 60f, 0f)); // 5e zone (diamant)
        pc.add(new Location(w, -49.5, 44.0, 239.5, 120f, 0f)); // 6e zone (lapis)

        pc.add(new Location(w, 55.5, 56.0, 51.5)); // 1er zone (bois)
        pc.add(new Location(w, 118.0, 56.0, 134.0, 135f, 0f)); // 2e zone (charbon)
        // pc.add(new Location(w, 158.0, 56.0, 136.0, -180f, 0f)); //Constant PNJ (or)
        pc.add(new Location(w, 161.5, 56.0, 134.5)); // 3e zone (or)
        pc.add(new Location(w, 104.0, 56.0, 161.0, 180f, 0f)); // 4e zone (fer)
        pc.add(new Location(w, 42.0, 56.0, 181.0, -60f, 0f)); // 5e zone (diamant)
        pc.add(new Location(w, 54.5, 44.0, 239.5, 120f, 0f)); // 6e zone (lapis)

        return pc;
    }

    public static Location getDoorConstantCoord() {
        return new Location(Bukkit.getWorld(worldName),
                FightToSurvive.GetConfig().getDouble("coordinates.doorCoord.constant.x"),
                FightToSurvive.GetConfig().getDouble("coordinates.doorCoord.constant.y"),
                FightToSurvive.GetConfig().getDouble("coordinates.doorCoord.constant.z"));
    }

    /**
     * Get the coordinate of the spawn point of target Team (Red or Blue)
     * 
     * @param team to target
     * @return the Location
     */
    public static Location getBaseLocation(Team team) {
        switch (team.getTeamName()) {
            case "Red":
                return getRedBase();
            case "Blue":
                return getBlueBase();
            default:
                return getSpawnCoordinate();
        }
    }

    public static Location getSpawnCoordinate() {
        return new Location(Bukkit.getWorld(worldName),
                FightToSurvive.GetConfig().getDouble("coordinates.lobby.x"),
                FightToSurvive.GetConfig().getDouble("coordinates.lobby.y"),
                FightToSurvive.GetConfig().getDouble("coordinates.lobby.z"));
    }

    public static Location getRedBase() {
        /*
         * return new Location(Bukkit.getWorld(worldName),
         * FightToSurvive.GetConfig().getDouble("coordinates.teamRed.spawnpoint.x"),
         * FightToSurvive.GetConfig().getDouble("coordinates.teamRed.spawnpoint.y"),
         * FightToSurvive.GetConfig().getDouble("coordinates.teamRed.spawnpoint.z")
         * );
         */
        return new Location(Bukkit.getWorld(worldName), -36.5, 56, 83.5, 135f, 0f);
    }

    public static Location getBlueBase() {
        /*
         * return new Location(Bukkit.getWorld(worldName),
         * FightToSurvive.GetConfig().getDouble("coordinates.teamBlue.spawnpoint.x"),
         * FightToSurvive.GetConfig().getDouble("coordinates.teamBlue.spawnpoint.y"),
         * FightToSurvive.GetConfig().getDouble("coordinates.teamBlue.spawnpoint.z")
         * );
         */
        return new Location(Bukkit.getWorld(worldName), 37.5, 56.0, 83.5, -135f, 0f);
    }
    // #endregion

    // Get all block around target location (mostly use around players) by radius
    public static List<Block> sphereAround(Location location, int radius) {
        List<Block> sphere = new ArrayList<>();
        Block center = location.getBlock();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = center.getRelative(x, y, z);
                    if (center.getLocation().distance(b.getLocation()) <= radius) {
                        sphere.add(b);
                    }
                }
            }
        }
        return sphere;
    }
}
