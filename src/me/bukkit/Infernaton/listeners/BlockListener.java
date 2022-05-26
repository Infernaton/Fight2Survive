package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.BreakBlockClock;
import me.bukkit.Infernaton.handler.BlockHandler;
import me.bukkit.Infernaton.handler.ChatHandler;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockListener implements Listener {

    private final FightToSurvive main;
    
    public BlockListener(FightToSurvive main) {
        this.main = main;
    }

    /**
     * To prevent the player in game, from breaking certain block that has the same name but not the same metadata
     * @param event
     */
    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.ADVENTURE){
            Block block = event.getBlock();
            if (block.getType() == Material.LOG){
                if (block.getState().getData().getData() != 0){
                    event.setCancelled(true);
                    ChatHandler.sendError(player, "Can't break this block");
                    return;
                }
            }
            BreakBlockClock.newCountDown(main, 10, block);
        }
    }

    @EventHandler
    public void onBlockClicked(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        List<Material> containers = Arrays.asList(
                Material.CHEST,
                Material.TRAPPED_CHEST,
                Material.FURNACE
        );

        if (player.getGameMode() == GameMode.ADVENTURE && main.constH().isState(GState.PLAYING)) {
            Block b = event.getClickedBlock();
            if (containers.contains(b.getType())){
                main.BH().addContainers(b);
            }
        }
    }
}
