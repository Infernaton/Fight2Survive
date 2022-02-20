package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class DoorListeners implements Listener {

    final FightToSurvive main;

    public DoorListeners(FightToSurvive main) {
        this.main = main;
    }

    public List<Material> keys = Arrays.asList(
            Material.DIAMOND_SWORD,
            Material.GOLD_SWORD
    );

    /**
     * Can interact to selected block with selected item
     * @param event
     */
    @EventHandler
    public void onInteractKeys(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        ItemStack it = event.getItem();
        /**
         * Checking if the player use a material which is in the keys List
         * Checking if that interacted block is a redstone
         * loop to get the redstone coord (x, y and z)
         * Destroy the redstone and blocks near to it
         */
        for (Material material : keys) {
            if (it != null && it.getType() == material) {
                Location location = event.getClickedBlock().getLocation();
                if (block != null && block.getType() == Material.REDSTONE_BLOCK) {
                    player.sendMessage("La porte est ouverte");

                    for (double x = -1; x <= 1; x++) {
                        for (double y = -1; y <= 1; y++) {
                            for (double z = -1; z <= 1; z++) {
                                Block bc = new Location(player.getWorld(), location.getBlockX()+x, location.getBlockY()+y, location.getBlockZ()+z).getBlock();
                                bc.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }
    }
}