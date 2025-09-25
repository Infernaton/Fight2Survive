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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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

        // Verify if the player click on a block and if the block is a redstone block
        // + check if the player is in a Team
        if (block == null || block.getType() != Material.REDSTONE_BLOCK || !Team.hasTeam(player))
            return;

        Location location = block.getLocation();
        DoorStruct door = DoorHandler.getDoor(location, Team.getTeam(player));
        if (door.tryToOpen(player)) {
            ChatHandler.toAllPlayer(StringConfig.openDoors());
//            CustomItem.removeItemHand(player);
            FinalPhaseHandler.Instance().asking(door.isLastDoor());
        } else {
            ChatHandler.sendError(player, "You don't have enough resources to pay.");
        }
    }
}
