package me.bukkit.Infernaton.store;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.CustomVillager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;

import static me.bukkit.Infernaton.store.CoordStorage.worldName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Handle all the mobs that players can encounter
 */
public class Mobs {

    // List of block type where a monster can spawn
    public static List<Material> spawnableBlocks() {
        return Arrays.asList(
                Material.GRASS,
                Material.DIRT,
                Material.STONE,
                Material.SOUL_SAND,
                Material.COBBLESTONE,
                Material.HARD_CLAY,
                Material.STAINED_CLAY);
    }

    public static List<EntityType> aggressiveMob(int lvl) {
        List<EntityType> list = new ArrayList<>();
        if (lvl > 5)
            lvl = 5;
        switch (lvl) {
            case 5:
                list.add(EntityType.SKELETON);
            case 4:
            case 3:
                list.add(EntityType.SPIDER);
            case 2:
            case 1:
                list.add(EntityType.ZOMBIE);
                break;
        }
        return list;
    }

    public static List<EntityType> spawnedMobs() {
        List<EntityType> list = new ArrayList<>(Arrays.asList(
                EntityType.VILLAGER,
                EntityType.EXPERIENCE_ORB,
                EntityType.DROPPED_ITEM));
        list.addAll(aggressiveMob(5));
        return list;
    }

    public static void createVillager(Location location, String name) {
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);

        new CustomVillager(FightToSurvive.Instance(), villager).addRecipe(Constants.getTrade(name)).finish();
    }

    public static void createRandomAggressiveMob(Location location, int mobLevel) {
        List<EntityType> mobList = aggressiveMob(mobLevel);
        EntityType mobType = mobList.get(new Random().nextInt(mobList.size()));

        // make sur the spawn location is in the center of a block
        location = new Location(location.getWorld(), location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f);
        location.getWorld().spawnEntity(location, mobType);
    }

    public static void setAllPnj() {
        List<Location> copiesPnjList = CoordStorage.getAllPnjLocation();
        for (int i = 0; i < copiesPnjList.size(); i++) {
            createVillager(copiesPnjList.get(i), Constants.pnjName()[i]);
        }
    }

    public static void killPnj() {
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (e instanceof Villager) {
                e.remove();
            }
        }
    }
}
