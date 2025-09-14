package me.bukkit.Infernaton.builder.clock;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.store.StringConfig;

import org.bukkit.Bukkit;

public class CountDown implements Runnable {

    protected int id;
    protected long time;

    protected static void newCountDown(CountDown cd, long time) {
        cd.time = time;
        int countDownId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FightToSurvive.Instance(), cd, time, 20L);
        cd.setId(countDownId);
    }

    public static void stopCountdown(int clockId) {
        Bukkit.getScheduler().cancelTask(clockId);
    }

    public static void stopAllCountdown(FightToSurvive main) {
        Bukkit.getScheduler().cancelTasks(main);
    }

    private void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        if (time == 0) {
            stopCountdown(id);
        }

        time--;
    }
}
