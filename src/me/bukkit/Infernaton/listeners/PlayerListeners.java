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
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListeners implements Listener {

    private FightToSurvive main;

    public PlayerListeners(FightToSurvive main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (!Team.hasTeam(player)){
            player.sendMessage("Hello " + Team.hasTeam(player));
            main.getSpectators().add(player);
        }
        if (main.isState(GState.WAITING)){
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
            inv.setItem(22, HandleItem.spectatorWool());

            player.openInventory(inv);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        ItemStack current = event.getCurrentItem();

        if (current.getType() == Material.AIR || current == null) return;
        else
            System.out.println(current);

        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        if (inv.getName().equalsIgnoreCase("§7Equipe")){
            event.setCancelled(true);
            ItemMeta currentMeta = current.getItemMeta();
            switch (currentMeta.getDisplayName()){
                case "§1Equipe Bleu":
                    main.getBlueTeam().add(player);
                    player.sendMessage(Team.getTeam(player).getTeamName());
                    break;
                case "§4Equipe Rouge":
                    main.getRedTeam().add(player);
                    player.sendMessage(Team.getTeam(player).getTeamName());
                    break;
                case "§7Spectateur":
                    main.getSpectators().add(player);
                    player.sendMessage(Team.getTeam(player).getTeamName());
                    break;
                default:
                    break;
            }
        }
    }
}