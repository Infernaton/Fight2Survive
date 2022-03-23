package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.*;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.HandlePlayerState;
import me.bukkit.Infernaton.handler.InterfaceHandler;
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

    private InterfaceHandler IH;

    public PlayerListeners(FightToSurvive main) {
        this.main = main;
        this.IH = new InterfaceHandler(main);
    }

    @EventHandler
     public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        //If it's the first time he join, the player don't have a team yet, so we forced him to join one
        if (!Team.hasTeam(player)){
            main.constH().getSpectators().add(player);
        }

        //We test if the player is currently in game when he join,
        // if the game crashed client side, it would be a shame if he can't rejoin the party
        boolean isCurrentlyIG = !main.constH().isState(GState.WAITING) &&
                !Team.getTeam(player).getTeamName().equalsIgnoreCase("Spectator");

        //And, if the player is in creative, we don't need to reset is position
        if (!isCurrentlyIG && player.getGameMode() != GameMode.CREATIVE){
            main.HP().resetPlayerState(player);
            player.teleport(main.constH().getSpawnCoordinate());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(item == null) return;

        Player player = event.getPlayer();
        Action action = event.getAction();

        //If the player clicked on a specified Compass, which is given we he spawn
        if(item.getType()== Material.COMPASS && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aNavigation")){
            player.openInventory(IH.selectTeam());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        ItemStack current = event.getCurrentItem();

        //If the player click outside the inventory or in a slot where there's nothing, we don't need the function to be executed
        if (current == null || current.getType() == Material.AIR) return;

        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        //Action on the inventory of the compass, given when joining the server
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

    /*
      Prevent the player from placing boat
      Because of would that permit player to bypass Door
     */
    @EventHandler
    public void onBoatPlace(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getPlayer().getItemInHand().getType() == Material.BOAT) {
                event.setCancelled(true);
            }
        }
    }
}