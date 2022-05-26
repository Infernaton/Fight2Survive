package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.CustomVillager;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static me.bukkit.Infernaton.handler.ConstantHandler.worldName;

public class MobsHandler {
    private int round = 1;
    private int level = 1;

    final private FightToSurvive main;
    public MobsHandler(FightToSurvive fightToSurvive) {
        this.main  = fightToSurvive;
    }

    public static void setNoAI(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsVil = ((CraftEntity) entity).getHandle();
        NBTTagCompound comp = new NBTTagCompound();
        nmsVil.c(comp);
        comp.setByte("NoAI", (byte) 1);
        nmsVil.f(comp);
        nmsVil.b(true);
    }
    public void createVillager(Location location, String name){
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);

        new CustomVillager(main, villager).addRecipe(main.constH().getTrade(name)).finish();
    }

    public void createZombie(Location location, String name){
        location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
    }
     public void createSpider(Location location, String name){
        location.getWorld().spawnEntity(location, EntityType.SPIDER);
    }
    public void createSkeleton(Location location, String name){
        location.getWorld().spawnEntity(location, EntityType.SKELETON);
    }


    public void setAllPnj() {
        List<Location> copiesPnjList = main.constH().getAllPnjLocation();
        for (int i=0; i<copiesPnjList.size(); i++){
            createVillager(copiesPnjList.get(i), main.constH().pnjName()[i]);
        }
    }

    public void killPnj(){
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (e instanceof Villager) {
                e.remove();
            }
        }
    }
    public void generateMobWave() {

        for (int i = 0; i < round; i++) {
            generateOneMob();
        }
        level++;
        int result = level % 3;
        if(result == 0){
            round++;
        }
    }

    public void generateOneMob() {
        List<Player> playerList = main.constH().getAllTeamsPlayer();
        for (Player player : playerList) {
            Location playerLocation = player.getLocation();
            List<Block> test = main.constH().sphereAround(playerLocation, 6);

            Block blockBelow;
            Block newBlock;
            do {
                int randomNum = ThreadLocalRandom.current().nextInt(0, test.size());
                //ChatHandler.sendInfoMessage(player, randomNum + "");
                newBlock = test.get(randomNum);
                blockBelow = newBlock.getRelative(0, -1, 0);
            } while (newBlock.getType() != Material.AIR || !main.constH().spawnableBlocks().contains(blockBelow.getType()));

            createZombie(newBlock.getLocation(), "Zombie");
            createSpider(newBlock.getLocation(), "Spider");
            if(round == 5){
                createSkeleton(newBlock.getLocation(), "Skeleton");
            }
        }
    }

    public void resetMob(){
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (main.constH().spawnedMobs().contains(e.getType())) {
                e.remove();
            }
        }
    }
}
