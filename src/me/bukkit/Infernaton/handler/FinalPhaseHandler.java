package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.builder.DoorStruct;
import me.bukkit.Infernaton.builder.Team;
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
        TitleHandler.toAllPlayer("§lThe Final Phase Begins", "§c§oAll remaining doors are now opened");
        DoorHandler.deleteAllDoors();
        Sounds.finalPhaseSound();
    }

    public void asking(boolean needToActivate) {
        // Will launched a timer before the finalPhase will begins
        if (needToActivate) {
            ChatHandler.toAllPlayer(StringConfig.finalPhase());
            new FinalPhaseCountdown(15);
        }
    }
}
