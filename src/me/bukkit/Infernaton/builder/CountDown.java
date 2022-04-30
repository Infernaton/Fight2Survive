package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.ChatHandler;
import org.bukkit.Bukkit;

/**
 * The clock right before the game start
 */
public class CountDown implements Runnable {

    private FightToSurvive main;
    private long time;
    private int id;

    public static void newCountDown(FightToSurvive main, long time){
        CountDown countDown = new CountDown(time, main);
        int countDownId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, countDown, time, 20L);
        countDown.setId(countDownId);
    }

    public static void stopCountdown(int clockId){
        Bukkit.getScheduler().cancelTask(clockId);
    }
    public static void stopAllCountdown(FightToSurvive main){
        Bukkit.getScheduler().cancelTasks(main);
    }

    private CountDown(long departTime, FightToSurvive main){
        this.time = departTime;
        this.main = main;
    }

    private void setId(int id){
        this.id = id;
    }

    @Override
    public void run() {

        if (time == 0){
            stopCountdown(id);
            main.start();
        }
        else if(time % 10 == 0 || time <= 5){
            ChatHandler.sendMessageListPlayer(main.constH().getAllTeamsPlayer(),time + " seconds left !");
        }
        time--;
    }
}
