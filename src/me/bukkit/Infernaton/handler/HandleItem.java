package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.ItemBuilder;
import me.bukkit.Infernaton.builder.Team;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HandleItem {

    private final FightToSurvive main;
    private ConstantHandler constH;
    public HandleItem(FightToSurvive main){
        this.main = main;
    }
    public ConstantHandler constH(){
        return constH;
    }

    public Team getRedTeam(){return Team.getTeamByName(main.stringH().redTeamName());}
    public Team getBlueTeam(){return Team.getTeamByName(main.stringH().blueTeamName());}
    public Team getSpectators(){return Team.getTeamByName(main.stringH().spectatorName());}
    /**
     * Give the compass that help the player to move around or start a game
     * @param player to give that compass
     */

    public void giveItemInInventory(Player player, ItemStack item, int slot){
        player.getInventory().setItem(slot, item);
        player.updateInventory();
    }
    public void removeItemHand(Player player){
        player.setItemInHand(new ItemStack(Material.AIR));
    }

    public ItemStack magicCompass(){
        return new ItemBuilder(Material.COMPASS).setName(main.stringH().compassName()).toItemStack();
    }
    public ItemStack paperKey(){
        return new ItemBuilder(Material.PAPER).setName(main.stringH().keyName()).toItemStack();
    }
    public ItemStack blueWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)11).setName(main.stringH().blueTeamItem()).setLore(getBlueTeam().getPlayers().toString()).toItemStack();
    }
    public ItemStack redWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)14).setName(main.stringH().redTeamItem()).setLore(getRedTeam().getPlayers().toString()).toItemStack();
    }
    public ItemStack spectatorWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)7).setName(main.stringH().spectatorsItem()).setLore(getSpectators().getPlayers().toString()).toItemStack();
    }
    public ItemStack gameStartWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)5).setName(main.stringH().launch()).toItemStack();
    }
    public ItemStack gameCancelWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)14).setName(main.stringH().cancelItem()).toItemStack();
    }
    public ItemStack optionsWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)8).setName(main.stringH().optionItem()).toItemStack();
    }
    public ItemStack returnWool(){
        return new ItemBuilder(Material.WOOL, 1, (byte)8).setName(main.stringH().returnItem()).toItemStack();
    }

    public ItemStack separator(){
        return new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack();
    }

    //#region TOOLS
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
                idsTag.add(new NBTTagString("minecraft:lapis_ore"));
            case 3:
                idsTag.add(new NBTTagString("minecraft:diamond_ore"));
                idsTag.add(new NBTTagString("minecraft:gold_ore"));
            case 2:
                idsTag.add(new NBTTagString("minecraft:iron_ore"));
                idsTag.add(new NBTTagString("minecraft:coal_ore"));
            case 1:
                idsTag.add(new NBTTagString("minecraft:cobblestone"));
                break;
        }
        NBTTagCompound tag2 = new NBTTagCompound();
        tag2.set("CanDestroy", idsTag);

        PICK.setTag(tag2);
        return CraftItemStack.asBukkitCopy(PICK);
    }

    public ItemStack woodAxe(){
        return transformAxe(new ItemStack(Material.WOOD_AXE));
    }
    public ItemStack stoneAxe(){
        return transformAxe(new ItemStack(Material.STONE_AXE));
    }
    public ItemStack goldAxe(){
        return new ItemBuilder(transformAxe(new ItemStack(Material.GOLD_AXE))).setInfinityDurability().toItemStack();
    }
    public ItemStack ironAxe(){
        return transformAxe(new ItemStack(Material.IRON_AXE));
    }
    public ItemStack diamondAxe(){
        return transformAxe(new ItemStack(Material.DIAMOND_AXE));
    }

    public ItemStack woodPickaxe(){
        return transformPickaxe(new ItemStack(Material.WOOD_PICKAXE),1);
    }
    public ItemStack stonePickaxe(){
        return transformPickaxe(new ItemStack(Material.STONE_PICKAXE),2);
    }
    public ItemStack goldPickaxe(){
        return new ItemBuilder(transformPickaxe(new ItemStack(Material.GOLD_PICKAXE),2)).setInfinityDurability().toItemStack();
    }
    public ItemStack ironPickaxe(){
        return transformPickaxe(new ItemStack(Material.IRON_PICKAXE),3);
    }
    public ItemStack diamondPickaxe(){
        return transformPickaxe(new ItemStack(Material.DIAMOND_PICKAXE),4);
    }

    public ItemStack goldSword(){
        return new ItemBuilder(Material.GOLD_SWORD).setInfinityDurability().toItemStack();
    }
    public ItemStack goldShovel(){
        return new ItemBuilder(Material.GOLD_SPADE).setInfinityDurability().toItemStack();
    }
    public ItemStack goldHoe(){
        return new ItemBuilder(Material.GOLD_HOE).setInfinityDurability().toItemStack();
    }
    //#endregion
}