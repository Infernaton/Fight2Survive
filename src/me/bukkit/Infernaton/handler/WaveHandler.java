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
import me.bukkit.Infernaton.builder.clock.GameRunnable;
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
        GameRunnable gr = FightToSurvive.getTimer();
        int currentTime;
        if (gr == null)
            currentTime = 0;
        else
            currentTime = gr.getTime();

        // Each 5 minutes, the mob level will rise to 1
        return (int) Math.floor(1 + (currentTime / (5 * 60)));
    }

    public float chanceToSpawn() {
        // Because the time need to be only for the night, the simpliest way to
        // represent that is to divide current time by 2
        // (day and night last the same time)
        return Constants.mobSpawnChance
                + (Constants.mobSpawnChanceMultiplier * (FightToSurvive.getTimer().getTime() / 2) / 3);
        // + 1 min => + 1%
    }

    public void spawnMob(Player player) {
        spawnMob(player, generateMobLevel());
    }

    public void spawnMob(Player player, int mobLevel) {
        Location playerLocation = player.getLocation();
        List<Block> test = CoordStorage.highestCircleArround(playerLocation, 8, 12);

        System.out.println(test.size());

        Block spawnBlockPosition;
        Block newBlock;
        int count = 50;
        do {
            int randomNum = ThreadLocalRandom.current().nextInt(0, test.size());
            // ChatHandler.sendInfoMessage(player, randomNum + "");
            newBlock = test.get(randomNum);
            spawnBlockPosition = newBlock.getRelative(0, 1, 0);
            count--;
        } while (spawnBlockPosition.getType() != Material.AIR && count > 0);

        if (spawnBlockPosition.getType() != Material.AIR) {
            ChatHandler.sendError(player, "Wasn't able to spawn a mob");
            return;
        }
        Mobs.createRandomAggressiveMob(spawnBlockPosition.getLocation(), mobLevel);
    }

    public void resetMob() {
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (Mobs.spawnedMobs().contains(e.getType())) {
                e.remove();
            }
        }
    }
}
