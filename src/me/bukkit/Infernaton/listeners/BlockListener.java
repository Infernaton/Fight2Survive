package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.clock.BreakBlockClock;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.HandlePlayerState;
import me.bukkit.Infernaton.store.Constants;
import me.bukkit.Infernaton.store.CustomItem;
import me.bukkit.Infernaton.store.StringConfig;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class BlockListener implements Listener {

    private final FightToSurvive main;

    public BlockListener(FightToSurvive main) {
        this.main = main;
    }

    /**
     * To prevent the player in game, from breaking certain block that has the same
     * name but not the same metadata
     * 
     * @param event
     */
    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.ADVENTURE)
            return;

        if (!HandlePlayerState.isPlayerInPlayableTeam(player)
                || FightToSurvive.isGameState(GState.WAITING))
            return;

        Block block = event.getBlock();
        // getData() % 4 != 0 -> if the result of getData() == 0 || 4 || 8 it means is an oak log
        if (block.getType() == Material.LOG && block.getState().getData().getData() % 4 != 0) {
            event.setCancelled(true);
            ChatHandler.sendError(player, StringConfig.avoidBreak());
            return;
        }

        Integer cd = Constants.cooldownBlock(block.getType());
        new BreakBlockClock(cd, block);
        // Give the player the given block to its inventory (replacing the block will
        // not drop the item)
        for (ItemStack itemStack : block.getDrops()) {
            CustomItem.giveItem(player, itemStack);
        }

        // Spawn manually the xp orb for ore because the "setType()" cancel it
        if (event.getExpToDrop() >= 1) {
            ExperienceOrb orb = (ExperienceOrb) block.getLocation().getWorld().spawnEntity(block.getLocation(),
                    EntityType.EXPERIENCE_ORB);
            orb.setExperience(event.getExpToDrop());
        }
        // Replace the broken block with bedrock, to prevent that player to dig through
        // the ground and getting stuck
        event.getBlock().setType(Material.BEDROCK);
    }

    /**
     * Storing containers that a player interact with
     * 
     * @todo prevent a containers to be add twice
     * @param event
     */
    @EventHandler
    public void onBlockClicked(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block b = event.getClickedBlock();

        List<Material> containers = Arrays.asList(
                Material.CHEST,
                Material.TRAPPED_CHEST,
                Material.FURNACE);

        if (player.getGameMode() == GameMode.ADVENTURE && FightToSurvive.isGameState(GState.PLAYING) && b != null
                && containers.contains(b.getType())) {
            main.BH().addContainers(b);
        }
    }
}
