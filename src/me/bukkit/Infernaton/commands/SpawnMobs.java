package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.WaveHandler;
import me.bukkit.Infernaton.store.Mobs;
import me.bukkit.Infernaton.store.StringConfig;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnMobs implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp())
            return false;

        if (cmd.getName().equalsIgnoreCase("mob_zombie") && sender instanceof Player) {
            ChatHandler.sendInfoMessage(sender, "Spawning a mob next to you.");
            if (args.length == 0)
                WaveHandler.Instance().spawnMob((Player) sender);

            else if (args.length == 1) {
                try {
                    WaveHandler.Instance().spawnMob((Player) sender, Integer.parseInt(args[0]));
                } catch (NumberFormatException e) {
                    ChatHandler.sendError(sender, "Argument wasn't an integer. Usage '/mob_zombie <level>'");
                }
            }

            else
                ChatHandler.sendError(sender, "Too much argument. Usage '/mob_zombie <level>'");

            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("set_villagers")) {
            if (FightToSurvive.isGameState(GState.WAITING)) {
                ChatHandler.sendInfoMessage(sender, StringConfig.setVill());
                Mobs.setAllPnj();
            } else {
                ChatHandler.sendCantWhilePlaying(sender);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("kill_pnj")) {
            ChatHandler.sendInfoMessage(sender, StringConfig.killVill());
            Mobs.killPnj();
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("hologram") && sender instanceof Player) {
            if (args.length == 0)
                ChatHandler.sendError(sender, "Missing text to display.");
            else {
                Location location = ((Player) sender).getLocation();
                ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

                String myString = String.join(" ", args);
                String textFinal = ChatColor.translateAlternateColorCodes('&', myString);

                as.setGravity(false); // Make sure it doesn't fall
                as.setCanPickupItems(false); // I'm not sure what happens if you leave this as it is, but you might as
                                             // well disable it
                as.setCustomName(textFinal); // Set this to the text you want
                as.setCustomNameVisible(true); // This makes the text appear no matter if your looking at the entity or
                                               // not
                as.setVisible(false); // Makes the ArmorStand invisible
                as.setSmall(true); // To reduce its hitbox

                ChatHandler.sendInfoMessage(sender, "Spawn the hologram.");
            }

            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("killhologram") && sender instanceof Player) {
            Location location = ((Player) sender).getLocation();
            Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, 2, 2, 2);
            for (Entity entity : nearbyEntities) {
                if (entity.getType() == EntityType.ARMOR_STAND) {
                    entity.remove();
                    break;
                }
            }
            ChatHandler.sendInfoMessage(sender, "Kill nearby entity.");

            return true;
        }

        return false;
    }
}
