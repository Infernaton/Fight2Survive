package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.ChatHandler;
import org.bukkit.Bukkit;

/**
 * Build a countdown which will automatically stop when reaches 0
 */
public class CountDownBuilder {

    public static void newCountDown(FightToSurvive main, long time, String msg){
        CountDown countDown = new CountDown(time, msg);
        int countDownId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, countDown, time, 20L);
        countDown.setId(countDownId);
    }

    public static void stopCountdown(int clockId){
        Bukkit.getScheduler().cancelTask(clockId);
    }
    public static void stopAllCountdown(FightToSurvive main){
        Bukkit.getScheduler().cancelTasks(main);
    }
}
