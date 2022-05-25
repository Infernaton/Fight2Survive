package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.*;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.InterfaceHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class PlayerListeners implements Listener {

    private FightToSurvive main;
    private InterfaceHandler IH;

    public PlayerListeners(FightToSurvive main) {
        this.main = main;
        this.IH = new InterfaceHandler(main);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //If it's the first time he join, the player don't have a team yet, so we forced him to join one
        if (!Team.hasTeam(player)) {
            main.constH().getSpectators().add(player);
        }

        //We test if the player is currently in game when he join,
        // if the game crashed client side, it would be a shame if he can't rejoin the party
        boolean isCurrentlyIG = !main.constH().isState(GState.WAITING) &&
                !Team.getTeam(player).getTeamName().equalsIgnoreCase("Spectators");

        //And, if the player is in creative, we don't need to reset is position
        if (!isCurrentlyIG && player.getGameMode() != GameMode.CREATIVE) {
            main.HP().resetPlayerState(player);
            player.teleport(main.constH().getSpawnCoordinate());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;

        Player player = event.getPlayer();
        Action action = event.getAction();

        //If the player clicked on a specified Compass, which is given when he spawn
        if (item.getType() == Material.COMPASS && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aNavigation")) {
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

        //Action on the inventory of the compass, given when joining the server
        if (inv.getName().equalsIgnoreCase("§7Equipe")) {
            event.setCancelled(true);
            ItemMeta currentMeta = current.getItemMeta();
            switch (currentMeta.getDisplayName()) {
                case "§1Equipe Bleu":
                    main.constH().getBlueTeam().add(player);
                    break;
                case "§4Equipe Rouge":
                    main.constH().getRedTeam().add(player);
                    break;
                case "§7Spectateur":
                    main.constH().getSpectators().add(player);
                    break;
                case "§2StartGame":
                    main.onStarting(player);
                    break;
                case "§fOptions":
                    player.openInventory(IH.optionsInventory());
                    break;
                default:
                    break;
            }
        }
        if (inv.getName().equalsIgnoreCase("§7Options")) {
            event.setCancelled(true);
            ItemMeta currentMeta = current.getItemMeta();
            switch (currentMeta.getDisplayName()) {
                case "§fRetour":
                    player.openInventory(IH.selectTeam());
                    break;
                default:
                    break;
            }
        }
        if (inv.getName().equalsIgnoreCase("§7Cancel Start")) {
            event.setCancelled(true);
            ItemMeta currentMeta = current.getItemMeta();
            switch (currentMeta.getDisplayName()) {
                case "§4CancelGame":
                    main.cancelStart();
                    break;
                default:
                    break;
            }
        }
    }

        /**
         * If a player quit the server, the event will trigger
         * in case a player quit when a party is running, we will, after some time, force him to retire from the game
         * If the player rejoin the server before the countdown stop, he will still be part of the game
         * @param event
         */
        @EventHandler
        public void onQuit ( final PlayerQuitEvent event){
            if (main.constH().isState(GState.PLAYING)) {
                final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

                final Runnable runnable = new Runnable() {
                    int countdownStarter = 10;

                    @Override
                    public void run() {
                        Player player = event.getPlayer();

                        countdownStarter--;
                        if (countdownStarter == 0) {
                            scheduler.shutdown();

                            //test if the players is really offline before kicking him of the current party
                            if (!Bukkit.getOnlinePlayers().contains(player) && main.constH().isState(GState.PLAYING)) {
                                Team team = Team.getTeam(player);
                                main.constH().getSpectators().add(player);

                                if (team.getPlayers().isEmpty()) {
                                    ChatHandler.toAllPlayer("Not enough player remaining, resetting the game");
                                    main.finish();
                                }
                            }
                        }
                    }
                };
                scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
            }
        }

        @EventHandler
        public void onDeath (PlayerDeathEvent event){
            Player player = event.getEntity();

            //Check if the game was entering the Final Phase (FP)
            if (main.FP().isActive() && main.constH().isState(GState.PLAYING)) {
                Team team = Team.getTeam(player);
                main.constH().getSpectators().add(player);
                if (team.getPlayers().isEmpty()) {
                    ChatHandler.toAllPlayer("Partie Terminée");
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
            } else {
                event.setRespawnLocation(main.constH().getSpawnCoordinate());
            }
        }

        /**
         * Prevent the player from placing boat when clicking on a block
         * Because of that, player can bypass Door
         * @param event
         */
        @EventHandler
        public void onBoatPlace (PlayerInteractEvent event){
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getPlayer().getItemInHand().getType() == Material.BOAT) {
                    event.setCancelled(true);
                }
            }
        }
    }