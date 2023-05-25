package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.CustomVillager;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;

import java.util.List;
import java.util.Random;
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

    public void createRandomAggressiveMob(Location location, int mobLevel){
        List<EntityType> mobList = main.constH().aggressiveMob(mobLevel);
        EntityType mobType = mobList.get(new Random().nextInt(mobList.size()));

        location.getWorld().spawnEntity(location, mobType);
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

    public int generateMobWave() {
        int nbMob = Math.min(level, 7);
        for (int i = 0; i < nbMob; i++) {
            generateOneMob(round);
        }
        level++;
        if(level % 3 == 0){
            round++;
        }
        return (nbMob-1)*3;
    }

    public void generateOneMob(int mobLevel) {
        List<Player> playerList = main.constH().getAllTeamsPlayer();
        for (Player player : playerList) {
            if (player.getGameMode() != GameMode.ADVENTURE) continue;
            Location playerLocation = player.getLocation();
            List<Block> test = main.constH().sphereAround(playerLocation, 12);

            Block blockBelow;
            Block newBlock;
            do {
                int randomNum = ThreadLocalRandom.current().nextInt(0, test.size());
                //ChatHandler.sendInfoMessage(player, randomNum + "");
                newBlock = test.get(randomNum);
                blockBelow = newBlock.getRelative(0, -1, 0);
            } while (newBlock.getType() != Material.AIR || !main.constH().spawnableBlocks().contains(blockBelow.getType()));

            createRandomAggressiveMob(newBlock.getLocation(), mobLevel);
        }
    }

    public void resetMob(){
        for (Entity e : Bukkit.getWorld(worldName).getEntities()) {
            if (main.constH().spawnedMobs().contains(e.getType())) {
                e.remove();
            }
        }
        level = 1;
        round = 1;
    }
}
