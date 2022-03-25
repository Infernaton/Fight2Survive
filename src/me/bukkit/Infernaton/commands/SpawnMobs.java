package me.bukkit.Infernaton.commands;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.MobsHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class SpawnMobs implements CommandExecutor {

    public List<Material> blockKeys = Arrays.asList(
            Material.GRASS,
            Material.DIRT,
            Material.STONE,
            Material.SOUL_SAND,
            Material.COBBLESTONE
    );

    final private FightToSurvive main;
    public SpawnMobs(FightToSurvive fightToSurvive) {
        this.main  = fightToSurvive;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("mob_zombie") && commandSender instanceof Player) {
            main.MobsHandler().createMob();
            //  Player player = (Player) commandSender;
            /* List<Player> playerList = main.constH().getAllTeamsPlayer();
            for (Player player: playerList
                 ) {
                Location playerLocation =  player.getLocation();
                List<Block> test = main.constH().sphereAround(playerLocation , 6);

                Block blockBelow  = null;
                Block newBlock = null;
                do {
                    int randomNum = ThreadLocalRandom.current().nextInt(0 , test.size());
                    ChatHandler.sendInfoMessage(player, randomNum + "");
                    newBlock = test.get(randomNum);
                    blockBelow = newBlock.getRelative(0, -1, 0);

                } while (newBlock.getType() != Material.AIR || !blockKeys.contains(blockBelow.getType()) );
                ChatHandler.sendMessage(player, newBlock.getType().toString() + blockBelow.getType().toString());
                ChatHandler.sendMessage(player, String.valueOf(newBlock.getLocation()));
                mobsHandler.createZombie(newBlock.getLocation(),"Zombie");
            }*/
            return true;
        }
        return false;
    }
}
