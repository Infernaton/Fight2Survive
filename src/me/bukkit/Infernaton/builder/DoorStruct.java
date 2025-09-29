package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.handler.BlockHandler;
import me.bukkit.Infernaton.store.Mobs;
import me.bukkit.Infernaton.store.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import static me.bukkit.Infernaton.store.CoordStorage.worldName;

public class DoorStruct {

    public Location origin;
    private ItemStack item1;
    private ItemStack item2;
    public boolean isLastDoor;

    private static void setDoorStruct(Location origin) {
        //Relative y - 1
        for (int x=-1; x<2; x++) {
            for (int z=-1; z<2; z++) {
                BlockHandler.setMaterial(origin.getBlock().getRelative(x, -1, z), new MaterialData(Material.SMOOTH_BRICK));
            }
        }

        //Relative y
        for (int x=-1; x<2; x++) {
            for (int z=-1; z<2; z++) {
                MaterialData d;
                if (x != 0 && z != 0)
                    d = new MaterialData(Material.COBBLE_WALL);
                else if (x == 0 && z == 0)
                    d = new MaterialData(Material.REDSTONE_BLOCK);
                else
                    d = new MaterialData(Material.AIR);
                BlockHandler.setMaterial(origin.getBlock().getRelative(x, 0, z), d);
            }
        }

        //Relative y + 1
        for (int x=-1; x<2; x++) {
            for (int z=-1; z<2; z++) {
                MaterialData d;
                if (x != 0 && z != 0)
                    d = new MaterialData(Material.STONE, (byte) 6);
                else
                    d = new MaterialData(Material.SMOOTH_BRICK, (byte) 3);
                BlockHandler.setMaterial(origin.getBlock().getRelative(x, +1, z), d);
            }
        }
    }

    private void setHologram(String name) {
        Location pos = new Location(origin.getWorld(), origin.getX()+0.5f, origin.getY(), origin.getZ()+0.5f);
        pos.add(origin.getDirection().multiply(2.1)); // move the name position to be on the verge of the door
        Mobs.createHologram(pos, name); // name of the door
    }

    public DoorStruct (Location origin, String name, ItemStack item1, boolean isLastDoor) {
        this(origin, name, item1, new ItemStack(Material.AIR, 1), isLastDoor);
    }
    public DoorStruct (Location origin, String name, ItemStack item1, ItemStack item2, boolean isLastDoor) {
        setDoorStruct(origin);
        this.origin = origin;
        this.item1 = item1;
        if (item2.getType() != Material.AIR) this.item2 = item2;
        this.isLastDoor = isLastDoor;
        this.setHologram(name);
    }

    public boolean isLastDoor() {
        return isLastDoor;
    }
    private boolean hasItem2Slot() { return item2 != null; }

    // to open the door, we just replace each block of it by air block
    public void open() {
        for (double x = -1; x <= 1; x++) {
            for (double y = -1; y <= 1; y++) {
                for (double z = -1; z <= 1; z++) {
                    BlockHandler.remove(new Location(Bukkit.getWorld(worldName), origin.getBlockX() + x,
                            origin.getBlockY() + y, origin.getBlockZ() + z));
                }
            }
        }
        Mobs.killNearbyHolograms(origin);
        Sounds.openDoor();
    }

    /**
     * Will try to open this door by paying the cost reference by item1 and item2
     * @param player the player that try to open the door
     * @return if he succeed
     */
    public boolean tryToOpen(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        if(playerInventory.contains(item1.getType(), item1.getAmount()) && (!hasItem2Slot() || playerInventory.contains(item2.getType(), item2.getAmount()))) {
            open();
            playerInventory.removeItem(item1);
            if (item2 != null) playerInventory.removeItem(item2);

            return true;
        }

        return false;
    }
}
