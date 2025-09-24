package me.bukkit.Infernaton.handler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class BlockHandler {

    private List<Block> containers = new ArrayList<>();

    /**
     * Add block Containers to a list (Chest, furnace ...)
     * 
     * @param block the container
     */
    public void addContainers(Block block) {
        containers.add(block);
    }

    /**
     * Reset the content of the container list
     */
    public void resetContainers() {
        for (Block b : containers) {
            MaterialData md = b.getState().getData();
            byte blockByte = b.getData();
            Material temp = b.getType();
            b.setType(Material.AIR);
            b.setType(temp);
            b.setData(blockByte);
            b.getState().setData(md);
        }
        containers = new ArrayList<>();
    }

    public static void setMaterial(Block block, MaterialData mat) {
        block.setType(mat.getItemType());
        block.setData(mat.getData());
    }

    public static void setMaterial(Location loc, MaterialData mat) {
        loc.getBlock().setType(mat.getItemType());
        loc.getBlock().setData(mat.getData());
    }

    public static void remove(Location loc) {
        setMaterial(loc, new MaterialData(Material.AIR));
    }
}
