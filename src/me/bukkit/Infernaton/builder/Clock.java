package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Clock implements Runnable {

    private Block block;
    private Material blockType;
    private long startTime;
    private int id;

    public static void newCountDown(FightToSurvive main, long time, Block block){
        Clock clock = new Clock(time, block);
        int countDownId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, clock, time, 20L);
        clock.setId(countDownId);
    }

    public void setId(int id){
        this.id = id;
    }
    public static void stopCountdown(int clockId){
        Bukkit.getScheduler().cancelTask(clockId);
    }

    public Clock(long startTime, Block block){
        this.startTime = startTime;
        this.block = block;
        this.blockType = block.getType();
    }

    @Override
    public void run() {
        startTime --;
        if (startTime == 0){
            block.setType(blockType);
            stopCountdown(id);
        }
    }
}
