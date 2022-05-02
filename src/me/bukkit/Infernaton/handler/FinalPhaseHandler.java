package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.Location;

import java.util.List;

public class FinalPhaseHandler {

    private final FightToSurvive main;
    private boolean active;

    public FinalPhaseHandler(FightToSurvive main){
        this.main = main;
        this.active = false;
    }

    public void off(){
        this.active = false;
    }
    public boolean isActive(){
        return active;
    }

    public void on(){
        active = true;
        ChatHandler.toAllPlayer("A team has open their last door. Starting the final Phase...");
        main.constH().deleteAllDoors();
    }
    public void asking(Location currentDoor, List<Location> listDoors){
        System.out.print("Derniere porte ? " + listDoors.get(listDoors.size()- 1) + " == " + currentDoor+ " = ");
        System.out.println(listDoors.get(listDoors.size()- 1) == currentDoor);
        if (listDoors.get(listDoors.size()- 1) == currentDoor){
            on();
        }
    }
}
