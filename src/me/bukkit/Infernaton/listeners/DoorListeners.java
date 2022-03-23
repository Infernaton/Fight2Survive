package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.HandleItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.bukkit.Infernaton.handler.ConstantHandler.worldName;

public class DoorListeners implements Listener {

    final FightToSurvive main;
    public List<ItemStack> keys = new ArrayList<>();

    public DoorListeners(FightToSurvive main) {
        this.main = main;
        keys.add(main.HI().paperKey());
    }



    /**
     * Can interact to selected block with selected item
     * Loop all door blocks and change blocks type to AIR block
     * @param event
     */
    @EventHandler
    public void onInteractKeys(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        ItemStack it = event.getItem();
        for (ItemStack itemStack : keys) {
            if (it != null && it.getType() == itemStack.getType() && block != null) {
                Location location = block.getLocation();
                if (block != null && block.getType() == Material.REDSTONE_BLOCK) {
                    ChatHandler.sendMessage(player, "La porte s'ouvre...");
                    for (double x = -1; x <= 1; x++) {
                        for (double y = -1; y <= 1; y++) {
                            for (double z = -1; z <= 1; z++) {
                                Block bc = new Location(player.getWorld(), location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z).getBlock();
                                bc.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Loop all blocks with the copiesDoors list and mainDoor locations
     *  Clonning mainDoor blocks type with the mainDoor location
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