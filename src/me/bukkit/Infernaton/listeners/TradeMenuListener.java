package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.OpenMenuTrade;
import me.bukkit.Infernaton.handler.InterfaceHandler;
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
    private InterfaceHandler IH;

    public TradeMenuListener(FightToSurvive main) {
        this.main = main;
        this.IH = new InterfaceHandler(main);
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity e = event.getRightClicked();

        if (event.getRightClicked() instanceof Villager){
            event.setCancelled(true);

            if (e.getName().equals("Lumber_Jack")){
                OpenMenuTrade trade = IH.tradeKey(new ItemStack(Material.LOG,10),new ItemStack(Material.COBBLESTONE, 10));
                trade.openTrade(p);
            }
        }
    }
}

