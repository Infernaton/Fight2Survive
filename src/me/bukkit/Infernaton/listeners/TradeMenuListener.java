package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.OpenMenuTrade;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.InterfaceHandler;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityVillager;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;

public class TradeMenuListener implements Listener {

    private FightToSurvive main;
    private InterfaceHandler IH;
    private OpenMenuTrade currentTrade;

    public TradeMenuListener(FightToSurvive main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity e = event.getRightClicked();

        if (e instanceof Villager && !e.getName().equals("Villager")){
            ((EntityVillager) e).a_((EntityHuman) p);
            //event.setCancelled(true);
            //currentTrade = main.constH().getTrade(e.getName());
            //currentTrade.openTrade(p);
        }
    }
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event){
        Player p = (Player) event.getPlayer();
        Inventory inv = event.getInventory();
        if (inv.getType().name().equals("MERCHANT")){
            MerchantInventory merch = (MerchantInventory) inv;
            //ChatHandler.broadcast(merch.getName() + "");
        }
    }
}

