package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.DoorStruct;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.DoorHandler;
import me.bukkit.Infernaton.handler.FinalPhaseHandler;
import me.bukkit.Infernaton.store.StringConfig;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoorListeners implements Listener {

    final FightToSurvive main;

    public DoorListeners(FightToSurvive main) {
        this.main = main;
    }

    /**
     * When a player click a redstone block (which represent a key hole on a door), it will open that door if it has all the required ressources
     *
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onInteractKeys(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        // Verify if the player click on a block and if the block is a redstone block
        // + check if the player is in a Team
        if (block == null || block.getType() != Material.REDSTONE_BLOCK || !Team.hasTeam(player))
            return;

        Location location = block.getLocation();
        interactDoor(location, player);
    }

    /**
     * Whenever a player click on a armor stand that is near a redstone block, it means they try to open its associated door
     * Better to use PlayerInteractEntityEvent, but it doesn't fire while clicking on armor stand
     * @param event PlayerInteractAtEntityEvent
     */
    @EventHandler
    public void onInteractDoorEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity.getType() != EntityType.ARMOR_STAND && !Team.hasTeam(player))
            return;

        Location location = entity.getLocation();
        interactDoor(location, player);
    }

    private void interactDoor(Location nearLocation, Player player) {
        DoorStruct door = DoorHandler.getNearbyDoor(nearLocation, Team.getTeam(player));
        if (door == null)
            return;

        if (door.tryToOpen(player)) {
            ChatHandler.toAllPlayer(StringConfig.openDoors());
            FinalPhaseHandler.Instance().asking(door.isLastDoor());
        } else {
            ChatHandler.sendError(player, "You don't have enough resources to pay.");
        }
    }
}
