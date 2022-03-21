package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import org.bukkit.Bukkit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;
import static me.bukkit.Infernaton.GState.WAITING;

public class DayNightCycle implements Runnable{
    private FightToSurvive main;
    int countdownStarter = 120;
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
        if (main.constH().isState(GState.PLAYING)){
            System.out.print("Timer Over!");
            scheduler.shutdown();
            Bukkit.getWorld("Arene").setTime(1000);
        }

        if (countdownStarter == 0 && dayOrNight) {
            countdownStarter = countdownStarter + 120;
            dayOrNight = false;
            System.out.print("Night Time");
            Bukkit.getWorld("Arene").setTime(13000);
        }else if(countdownStarter == 0 && !dayOrNight) {
            countdownStarter = countdownStarter + 120;
            dayOrNight = true;
            System.out.print("Day Time");
            Bukkit.getWorld("Arene").setTime(1000);
        }
    }

}
