package me.bukkit.Infernaton.store;

import me.bukkit.Infernaton.builder.ItemBuilder;
import me.bukkit.Infernaton.handler.ChatHandler;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import static me.bukkit.Infernaton.store.CoordStorage.worldName;

import java.util.Collection;

/**
 * Store all the specific item of the game
 */
public class CustomItem {

    public static void giveItem(Player player, ItemStack item) {
        player.getInventory().addItem(item);
        player.updateInventory();
    }

    /**
     * @param player
     * @param item
     * @param slot
     */
    public static void setItemInInventory(Player player, ItemStack item, int slot) {
        player.getInventory().setItem(slot, item);
        player.updateInventory();
    }

    public static void removeItemHand(Player player) {
        player.setItemInHand(new ItemStack(Material.AIR));
    }

    public static boolean comparor(ItemStack check, ItemStack reference) {
        return check.getType() == reference.getType() && check.hasItemMeta() && check.getItemMeta().hasDisplayName()
                && check.getItemMeta().getDisplayName().equalsIgnoreCase(reference.getItemMeta().getDisplayName());
    }

    // #region Custom Item
    public static ItemStack magicCompass() {
        return new ItemBuilder(Material.COMPASS).setName(StringConfig.compassName()).toItemStack();
    }

    public static ItemStack paperKey() {
        return new ItemBuilder(Material.PAPER).setName(StringConfig.keyName()).toItemStack();
    }
    // #endregion

    // #region Menu Item
    public static ItemStack blueWool() {
        return new ItemBuilder(Material.WOOL, 1, (byte) 11).setName(StringConfig.blueTeamItem()).setLore()
                .setLore(StringConfig.makePlayerList(Constants.getBlueTeam().getPlayers()))
                .toItemStack();
    }

    public static ItemStack redWool() {
        return new ItemBuilder(Material.WOOL, 1, (byte) 14).setName(StringConfig.redTeamItem())
                .setLore(StringConfig.makePlayerList(Constants.getRedTeam().getPlayers()))
                .toItemStack();
    }

    public static ItemStack spectatorWool() {
        return new ItemBuilder(Material.WOOL, 1, (byte) 7).setName(StringConfig.spectatorsItem())
                .setLore(StringConfig.makePlayerList(Constants.getSpectators().getPlayers()))
                .toItemStack();
    }

    public static ItemStack gameStartWool() {
        return new ItemBuilder(Material.WOOL, 1, (byte) 5).setName(StringConfig.launch()).toItemStack();
    }

    public static ItemStack gameCancelWool() {
        return new ItemBuilder(Material.WOOL, 1, (byte) 14).setName(StringConfig.cancelItem()).toItemStack();
    }

    public static ItemStack optionsWool() {
        return new ItemBuilder(Material.WOOL, 1, (byte) 8).setName(StringConfig.optionItem()).toItemStack();
    }

    public static ItemStack returnWool() {
        return new ItemBuilder(Material.WOOL, 1, (byte) 8).setName(StringConfig.returnItem()).toItemStack();
    }

    public static ItemStack separator() {
        return new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack();
    }
    // #endregion

    // #region TOOLS
    private static ItemStack transformAxe(ItemStack axe) {
        net.minecraft.server.v1_8_R3.ItemStack AXE = CraftItemStack.asNMSCopy(axe);
        NBTTagList idsTag2 = new NBTTagList();
        idsTag2.add(new NBTTagString("minecraft:log"));
        NBTTagCompound tag2 = new NBTTagCompound();
        tag2.set("CanDestroy", idsTag2);
        AXE.setTag(tag2);
        return CraftItemStack.asBukkitCopy(AXE);
    }

    private static ItemStack transformPickaxe(ItemStack pick, int level) {
        net.minecraft.server.v1_8_R3.ItemStack PICK = CraftItemStack.asNMSCopy(pick);
        NBTTagList idsTag = new NBTTagList();
        if (level > 5)
            level = 5;
        switch (level) {
            case 5:
            case 4:
                idsTag.add(new NBTTagString("minecraft:lapis_ore"));
            case 3:
                idsTag.add(new NBTTagString("minecraft:diamond_ore"));
                idsTag.add(new NBTTagString("minecraft:gold_ore"));
            case 2:
                idsTag.add(new NBTTagString("minecraft:iron_ore"));
                idsTag.add(new NBTTagString("minecraft:coal_ore"));
            case 1:
                idsTag.add(new NBTTagString("minecraft:cobblestone"));
                idsTag.add(new NBTTagString("minecraft:stone"));
                break;
        }
        NBTTagCompound tag2 = new NBTTagCompound();
        tag2.set("CanDestroy", idsTag);

        PICK.setTag(tag2);
        return CraftItemStack.asBukkitCopy(PICK);
    }

    public static ItemStack woodAxe() {
        return transformAxe(new ItemStack(Material.WOOD_AXE));
    }

    public static ItemStack stoneAxe() {
        return transformAxe(new ItemStack(Material.STONE_AXE));
    }

    public static ItemStack goldAxe() {
        return new ItemBuilder(transformAxe(new ItemStack(Material.GOLD_AXE))).setInfinityDurability().toItemStack();
    }

    public static ItemStack ironAxe() {
        return transformAxe(new ItemStack(Material.IRON_AXE));
    }

    public static ItemStack diamondAxe() {
        return transformAxe(new ItemStack(Material.DIAMOND_AXE));
    }

    public static ItemStack woodPickaxe() {
        return transformPickaxe(new ItemStack(Material.WOOD_PICKAXE), 1);
    }

    public static ItemStack stonePickaxe() {
        return transformPickaxe(new ItemStack(Material.STONE_PICKAXE), 2);
    }

    public static ItemStack goldPickaxe() {
        return new ItemBuilder(transformPickaxe(new ItemStack(Material.GOLD_PICKAXE), 2)).setInfinityDurability()
                .toItemStack();
    }

    public static ItemStack ironPickaxe() {
        return transformPickaxe(new ItemStack(Material.IRON_PICKAXE), 3);
    }

    public static ItemStack diamondPickaxe() {
        return transformPickaxe(new ItemStack(Material.DIAMOND_PICKAXE), 4);
    }

    public static ItemStack goldSword() {
        return new ItemBuilder(Material.GOLD_SWORD).setInfinityDurability().toItemStack();
    }

    public static ItemStack goldShovel() {
        return new ItemBuilder(Material.GOLD_SPADE).setInfinityDurability().toItemStack();
    }

    public static ItemStack goldHoe() {
        return new ItemBuilder(Material.GOLD_HOE).setInfinityDurability().toItemStack();
    }
    // #endregion

    // #region spawn item
    private static void spawnItem(Location loc, ItemStack it) {
        Bukkit.getWorld(worldName).dropItem(loc, it).setVelocity(new Vector(0.0, 0.0, 0.0));
    }

    /**
     * Try to spawn an apple if there is a player nearby
     * 
     * @todo see if this function can go somewhere else
     * @param loc where the item will spawn
     * @return the success of the operation
     */
    public static boolean spawningApple(Location loc) {
        Collection<Entity> entities = Bukkit.getWorld(worldName).getNearbyEntities(loc, 25, 6, 25);
        for (Entity e : entities) {
            if (e instanceof Player) {
                spawnItem(loc, new ItemStack(Material.APPLE));
                return true;
            }
        }
        return false;
    }
    // #endregion
}