package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

import static me.bukkit.Infernaton.handler.SpatialHandler.worldName;

public class DoorHandler {
    private FightToSurvive main;

    public DoorHandler(FightToSurvive context){
        this.main = context;

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
