package me.bukkit.Infernaton.builder.clock;

import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.FinalPhaseHandler;
import me.bukkit.Infernaton.store.Sounds;
import me.bukkit.Infernaton.store.StringConfig;

public class FinalPhaseCountdown extends CountDown{

    public FinalPhaseCountdown(long cooldown) {
        newCountDown(this, cooldown);
    }

    @Override
    public void run() {

        if (time == 0) {
            FinalPhaseHandler.Instance().activate();
        } else if (time % 10 == 0 || time <= 5) {
            Sounds.tickTimerSound();
            ChatHandler.toAllPlayer(StringConfig.secondLeft((int) time));
        }

        //Will update the timer
        super.run();
    }
}
