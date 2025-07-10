package me.bukkit.Infernaton.handler;

import static me.bukkit.Infernaton.store.CoordStorage.worldName;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.store.Constants;
import me.bukkit.Infernaton.store.CoordStorage;
import me.bukkit.Infernaton.store.Mobs;

/**
 * Handle wave from aggressive mob
 */
public class WaveHandler {

    private static WaveHandler self;

    public static WaveHandler Instance() {
        if (self == null) {
            self = new WaveHandler();
        }
        return self;
    }

    private int generateMobLevel() {
        // Each 5 minutes, the mob level will rise to 1
        return Math.round(1 + (FightToSurvive.getTimer().getTime() / 5 * 60));
    }

    public float chanceToSpawn() {
        return Constants.mobSpawnChance
                + (Constants.mobSpawnChanceMultiplier * FightToSurvive.getTimer().getTime() / 3);
        // 1 min => + 2%
    }

    public void spawnMob(Player player) {
        spawnMob(player, generateMobLevel());
    }

    public void spawnMob(Player player, int mobLevel) {
        Location playerLocation = player.getLocation();
        List<Block> test = CoordStorage.sphereAround(playerLocation, 12);

        Block blockBelow;
        Block newBlock;
        int count = 50;
        do {
            int randomNum = ThreadLocalRandom.current().nextInt(0, test.size());
            // ChatHandler.sendInfoMessage(player, randomNum + "");
            newBlock = test.get(randomNum);
            blockBelow = newBlock.getRelative(0, -1, 0);
            count--;
        } while ((newBlock.getType() != Material.AIR || !Mobs.spawnableBlocks().contains(blockBelow.getType()))
                && count > 0);

        if (newBlock.getType() != Material.AIR || !Mobs.spawnableBlocks().contains(blockBelow.getType())) {
            ChatHandler.sendError(player, "Wasn't able to spawn a mob");
            return;
        }
        Mobs.createRandomAggressiveMob(newBlock.getLocation(), mobLevel);
    }

    public void resetMob() {
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (Mobs.spawnedMobs().contains(e.getType())) {
                e.remove();
            }
        }
    }
}
