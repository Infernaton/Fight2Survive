package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MobsHandler {


    final private FightToSurvive main;
    public MobsHandler(FightToSurvive fightToSurvive) {
        this.main  = fightToSurvive;
    }

    public List<Material> blockKeys = Arrays.asList(
            Material.GRASS,
            Material.DIRT,
            Material.STONE,
            Material.SOUL_SAND,
            Material.COBBLESTONE
    );
    public static void setAI(LivingEntity entity, boolean hasAi) {
        EntityLiving handle = ((CraftLivingEntity) entity).getHandle();
        handle.getDataWatcher().watch(15, (byte) (hasAi ? 0 : 1));
    }
    public static void createVillager(Location location, String name){
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);
        setAI(villager, false);
    }
    public static void createZombie(Location location, String name){
        Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        zombie.setCustomName(name);
        zombie.setCustomNameVisible(true);
        ChatHandler.broadcast(zombie.toString());
    }
    public void createMob() {

        List<Player> playerList = main.constH().getAllTeamsPlayer();
        for (Player player : playerList
        ) {
            Location playerLocation = player.getLocation();
            List<Block> test = main.constH().sphereAround(playerLocation, 6);

            Block blockBelow;
            Block newBlock;
            do {
                int randomNum = ThreadLocalRandom.current().nextInt(0, test.size());
                ChatHandler.sendInfoMessage(player, randomNum + "");
                newBlock = test.get(randomNum);
                blockBelow = newBlock.getRelative(0, -1, 0);

            } while (newBlock.getType() != Material.AIR || !blockKeys.contains(blockBelow.getType()));
            ChatHandler.sendMessage(player, newBlock.getType().toString() + blockBelow.getType().toString());
            ChatHandler.sendMessage(player, String.valueOf(newBlock.getLocation()));
            MobsHandler.createZombie(newBlock.getLocation(), "Zombie");
        }
    }
}
