package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.DoorStruct;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.store.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import static me.bukkit.Infernaton.store.CoordStorage.worldName;

import java.util.HashMap;
import java.util.Map;

/**
 * Handle state of doors between rooms
 */
public class DoorHandler {

    private static Map<String, DoorStruct> redDoorsList;
    private static Map<String, DoorStruct> blueDoorsList;

    public static void setAllDoors() {
        redDoorsList = new HashMap<>();
        String redPath = "teamData.red.doors";
        redDoorsList = setAllTeamDoors(redPath, redDoorsList);

        blueDoorsList = new HashMap<>();
        String bluePath = "teamData.blue.doors";
        blueDoorsList = setAllTeamDoors(bluePath, blueDoorsList);
    }

    /**
     * Get All doors coordinates by team and generate a door
     */
    private static Map<String, DoorStruct> setAllTeamDoors(String teamkey, Map<String, DoorStruct> doorsList) {
        FileConfiguration conf =  FightToSurvive.GetConfig();
        for (String key : conf.getConfigurationSection(teamkey).getKeys(false)) {
            String currentPath = teamkey + "." + key;
            Location origin = new Location(Bukkit.getWorld(worldName),
                    conf.getDouble( currentPath + ".x"),
                    conf.getDouble(currentPath + ".y"),
                    conf.getDouble(currentPath + ".z"),
                    (float) conf.getDouble(currentPath + ".facing"),
                    0.0f);

            String name = conf.getString(currentPath + ".name");
            ItemStack item1 = conf.getItemStack(currentPath + ".cost.1");
            ItemStack item2 = conf.getItemStack(currentPath + ".cost.2", new ItemStack(Material.AIR));

            boolean isLastDoor = conf.getBoolean(currentPath + ".metadata.isLastDoor", false);
            DoorStruct door = new DoorStruct(origin, name, item1, item2, isLastDoor);
            //get block location to remove potential yaw or pitch involve
            doorsList.put(origin.getBlock().getLocation().toString(), door);
        }
        return doorsList;
    }

    /**
     * Delete all existing Doors based on the assign coordinate
     */
    public static void deleteAllDoors() {
        for (DoorStruct door : redDoorsList.values()) {
            door.open();
        }

        for (DoorStruct door : blueDoorsList.values()) {
            door.open();
        }
    }

    public static DoorStruct getDoor(Location loc, Team team) {
        if (team.equals(Constants.getSpectators())) return null;
        Map<String, DoorStruct> doorList = team.equals(Constants.getRedTeam()) ? redDoorsList : blueDoorsList;
        return doorList.get(loc.toString());
    }

    public static DoorStruct getNearbyDoor(Location loc, Team team) {
        if (team.equals(Constants.getSpectators())) return null;
        Map<String, DoorStruct> doorList = team.equals(Constants.getRedTeam()) ? redDoorsList : blueDoorsList;
        for (DoorStruct door : doorList.values()) {
            if (door.origin.distance(loc) <= 3) {
                return door;
            }
        }

        // No door was nearby the current player
        return null;
    }
}
