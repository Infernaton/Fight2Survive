package me.bukkit.Infernaton.builder.clock;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.store.StringConfig;

public class StartingCountdown extends CountDown{

    public StartingCountdown(long departTime) {
        newCountDown(this, departTime);
    }

    @Override
    public void run() {
        if (time == 0) {
            FightToSurvive.Instance().start();
        } else if (time % 10 == 0 || time <= 5) {
            ChatHandler.toAllPlayer(StringConfig.secondLeft((int) time));
        }

        //Will update the timer
        super.run();
    }
}
