package me.bukkit.Infernaton.commands;


import me.bukkit.Infernaton.OpenMenuTrade;
import me.bukkit.Infernaton.handler.MOB;
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


        if (cmd.getName().equalsIgnoreCase("mob_villager") && sender instanceof Player) {
            Player player = (Player) sender;
            Location location1 = new Location(player.getWorld(), -48.500, 56, 50);
            MOB.createVillager(location1,"Lumber_Jack");
            Location location2 = new Location(player.getWorld(), -47.500, 56, 50);
            MOB.createVillager(location2,"Black_Smith");
            Location location3 = new Location(player.getWorld(), -46.500, 56, 50);
            MOB.createVillager(location3,"Wizardo_Carlos");
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