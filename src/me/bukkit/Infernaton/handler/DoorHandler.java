package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.Store.SpatialHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import static me.bukkit.Infernaton.handler.Store.SpatialHandler.worldName;

import java.util.List;

/**
 * Handle state of doors between rooms
 * 
 * @todo make static function
 */
public class DoorHandler {
    private FightToSurvive main;

    public DoorHandler(FightToSurvive context) {
        this.main = context;
    }

    /**
     * Clone the main door design on each assign coordinate
     */
    public void setAllDoors() {
        Location mainDoor = SpatialHandler.getDoorConstantCoord();
        List<Location> copiesDoorsList = ConstantHandler.getAllCopiesDoors();
        for (Location copiesDoors : copiesDoorsList) {
            for (double x = -1; x <= 1; x++) {
                for (double y = -1; y <= 1; y++) {
                    for (double z = -1; z <= 1; z++) {
                        Block mainBlock = new Location(Bukkit.getWorld(worldName), mainDoor.getBlockX() + x,
                                mainDoor.getBlockY() + y, mainDoor.getBlockZ() + z).getBlock();
                        Block copiesBlock = new Location(Bukkit.getWorld(worldName), copiesDoors.getBlockX() + x,
                                copiesDoors.getBlockY() + y, copiesDoors.getBlockZ() + z).getBlock();
                        copiesBlock.setType(mainBlock.getType());
                    }
                }
            }
        }
    }

    /**
     * Delete all existing Doors based on the assign coordinate
     */
    public void deleteAllDoors() {
        List<Location> copiesDoorsList = ConstantHandler.getAllCopiesDoors();
        for (Location copiesDoors : copiesDoorsList) {
            for (double x = -1; x <= 1; x++) {
                for (double y = -1; y <= 1; y++) {
                    for (double z = -1; z <= 1; z++) {
                        Block copiesBlock = new Location(Bukkit.getWorld(worldName), copiesDoors.getBlockX() + x,
                                copiesDoors.getBlockY() + y, copiesDoors.getBlockZ() + z).getBlock();
                        copiesBlock.setType(Material.AIR);
                    }
                }
            }
        }
    }
}
