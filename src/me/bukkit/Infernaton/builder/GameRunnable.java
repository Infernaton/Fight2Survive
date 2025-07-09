package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.ConstantHandler;
import me.bukkit.Infernaton.handler.Store.StringHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

import static me.bukkit.Infernaton.handler.SpatialHandler.worldName;

public class GameRunnable implements Runnable {

    private final int dayTime = 120; // length of a day or night in seconds
    private int countdownStarter = 0;
    private boolean isDay = true;
    private int id;
    private final FightToSurvive main;
    private final Location[] appleLocations;
    private Map<String, Integer> coolDownLoc;

    private GameRunnable(FightToSurvive main) {
        this.main = main;
        this.appleLocations = main.SH().getSpawnApplePoint();
        this.coolDownLoc = new HashMap<>();
    }

    public static GameRunnable newCountDown(FightToSurvive main) {
        GameRunnable clock = new GameRunnable(main);
        int countDownId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, clock, clock.countdownStarter, 20L);
        clock.setId(countDownId);
        return clock;
    }

    public static void stopCountdown(int clockId) {
        Bukkit.getScheduler().cancelTask(clockId);
    }

    public int getTime() {
        return countdownStarter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Translate the current value from the game timer into a readable time code
     * 
     * @return the translated time value
     */
    public String stringTimer() {
        long longTimer = getTime();
        String formatTimer = longTimer < 6000 ? "%02d:%02d" : "%03d:%02d";
        return String.format(formatTimer, (longTimer % 3600) / 60, (longTimer % 60));
    }

    private void changeDay(int time, String sentence) {
        ChatHandler.toAllPlayer(sentence);
        Bukkit.getWorld(worldName).setTime(time);
    }

    @Override
    public void run() {

        countdownStarter++;

        // Spawning mob once the night is set
        if (!isDay && !coolDownLoc.containsKey(StringHandler.mobWaveKey())) {
            int timeReduce = main.MH().generateMobWave();
            coolDownLoc.put(StringHandler.mobWaveKey(), ConstantHandler.mobWaveCooldown - timeReduce);
        }

        // Warning all player of the change of the time
        if ((countdownStarter + 5) % dayTime == 0) {
            ChatHandler.toAllPlayer(isDay ? StringHandler.nearNight() : StringHandler.nearDay());
        }
        // Changing the current time
        if (countdownStarter % dayTime == 0) {
            isDay = !isDay;
            if (isDay) {
                changeDay(1000, StringHandler.day());
            } else {
                changeDay(16000, StringHandler.night());
            }
        }

        // To spawn apple on each location
        // In the methods, there a test if a player is in range
        for (Location loc : appleLocations) {
            if (!coolDownLoc.containsKey(loc.toString())) {
                boolean isSpawn = main.HI().spawningApple(loc);
                if (isSpawn)
                    coolDownLoc.put(loc.toString(), ConstantHandler.appleSpawningCooldown);
            }
        }

        // All coolDown - 1
        for (Map.Entry<String, Integer> entry : coolDownLoc.entrySet()) {
            if (entry.getValue() == 0)
                coolDownLoc.remove(entry.getKey());
            else
                entry.setValue(entry.getValue() - 1);
        }

        // Stopping the timer if the game stop
        if (!main.constH().isState(GState.PLAYING)) {
            countdownStarter = 0;
            System.out.print("Timer Over!");
            stopCountdown(id);
            Bukkit.getWorld(worldName).setTime(1000);
        }
    }
}