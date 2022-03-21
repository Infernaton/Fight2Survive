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
    int countdownStarter = 20;
    boolean dayOrNight = true;
    int aze = 1;
    ScheduledExecutorService scheduler;

    public DayNightCycle(FightToSurvive main){
        this.main= main;
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this, 0, 1, SECONDS);
    }

    @Override
    public void run() {

        System.out.println(countdownStarter);
        countdownStarter--;
        if (!main.constH().isState(GState.PLAYING)){
            System.out.println("Timer Over!");
            scheduler.shutdown();
        }

        if (countdownStarter == 0 && dayOrNight) {
            countdownStarter = countdownStarter + 20;
            dayOrNight = false;
            System.out.println("Night Time");
            Bukkit.getWorld("Arene").setTime(13000);
        }else if(countdownStarter == 0 && !dayOrNight) {
            countdownStarter = countdownStarter + 20;
            dayOrNight = true;
            System.out.println("Day Time");
            Bukkit.getWorld("Arene").setTime(1000);
        }
    }

}
