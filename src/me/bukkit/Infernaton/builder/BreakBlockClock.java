package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;


/**
 * A clock to define which is called right after a player break a block,
 * when the clock stop, the game replace the breaking block
 */
public class BreakBlockClock implements Runnable {

    private Block block;
    private Material blockType;
    private long startTime;
    private int id;

    public static void newCountDown(FightToSurvive main, long time, Block block){
        BreakBlockClock clock = new BreakBlockClock(time, block);
        int countDownId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, clock, time, 20L);
        clock.setId(countDownId);
    }
    public static void stopCountdown(int clockId){
        Bukkit.getScheduler().cancelTask(clockId);
    }

    private BreakBlockClock(long startTime, Block block){
        this.startTime = startTime;
        this.block = block;
        this.blockType = block.getType();
    }

    private void setId(int id){
        this.id = id;
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
