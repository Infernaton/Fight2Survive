package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.builder.clock.CountDown;
import me.bukkit.Infernaton.store.Sounds;

import me.bukkit.Infernaton.store.StringConfig;

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
            new CountDown(15) {
                @Override
                public void newRun() {
                    if (time == 0) {
                        FinalPhaseHandler.Instance().activate();
                    } else if (time % 10 == 0 || time <= 5) {
                        Sounds.tickTimerSound();
                        ChatHandler.toAllPlayer(StringConfig.secondLeft((int) time));
                    }
                }
            };
//            new FinalPhaseCountdown(15);
        }
    }
}
