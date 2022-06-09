package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;

import static me.bukkit.Infernaton.handler.ConstantHandler.worldName;

public class GameRunnable implements Runnable{

    private final FightToSurvive main;
    private final int dayTime = 120; //length of a day or night in seconds
    private int countdownStarter = 0;
    private boolean isDay = true;
    private int id;

    private GameRunnable(FightToSurvive main){
        this.main = main;
    }

    public static GameRunnable newCountDown(FightToSurvive main){
        GameRunnable clock = new GameRunnable(main);
        int countDownId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, clock, clock.countdownStarter, 20L);
        clock.setId(countDownId);
        return clock;
    }
    public static void stopCountdown(int clockId){
        Bukkit.getScheduler().cancelTask(clockId);
    }

    public int getTime(){
        return countdownStarter;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }


    /**
     * Translate the current value from the game timer into a readable time code
     * @return the translated time value
     */
    public String stringTimer(){
        long longTimer = getTime();
        String formatTimer = longTimer < 6000 ? "mm:ss" : "mmm:ss";
        return DurationFormatUtils.formatDuration(longTimer, formatTimer);
    }

    @Override
    public void run() {

        countdownStarter++;
        main.getScoreboardManager().updateScoreboards();

        if (!main.constH().isState(GState.PLAYING)){
            countdownStarter = 0;
            System.out.print("Timer Over!");
            stopCountdown(id);
            Bukkit.getWorld(worldName).setTime(1000);
        }

        if ((countdownStarter+5) % dayTime == 0) {
            if (isDay){
                ChatHandler.broadcast(main.stringH().nearNight());
            }
            else{
                ChatHandler.broadcast(main.stringH().nearDay());
            }
        }
        if (countdownStarter % dayTime == 0) {
            isDay = !isDay;
            if (isDay){
                ChatHandler.broadcast(main.stringH().day());
                Bukkit.getWorld(worldName).setTime(1000);
            }
            else{
                ChatHandler.broadcast(main.stringH().night());
                Bukkit.getWorld(worldName).setTime(16000);
                main.MH().generateMobWave();
            }
        }
    }
}