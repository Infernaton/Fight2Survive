package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.store.Constants;
import me.bukkit.Infernaton.handler.store.CoordStorage;
import me.bukkit.Infernaton.handler.store.CustomItem;
import me.bukkit.Infernaton.handler.store.StringConfig;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import static me.bukkit.Infernaton.handler.store.CoordStorage.worldName;

import java.util.HashMap;
import java.util.Map;

public class GameRunnable implements Runnable {

    private final int dayTime = 120; // length of a day or night in seconds
    private int countdownStarter = 0;
    private boolean isDay = true;
    private int id;
    private final FightToSurvive main;
    private final Location[] appleLocations = CoordStorage.getSpawnApplePoint();
    private Map<String, Integer> coolDownLoc;

    private GameRunnable(FightToSurvive main) {
        this.main = main;
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
        if (!isDay && !coolDownLoc.containsKey(StringConfig.mobWaveKey())) {
            int timeReduce = main.MH().generateMobWave();
            coolDownLoc.put(StringConfig.mobWaveKey(), Constants.mobWaveCooldown - timeReduce);
        }

        // Warning all player of the change of the time
        if ((countdownStarter + 5) % dayTime == 0) {
            ChatHandler.toAllPlayer(isDay ? StringConfig.nearNight() : StringConfig.nearDay());
        }
        // Changing the current time
        if (countdownStarter % dayTime == 0) {
            isDay = !isDay;
            if (isDay) {
                changeDay(1000, StringConfig.day());
            } else {
                changeDay(16000, StringConfig.night());
            }
        }

        // To spawn apple on each location
        // In the methods, there a test if a player is in range
        for (Location loc : appleLocations) {
            if (!coolDownLoc.containsKey(loc.toString())) {
                boolean isSpawn = CustomItem.spawningApple(loc);
                if (isSpawn)
                    coolDownLoc.put(loc.toString(), Constants.appleSpawningCooldown);
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
        if (!FightToSurvive.isGameState(GState.PLAYING)) {
            countdownStarter = 0;
            System.out.print("Timer Over!");
            stopCountdown(id);
            Bukkit.getWorld(worldName).setTime(1000);
        }
    }
}