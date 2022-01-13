package me.bukkit.Infernaton.commands;


import me.bukkit.Infernaton.OpenMenuTrade;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnVillager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (cmd.getName().equalsIgnoreCase("mob") && sender instanceof Player) {

            Player player = (Player) sender;
            Location location = new Location(player.getWorld(), 85.976, 56, 177.573);

            Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
            villager.setCustomName("Passe Partout");
            villager.setCustomNameVisible(true);

            return true;
        }

        if (cmd.getName().equalsIgnoreCase("trade") && sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                OpenMenuTrade trade = new OpenMenuTrade("shop");
                trade.addTrade(new ItemStack(Material.COBBLESTONE, 10), new ItemStack(Material.WOOL, 30));
                trade.addTrade(new ItemStack(Material.STICK, 2), new ItemStack(Material.WOOD, 3), new ItemStack(Material.WOOD_AXE, 1));
                trade.openTrade(p);
            }
        }
        return false;


    }

}