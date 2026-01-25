package me.bukkit.Infernaton.handler;

import static me.bukkit.Infernaton.store.CoordStorage.worldName;

import java.util.ArrayList;
import java.util.List;

import me.bukkit.Infernaton.store.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
        return Config.getMobSpawnChance()
                + (Config.getMobSpawnChanceMultiplier() * (FightToSurvive.getTimer().getTime() / 2) / 3);
        // + 1 min => + 1%
    }

    public void spawnMob(Player player) {
        spawnMob(player, generateMobLevel());
    }

    public void spawnMob(Player player, int mobLevel) {
        Location highest = CoordStorage.getRandomHighestAround(player.getLocation(), 8, 12);

        if (highest == null) {
            ChatHandler.sendError(player, "Wasn't able to spawn a mob");
            return;
        }
        Mobs.createRandomAggressiveMob(highest, mobLevel);
    }

    public List<LivingEntity> getAllMobs() {
        List<LivingEntity> entities = new ArrayList<>();
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (e instanceof LivingEntity && Mobs.aggressiveMob(5).contains(e.getType())) {
                entities.add((LivingEntity) e);
            }
        }
        return entities;
    }

    public void resetSpawnedEntity() {
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (Mobs.spawnedEntity().contains(e.getType())) {
                e.remove();
            }
        }
    }
}
