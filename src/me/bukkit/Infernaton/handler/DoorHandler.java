package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.DoorStruct;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.store.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Location;

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
            Location origin = new Location(Bukkit.getWorld(worldName),
                    conf.getDouble(teamkey + "." + key + ".x"),
                    conf.getDouble(teamkey + "." + key + ".y"),
                    conf.getDouble(teamkey + "." + key + ".z"));

            ItemStack item1 = conf.getItemStack(teamkey + "." + key + ".cost.1");
            // handle item2

            boolean isLastDoor = conf.getBoolean(teamkey + "." + key + ".metadata.isLastDoor", false);
            String isLastDoorString = conf.getString(teamkey + "." + key + ".metadata.isLastDoor", "test");
            DoorStruct door = new DoorStruct(origin, item1, isLastDoor);
            doorsList.put(origin.toString(), door);
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
}
