package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FinalPhaseHandler {

    private FightToSurvive main;

    public FinalPhaseHandler(FightToSurvive main){
        this.main = main;
    }
    public void on(){
        ChatHandler.broadcast("Open All Doors");
        List<Location> allCopies = main.constH().getAllCopiesDoors();
        for (Location copy : allCopies) {
            System.out.println(copy);
        }
    }
}
