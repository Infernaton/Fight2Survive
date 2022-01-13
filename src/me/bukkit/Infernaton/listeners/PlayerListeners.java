package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.*;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.HandleItem;
import me.bukkit.Infernaton.handler.HandlePlayerState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
            main.constH().getSpectators().add(player);
        }
        boolean isCurrentlyIG = !main.constH().isState(GState.WAITING) &&
                !Team.getTeam(player).getTeamName().equalsIgnoreCase("Spectator");
        if (!isCurrentlyIG && player.getGameMode() != GameMode.CREATIVE){
            HandlePlayerState.resetPlayerState(player);
            player.teleport(main.constH().getSpawnCoordinate());
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

        if (current == null || current.getType() == Material.AIR) return;

        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        if (inv.getName().equalsIgnoreCase("§7Equipe")){
            event.setCancelled(true);
            ItemMeta currentMeta = current.getItemMeta();
            switch (currentMeta.getDisplayName()){
                case "§1Equipe Bleu":
                    main.constH().getBlueTeam().add(player);
                    break;
                case "§4Equipe Rouge":
                    main.constH().getRedTeam().add(player);
                    break;
                case "§7Spectateur":
                    main.constH().getSpectators().add(player);
                    break;
                default:
                    break;
            }
        }
    }
}