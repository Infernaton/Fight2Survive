package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.*;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.ConstantHandler;
import me.bukkit.Infernaton.handler.store.CoordStorage;
import me.bukkit.Infernaton.handler.store.InterfaceMenu;
import me.bukkit.Infernaton.handler.store.StringConfig;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener {

    private final FightToSurvive main;

    public PlayerListeners(FightToSurvive main) {
        this.main = main;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Check if the game was entering the Final Phase (FP)
        if (!main.FP().isActive() || !FightToSurvive.isGameState(GState.PLAYING))
            return;

        Team team = Team.getTeam(player);
        ConstantHandler.getSpectators().add(player);
        if (team.getPlayers().isEmpty()) {
            main.finish();
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Team team = Team.getTeam(player);

        // Check if the player is in a team to respawn him to the right place
        if (FightToSurvive.isGameState(GState.PLAYING) && team != null) {
            event.setRespawnLocation(CoordStorage.getBaseLocation(team));
            main.HP().giveStarterPack(player);
        } else {
            event.setRespawnLocation(CoordStorage.getSpawnCoordinate());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null)
            return;

        Player player = event.getPlayer();

        // If the player clicked on a specified Compass, which is given when he spawn
        if (item.getType() == Material.COMPASS && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName().equalsIgnoreCase(StringConfig.compassName())) {
            if (FightToSurvive.isGameState(GState.STARTING)) {
                player.openInventory(InterfaceMenu.cancelStart());
            } else {
                player.openInventory(InterfaceMenu.selectTeam());
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack current = event.getCurrentItem();

        // If the player click outside the inventory or in a slot where there's nothing,
        // we don't need the function to be executed
        if (current == null || current.getType() == Material.AIR)
            return;

        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        String itemName = current.getItemMeta().getDisplayName();

        // Action on the inventory of the compass, given when joining the server
        if (inv.getName().equalsIgnoreCase(StringConfig.teamInventory())) {
            event.setCancelled(true);
            if (itemName.equals(StringConfig.blueTeamItem())) {
                main.addingTeam(ConstantHandler.getBlueTeam(), player);
                player.closeInventory();
            } else if (itemName.equals(StringConfig.redTeamItem())) {
                main.addingTeam(ConstantHandler.getRedTeam(), player);
                player.closeInventory();
            } else if (itemName.equals(StringConfig.spectatorsItem())) {
                main.addingTeam(ConstantHandler.getSpectators(), player);
                player.closeInventory();
            } else if (itemName.equals(StringConfig.launch())) {
                main.onStarting(player);
                player.closeInventory();
            } else if (itemName.equals(StringConfig.optionItem())) {
                player.openInventory(InterfaceMenu.optionsInventory());
            }
        }
        if (inv.getName().equalsIgnoreCase(StringConfig.optionInventory())) {
            event.setCancelled(true);
            if (itemName.equals(StringConfig.returnItem())) {
                player.openInventory(InterfaceMenu.selectTeam());
            }
        }
        if (inv.getName().equalsIgnoreCase(StringConfig.cancelInventory())) {
            event.setCancelled(true);
            if (itemName.equals(StringConfig.cancelItem())) {
                main.cancelStart();
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        Team playerTeam = Team.getTeam(p);
        String colorName;
        if (playerTeam != null) {
            colorName = playerTeam.getTeamColor();
        } else {
            colorName = "§r";
        }
        ChatHandler.broadcast(colorName + p.getDisplayName() + "§r: " + e.getMessage());
    }

    /**
     * Prevent the player from placing boat when clicking on a block
     * Because of that, player can bypass Door
     * 
     * @param event
     */
    @EventHandler
    public void onBoatPlace(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Player p = event.getPlayer();
        if (p.getItemInHand().getType() == Material.BOAT) {
            event.setCancelled(true);
            ChatHandler.sendError(p, StringConfig.cantWhilePlaying());
        }
    }
}