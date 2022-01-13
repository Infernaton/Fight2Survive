package me.bukkit.Infernaton.commands;


import me.bukkit.Infernaton.OpenMenuTrade;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

public class SpawnVillager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (cmd.getName().equalsIgnoreCase("mob_wood") && sender instanceof Player) {
            Player player = (Player) sender;
            Location location = new Location(player.getWorld(), 85.500, 56, 175.500);
            Villager lumberjack = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
            lumberjack.setCustomName("Lumber_Jack");
            lumberjack.setCustomNameVisible(true);
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("mob_iron") && sender instanceof Player) {
            Player player = (Player) sender;
            Location location = new Location(player.getWorld(), 85.926, 56, 177.561);
            Villager black_smith = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
            black_smith.setCustomName("Black_Smith");
            black_smith.setCustomNameVisible(true);
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("mob_potion") && sender instanceof Player) {
            Player player = (Player) sender;
            Location location = new Location(player.getWorld(), 85.926, 56, 179.561);
            Villager black_smith = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
            black_smith.setCustomName("Wizardo_Carlos");
            black_smith.setCustomNameVisible(true);
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