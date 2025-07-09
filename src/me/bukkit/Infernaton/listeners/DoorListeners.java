package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.FinalPhaseHandler;
import me.bukkit.Infernaton.store.Constants;
import me.bukkit.Infernaton.store.CustomItem;
import me.bukkit.Infernaton.store.StringConfig;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class DoorListeners implements Listener {

    final FightToSurvive main;

    public DoorListeners(FightToSurvive main) {
        this.main = main;
    }

    /**
     * When the player click with the key item in game, on a redstone block (which
     * represent the key hole of the doors),
     * it will open that door
     *
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onInteractKeys(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        ItemStack it = event.getItem();

        // Verify if the player click with an item on a block
        if (it == null || it.getItemMeta() == null || block == null)
            return;

        // Check if the item is the key of a doors, and the clicked block is a redstone
        // block
        if (!CustomItem.comparor(it, CustomItem.paperKey())
                || block.getType() != Material.REDSTONE_BLOCK)
            return;

        // to open the door, we just replace each block of it by air block
        Location location = block.getLocation();
        for (double x = -1; x <= 1; x++) {
            for (double y = -1; y <= 1; y++) {
                for (double z = -1; z <= 1; z++) {
                    Block bc = new Location(player.getWorld(), location.getBlockX() + x, location.getBlockY() + y,
                            location.getBlockZ() + z).getBlock();
                    bc.setType(Material.AIR);
                }
            }
        }
        ChatHandler.toAllPlayer(StringConfig.openDoors());
        CustomItem.removeItemHand(player);
        FinalPhaseHandler.Instance().asking(location, Constants.getAllCopiesDoors());
    }
}
