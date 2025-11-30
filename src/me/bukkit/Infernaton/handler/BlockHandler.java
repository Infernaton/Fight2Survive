package me.bukkit.Infernaton.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class BlockHandler {

    private List<Block> containers = new ArrayList<>();

    private static BlockHandler self;

    public static BlockHandler Instance() {
        if (self == null) {
            self = new BlockHandler();
        }
        return self;
    }

    /**
     * Add block Containers to a list (Chest, furnace ...)
     * 
     * @param block the container
     */
    public static void addContainers(Block block) {
        self.containers.add(block);
    }

    /**
     * Reset the content of the container list
     */
    public static void resetContainers() {
        for (Block b : self.containers) {
            MaterialData md = b.getState().getData();
            byte blockByte = b.getData();
            Material temp = b.getType();
            b.setType(Material.AIR);
            b.setType(temp);
            b.setData(blockByte);
            b.getState().setData(md);
        }
        self.containers = new ArrayList<>();
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

    public static ItemStack getSmeltingResult(ItemStack it) {
        switch (it.getType()) {
            case IRON_ORE:
                return new ItemStack(Material.IRON_INGOT, it.getAmount());
            case GOLD_ORE:
                return new ItemStack(Material.GOLD_INGOT, it.getAmount());
            default:
                return it;
        }
    }
}
