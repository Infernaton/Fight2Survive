package me.bukkit.Infernaton.handler;

import static me.bukkit.Infernaton.handler.store.CoordStorage.worldName;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.bukkit.Infernaton.handler.store.Constants;
import me.bukkit.Infernaton.handler.store.CoordStorage;
import me.bukkit.Infernaton.handler.store.Mobs;

/**
 * Handle wave from aggressive mob
 */
public class WaveHandler {

    private int round = 1;
    private int level = 1;

    private static WaveHandler self;

    public static WaveHandler Instance() {
        if (self == null) {
            self = new WaveHandler();
        }
        return self;
    }

    public int generateMobWave() {
        int nbMob = Math.min(level, 7);
        for (int i = 0; i < nbMob; i++) {
            generateOneMob(round);
        }
        level++;
        if (level % 3 == 0) {
            round++;
        }
        return (nbMob - 1) * 3;
    }

    public void generateOneMob(int mobLevel) {
        List<Player> playerList = Constants.getAllTeamsPlayer();
        for (Player player : playerList) {
            if (player.getGameMode() != GameMode.ADVENTURE)
                continue;
            Location playerLocation = player.getLocation();
            List<Block> test = CoordStorage.sphereAround(playerLocation, 12);

            Block blockBelow;
            Block newBlock;
            do {
                int randomNum = ThreadLocalRandom.current().nextInt(0, test.size());
                // ChatHandler.sendInfoMessage(player, randomNum + "");
                newBlock = test.get(randomNum);
                blockBelow = newBlock.getRelative(0, -1, 0);
            } while (newBlock.getType() != Material.AIR
                    || !Mobs.spawnableBlocks().contains(blockBelow.getType()));

            Mobs.createRandomAggressiveMob(newBlock.getLocation(), mobLevel);
        }
    }

    public void resetMob() {
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (Mobs.spawnedMobs().contains(e.getType())) {
                e.remove();
            }
        }
        level = 1;
        round = 1;
    }
}
