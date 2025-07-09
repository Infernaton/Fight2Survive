package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.BreakBlockClock;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.ConstantHandler;
import me.bukkit.Infernaton.handler.Store.StringHandler;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
        Block block = event.getBlock();
        if (block.getType() == Material.LOG && block.getState().getData().getData() != 0) {
            event.setCancelled(true);
            ChatHandler.sendError(player, StringHandler.avoidBreak());
            return;
        }
        Integer cd = ConstantHandler.coolDownBlock().get(block.getType());
        BreakBlockClock.newCountDown(main, cd != null ? cd : 10, block);
    }

    /**
     * Storing containers that a player interract with
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

        if (player.getGameMode() == GameMode.ADVENTURE && main.constH().isState(GState.PLAYING) && b != null
                && containers.contains(b.getType())) {
            main.BH().addContainers(b);
        }
    }
}
