package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.builder.clock.FinalPhaseCountdown;
import me.bukkit.Infernaton.builder.clock.StartingCountdown;
import me.bukkit.Infernaton.store.Constants;
import me.bukkit.Infernaton.store.Sounds;
import org.bukkit.Location;

import me.bukkit.Infernaton.store.StringConfig;
import org.bukkit.Sound;

import java.util.List;

/**
 * Handle the Final Phase, were player can't respawn and mean the end of the
 * game.
 */
public class FinalPhaseHandler {

    private boolean active;

    private static FinalPhaseHandler self;

    public static FinalPhaseHandler Instance() {
        if (self == null) {
            self = new FinalPhaseHandler();
        }
        return self;
    }

    public FinalPhaseHandler() {
        this.active = false;
    }

    public void off() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
        ChatHandler.toAllPlayer(StringConfig.finalPhase());
        DoorHandler.deleteAllDoors();
        TitleHandler.toAllPlayer("§lThe Final Phase Begins", "§c§oAll remaining doors are now opened");
        Sounds.finalPhaseSound();
    }

    public void asking(Location currentDoor, List<Location> listDoors) {

        boolean isRed = listDoors.indexOf(currentDoor) % 2 == 0;

        Location lastDoor = listDoors.get(listDoors.size() - 1 - (isRed ? 1 : 0));

        // Will launched a timer before the finalPhase will begins
        if (lastDoor.hashCode() == currentDoor.hashCode()) {
            new FinalPhaseCountdown(15);
        }
    }
}
