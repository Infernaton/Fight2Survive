package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.Clock;
import me.bukkit.Infernaton.handler.ChatHandler;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {

    private FightToSurvive main;
    
    public BlockListener(FightToSurvive main) {
        this.main = main;
    }

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
            Clock.newCountDown(main, 10, block);
        }
    }
}
