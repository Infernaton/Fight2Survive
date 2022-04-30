package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.OpenMenuTrade;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class TradeMenuListener implements Listener {

    private FightToSurvive main;

    public TradeMenuListener(FightToSurvive main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity e = event.getRightClicked();

        if (event.getRightClicked() instanceof Villager){
            event.setCancelled(true);

            if (e.getName().equals("Lumber_Jack")){

                OpenMenuTrade trade = new OpenMenuTrade("shop");

                trade.addTrade(new ItemStack(Material.LOG,10),new ItemStack(Material.COBBLESTONE, 10), main.HI().paperKey());

                trade.openTrade(p);
            }
        }
    }
}

