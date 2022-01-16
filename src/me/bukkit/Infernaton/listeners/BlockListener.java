package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.Clock;
import org.bukkit.GameMode;
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
        if (player.getGameMode() != GameMode.CREATIVE){
            Block block = event.getBlock();
            Clock.newCountDown(main, 10, block);
        }
    }
}
