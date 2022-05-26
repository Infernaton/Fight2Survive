package me.bukkit.Infernaton.handler;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class BlockHandler {

    private List<Block> containers = new ArrayList<>();

    public void addContainers(Block block){
        containers.add(block);
    }

    public void resetContainers(){
        for (Block b : containers) {
            MaterialData md =  b.getState().getData();
            Material temp = b.getType();
            b.setType(Material.AIR);
            b.setType(temp);
            b.getState().setData(md);
            b.getState().update();
        }
        containers = new ArrayList<>();
    }
}
