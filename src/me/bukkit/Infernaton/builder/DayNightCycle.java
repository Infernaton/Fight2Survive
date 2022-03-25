package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DayNightCycle implements Runnable{

    private FightToSurvive main;
    private final int initTime = 120;
    int countdownStarter = initTime;
    boolean dayOrNight = true;

    private int id;

    private DayNightCycle(FightToSurvive main){
        this.main = main;
    }

    public static void newCountDown(FightToSurvive main){
        DayNightCycle clock = new DayNightCycle(main);
        int countDownId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, clock, clock.initTime, 20L);
        clock.setId(countDownId);
    }


    public void setId(int id){
        this.id = id;
    }
    public static void stopCountdown(int clockId){
        Bukkit.getScheduler().cancelTask(clockId);
    }

    @Override
    public void run() {

        System.out.print(countdownStarter);
        countdownStarter--;

        if (!main.constH().isState(GState.PLAYING)){
            System.out.print("Timer Over!");
            stopCountdown(id);
            Bukkit.getWorld("Arene").setTime(1000);
        }

        if (countdownStarter == 0) {
            countdownStarter = initTime;
            dayOrNight = !dayOrNight;
            if (dayOrNight){
                ChatHandler.broadcast("Day Time");
                Bukkit.getWorld("Arene").setTime(1000);
            }
            else{
                ChatHandler.broadcast("Night Time");
                Bukkit.getWorld("Arene").setTime(16000);
                main.MobsHandler().createMob();
            }
        }
    }
}