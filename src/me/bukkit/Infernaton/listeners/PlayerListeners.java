package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.*;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.InterfaceHandler;
import me.bukkit.Infernaton.handler.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class PlayerListeners implements Listener {

    private final FightToSurvive main;
    private final InterfaceHandler IH;

    public PlayerListeners(FightToSurvive main) {
        this.main = main;
        this.IH = new InterfaceHandler(main);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        //Check if the game was entering the Final Phase (FP)
        if (main.FP().isActive() && main.constH().isState(GState.PLAYING)) {
            Team team = Team.getTeam(player);
            main.constH().getSpectators().add(player);
            if (team.getPlayers().isEmpty()) {
                main.finish();
            }
        }
    }

    @EventHandler
    public void onRespawn (PlayerRespawnEvent event){
        Player player = event.getPlayer();
        Team team = Team.getTeam(player);

        //Check if the player is in a team to respawn him to the right place
        if (main.constH().isState(GState.PLAYING) && team != null) {
            event.setRespawnLocation(main.constH().getBaseLocation(team));
            main.HP().giveStarterPack(player);
        } else {
            event.setRespawnLocation(main.constH().getSpawnCoordinate());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;

        Player player = event.getPlayer();

        //If the player clicked on a specified Compass, which is given when he spawn
        if (item.getType() == Material.COMPASS && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName().equalsIgnoreCase(main.stringH().compassName())) {
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

        //If the player click outside the inventory or in a slot where there's nothing, we don't need the function to be executed
        if (current == null || current.getType() == Material.AIR) return;

        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        String itemName = current.getItemMeta().getDisplayName();

        //Action on the inventory of the compass, given when joining the server
        if (inv.getName().equalsIgnoreCase(main.stringH().teamInventory())) {
            event.setCancelled(true);
            if (itemName.equals(main.stringH().blueTeamItem())){
                main.addingTeam(main.constH().getBlueTeam(), player);
                player.closeInventory();
            }else if (itemName.equals(main.stringH().redTeamItem())){
                main.addingTeam(main.constH().getRedTeam(), player);
                player.closeInventory();
            }else if (itemName.equals(main.stringH().spectatorsItem())){
                main.addingTeam(main.constH().getSpectators(), player);
                player.closeInventory();
            }else if (itemName.equals(main.stringH().launch())){
                main.onStarting(player);
                player.closeInventory();
            }else if (itemName.equals(main.stringH().optionItem())){
                player.openInventory(IH.optionsInventory());
            }
        }
        if (inv.getName().equalsIgnoreCase(main.stringH().optionInventory())) {
            event.setCancelled(true);
            if (itemName.equals(main.stringH().returnItem())){
                player.openInventory(IH.selectTeam());
            }
        }
        if (inv.getName().equalsIgnoreCase(main.stringH().cancelInventory())) {
            event.setCancelled(true);
            if (itemName.equals(main.stringH().cancelItem())){
                main.cancelStart();
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent e){
        e.setCancelled(true);
        Player p = e.getPlayer();
        Team playerTeam = Team.getTeam(p);
        String colorName;
        if (playerTeam != null){
            colorName = playerTeam.getTeamColor();
        }else {
            colorName = "§r";
        }
        ChatHandler.broadcast(colorName + p.getDisplayName() + "§r: " + e.getMessage());
    }

    /**
     * Prevent the player from placing boat when clicking on a block
     * Because of that, player can bypass Door
     * @param event
     */
    @EventHandler
    public void onBoatPlace (PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player p = event.getPlayer();
            if (p.getItemInHand().getType() == Material.BOAT) {
                event.setCancelled(true);
                ChatHandler.sendError(p, main.stringH().cantWhilePlaying());
            }
        }
    }
}