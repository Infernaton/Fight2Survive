package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.handler.mobsHandler;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class SpawnVillager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("mob_villager") && sender instanceof Player) {
            Player player = (Player) sender;
            Location location1 = new Location(player.getWorld(), -48.500, 56, 50);
            Location location2 = new Location(player.getWorld(), 55.500, 56, 50);
            mobsHandler.createVillager(location1,"Lumber_Jack");
            mobsHandler.createVillager(location2,"Lumber_Jack");
            /**Location location3 = new Location(player.getWorld(), -47.500, 56, 50);
            Location location4 = new Location(player.getWorld(), 55.500, 56, 50);
            MOB.createVillager(location3,"Black_Smith");
            MOB.createVillager(location4,"Black_Smith");
            Location location5 = new Location(player.getWorld(), -46.500, 56, 50);
            Location location6 = new Location(player.getWorld(), 56.500, 56, 50);
            MOB.createVillager(location5,"Wizardo_Carlos");
            MOB.createVillager(location6,"Wizardo_Carlos");*/
            return true;
        }
        return false;
    }
}