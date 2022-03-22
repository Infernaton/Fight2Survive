package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.listeners.DoorListeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FinalPhaseHandler {

    private FightToSurvive main;

    public FinalPhaseHandler(FightToSurvive main){
        this.main = main;
    }
    public void on(){
        ChatHandler.toAllPlayer("A team has open their last door. Starting the final Phase...");
        DoorListeners setDoors = new DoorListeners(this.main);
        setDoors.deleteAllDoors();
    }
}
