package me.bukkit.Infernaton.builder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Clock implements Runnable {
    private Block block;
    private long startTime;


    public Clock(long startTime, Block block){
        this.startTime = startTime;
        this.block = block;

    }

    @Override
    public void run() {
        Bukkit.broadcastMessage("La clock est lancée");
        startTime --;
        if (startTime == 0){
            block.setType(Material.COBBLESTONE);
            Bukkit.broadcastMessage("Les ressources ont respawn");
        }
    }
}
