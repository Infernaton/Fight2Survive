package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener {

    private FightToSurvive main;

    public PlayerListeners(FightToSurvive main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if ( ((!main.getRedPlayers().contains(player) || !main.getBluePlayers().contains(player)) && !main.isState(GState.WAITING)) || main.isState(GState.WAITING)){
            HandlePlayerState.resetPlayerState(player);
            player.teleport(main.getSpawnCoordinate());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(item == null) return;

        Player player = event.getPlayer();
        Action action = event.getAction();

        if(item.getType()== Material.COMPASS && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aNavigation")){
            Inventory inv = Bukkit.createInventory(null, 27, "§7Equipe");


            inv.setItem(11, HandleItem.blueWool());
            inv.setItem(15, HandleItem.redWool());

            player.openInventory(inv);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        ItemStack current = event.getCurrentItem();
        if (current == null) return;

        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        if (inv.getName().equalsIgnoreCase("§7Equipe")){
            event.setCancelled(true);
        }
    }
}
