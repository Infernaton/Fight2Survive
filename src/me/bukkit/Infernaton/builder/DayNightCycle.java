package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import org.bukkit.Bukkit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;
import static me.bukkit.Infernaton.handler.ConstantHandler.worldName;

public class DayNightCycle implements Runnable{
    private final FightToSurvive main;
    private final int initTime = 120;

    int countdownStarter = initTime;
    boolean dayOrNight = true;
    ScheduledExecutorService scheduler;

    public DayNightCycle(FightToSurvive main){
        this.main= main;
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this, 0, 1, SECONDS);
    }

    @Override
    public void run() {

        System.out.print(countdownStarter);
        countdownStarter--;
        if (!main.constH().isState(GState.PLAYING)){
            System.out.print("Timer Over!");
            scheduler.shutdown();
            Bukkit.getWorld(worldName).setTime(1000);
        }

        if (countdownStarter == 0 && dayOrNight) {
            countdownStarter = countdownStarter + initTime;
            dayOrNight = false;
            ChatHandler.broadcast("Night Time");
            Bukkit.getWorld(worldName).setTime(13000);
        }else if(countdownStarter == 0) {
            countdownStarter = countdownStarter + initTime;
            dayOrNight = true;
            ChatHandler.broadcast("Day Time");
            Bukkit.getWorld(worldName).setTime(1000);
        }
    }

}
