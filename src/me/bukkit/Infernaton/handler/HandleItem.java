package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.ItemBuilder;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandleItem {

    private final FightToSurvive main;

    public HandleItem(FightToSurvive main){
        this.main = main;
    }

    /**
     * Give the compass that help the player to move around or start a game
     * @param player to give that compass
     */
    public void giveItemInInventory(Player player, ItemStack item, int slot){
        player.getInventory().setItem(slot, item);
        player.updateInventory();
    }

    public ItemStack magicCompass(){
        return new ItemBuilder(Material.COMPASS).setName("§aNavigation").toItemStack();
    }
    public ItemStack blueWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)11).setName("§1Equipe Bleu").toItemStack();
    }
    public ItemStack redWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)14).setName("§4Equipe Rouge").toItemStack();
    }
    public ItemStack spectatorWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)7).setName("§7Spectateur").toItemStack();
    }
    public ItemStack transformAxe(ItemStack axe){
        net.minecraft.server.v1_8_R3.ItemStack AXE = CraftItemStack.asNMSCopy(axe);
        NBTTagList idsTag2 = new NBTTagList();
        idsTag2.add(new NBTTagString("minecraft:log"));
        NBTTagCompound tag2 = new NBTTagCompound();
        tag2.set("CanDestroy", idsTag2);
        AXE.setTag(tag2);
        return CraftItemStack.asBukkitCopy(AXE);
    }

    public ItemStack transformPickaxe(ItemStack pick, int level){
        net.minecraft.server.v1_8_R3.ItemStack PICK = CraftItemStack.asNMSCopy(pick);
        NBTTagList idsTag = new NBTTagList();
        if (level>5) level = 5;
        switch (level){
            case 5:
            case 4:
            case 3:
            case 2:
                idsTag.add(new NBTTagString("minecraft:iron_ore"));
            case 1:
                idsTag.add(new NBTTagString("minecraft:cobblestone"));
                break;
        }
        NBTTagCompound tag2 = new NBTTagCompound();
        tag2.set("CanDestroy", idsTag);

        PICK.setTag(tag2);
        return CraftItemStack.asBukkitCopy(PICK);
    }

    public ItemStack stoneAxe(){
        return transformAxe(new ItemStack(Material.STONE_AXE));
    }
    public ItemStack woodPickaxe(){
        return transformPickaxe(new ItemStack(Material.WOOD_PICKAXE),1);
    }
    public ItemStack stonePickaxe(){
        return transformPickaxe(new ItemStack(Material.STONE_PICKAXE),2);
    }
}