package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.*;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.ConstantHandler;
import me.bukkit.Infernaton.handler.InterfaceHandler;
import me.bukkit.Infernaton.handler.Store.SpatialHandler;
import me.bukkit.Infernaton.handler.Store.StringHandler;

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
    private final InterfaceHandler IH;

    public PlayerListeners(FightToSurvive main) {
        this.main = main;
        this.IH = new InterfaceHandler(main);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Check if the game was entering the Final Phase (FP)
        if (!main.FP().isActive() || !main.constH().isState(GState.PLAYING))
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
        if (main.constH().isState(GState.PLAYING) && team != null) {
            event.setRespawnLocation(SpatialHandler.getBaseLocation(team));
            main.HP().giveStarterPack(player);
        } else {
            event.setRespawnLocation(SpatialHandler.getSpawnCoordinate());
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
                && item.getItemMeta().getDisplayName().equalsIgnoreCase(StringHandler.compassName())) {
            if (main.constH().isState(GState.STARTING)) {
                player.openInventory(IH.cancelStart());
            } else {
                player.openInventory(IH.selectTeam());
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
        if (inv.getName().equalsIgnoreCase(StringHandler.teamInventory())) {
            event.setCancelled(true);
            if (itemName.equals(StringHandler.blueTeamItem())) {
                main.addingTeam(ConstantHandler.getBlueTeam(), player);
                player.closeInventory();
            } else if (itemName.equals(StringHandler.redTeamItem())) {
                main.addingTeam(ConstantHandler.getRedTeam(), player);
                player.closeInventory();
            } else if (itemName.equals(StringHandler.spectatorsItem())) {
                main.addingTeam(ConstantHandler.getSpectators(), player);
                player.closeInventory();
            } else if (itemName.equals(StringHandler.launch())) {
                main.onStarting(player);
                player.closeInventory();
            } else if (itemName.equals(StringHandler.optionItem())) {
                player.openInventory(IH.optionsInventory());
            }
        }
        if (inv.getName().equalsIgnoreCase(StringHandler.optionInventory())) {
            event.setCancelled(true);
            if (itemName.equals(StringHandler.returnItem())) {
                player.openInventory(IH.selectTeam());
            }
        }
        if (inv.getName().equalsIgnoreCase(StringHandler.cancelInventory())) {
            event.setCancelled(true);
            if (itemName.equals(StringHandler.cancelItem())) {
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
            ChatHandler.sendError(p, StringHandler.cantWhilePlaying());
        }
    }
}