package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.OpenMenuTrade;
import org.bukkit.Material;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class TradeMenuListener implements Listener {
    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        if (event.getRightClicked() instanceof Villager){
            event.setCancelled(true);
            OpenMenuTrade trade = new OpenMenuTrade("shop");
            trade.addTrade(new ItemStack(org.bukkit.Material.COBBLESTONE, 10),new ItemStack(org.bukkit.Material.WOOL, 30));
            trade.addTrade(new ItemStack(org.bukkit.Material.STICK, 2),new ItemStack(org.bukkit.Material.WOOD, 3), new ItemStack(Material.WOOD_AXE, 1));
            trade.openTrade(p);
        }
    }
}
