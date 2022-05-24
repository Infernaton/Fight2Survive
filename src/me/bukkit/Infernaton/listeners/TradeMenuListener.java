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
    private OpenMenuTrade trade;

    public TradeMenuListener(FightToSurvive main) {
        this.main = main;
        this.IH = new InterfaceHandler(main);
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity e = event.getRightClicked();

        if (e instanceof Villager){
            event.setCancelled(true);
            switch (e.getName()){
                case "Lumber Jack":
                    trade = IH.tradeKey(new ItemStack(Material.LOG,10),new ItemStack(Material.COBBLESTONE, 10));
                    break;
                case "Didier":
                    trade = IH.tradeKey(new ItemStack(Material.COAL_BLOCK,3));
                    break;
                case "Jean-Pierre Fanguin":
                    trade = IH.tradeKey(new ItemStack(Material.IRON_BLOCK,4));
                    break;
                case "Rodrigues de pomero":
                    trade = IH.tradeKey(new ItemStack(Material.GOLD_NUGGET,50));
                    break;
                /*case "André de pomero":
                    trade = IH.tradeKey(new ItemStack(Material.,));
                    trade.openTrade(p);
                    break;*/
                case "Baruk le diamantiare":
                    trade = IH.tradeKey(new ItemStack(Material.DIAMOND,6),new ItemStack(Material.COAL, 12));
                    break;
                case "Fabala l'enchanteur":
                    trade = IH.tradeKey(new ItemStack(Material.LAPIS_BLOCK,6),new ItemStack(Material.GOLD_SWORD, 1));
                    break;
                default:
                    trade = new OpenMenuTrade("DEFAULT").addTrade(new ItemStack(Material.AIR), new ItemStack(Material.AIR));
                    break;
            }
            trade.openTrade(p);
        }
    }
}

