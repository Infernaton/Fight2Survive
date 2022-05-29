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
        ChatHandler.toAllPlayer(main.stringH().finalPhase());
        main.DH().deleteAllDoors();
    }
    public void asking(Location currentDoor, List<Location> listDoors){

        boolean isRed = listDoors.indexOf(currentDoor) % 2 == 0;

        Location lastDoor = listDoors.get(listDoors.size()- 1 - (isRed ? 1 : 0));
        System.out.print("Derniere porte ? " + lastDoor.hashCode() + " == " + currentDoor.hashCode() + " = ");
        System.out.println(lastDoor.hashCode() == currentDoor.hashCode());
        if (lastDoor.hashCode() == currentDoor.hashCode()){
            on();
        }
    }
}
