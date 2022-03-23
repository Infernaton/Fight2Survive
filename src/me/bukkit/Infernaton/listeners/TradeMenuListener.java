package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.OpenMenuTrade;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TradeMenuListener implements Listener {



    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity e = event.getRightClicked();



        if (event.getRightClicked() instanceof Villager){
        if (e.getName().equals("Lumber_Jack")){
            event.setCancelled(true);

            OpenMenuTrade trade = new OpenMenuTrade("shop");

            net.minecraft.server.v1_8_R3.ItemStack WOOD_PICKAXE = CraftItemStack.asNMSCopy(new ItemStack(Material.WOOD_PICKAXE, 1));
            NBTTagList idsTag1 = new NBTTagList();
            idsTag1.add(new NBTTagString("minecraft:cobblestone"));
            NBTTagCompound tag1 = WOOD_PICKAXE.hasTag() ? WOOD_PICKAXE.getTag() : new NBTTagCompound();
            tag1.set("CanDestroy", idsTag1);
            WOOD_PICKAXE.setTag(tag1);
            trade.addTrade(new ItemStack(Material.LOG, 6), (CraftItemStack.asBukkitCopy(WOOD_PICKAXE)));

            net.minecraft.server.v1_8_R3.ItemStack STONE_AXE = CraftItemStack.asNMSCopy(new ItemStack(Material.STONE_AXE, 1));
            NBTTagList idsTag2 = new NBTTagList();
            idsTag2.add(new NBTTagString("minecraft:log"));
            NBTTagCompound tag2 = STONE_AXE.hasTag() ? STONE_AXE.getTag() : new NBTTagCompound();
            tag2.set("CanDestroy", idsTag2);
            STONE_AXE.setTag(tag2);
            trade.addTrade(new ItemStack(Material.LOG, 4),new ItemStack(Material.COBBLESTONE, 4), (CraftItemStack.asBukkitCopy(STONE_AXE)));

            net.minecraft.server.v1_8_R3.ItemStack STONE_PICKAXE = CraftItemStack.asNMSCopy(new ItemStack(Material.STONE_PICKAXE, 1));
            NBTTagList idsTag3 = new NBTTagList();
            idsTag3.add(new NBTTagString("minecraft:cobblestone"));
            NBTTagCompound tag3 = STONE_PICKAXE.hasTag() ? STONE_PICKAXE.getTag() : new NBTTagCompound();
            tag3.set("CanDestroy", idsTag3);
            STONE_PICKAXE.setTag(tag3);
            trade.addTrade(new ItemStack(Material.LOG, 6),new ItemStack(Material.COBBLESTONE, 8), (CraftItemStack.asBukkitCopy(STONE_PICKAXE)));
            trade.addTrade(new ItemStack(Material.GOLD_INGOT, 5), new ItemStack(Material.GOLDEN_APPLE, 1));


            ItemStack PAPER_KEY = new ItemStack(Material.PAPER);
            ItemMeta papermeta = PAPER_KEY.getItemMeta();
            papermeta.setDisplayName("Key");
            PAPER_KEY.setItemMeta(papermeta);
            trade.addTrade(new ItemStack(Material.LOG,15),new ItemStack(Material.COBBLESTONE, 15),(PAPER_KEY));
            trade.openTrade(p);
        }
        if (e.getName().equals("Black_Smith")){
            event.setCancelled(true);
            OpenMenuTrade trade = new OpenMenuTrade("shop");
            trade.addTrade(new ItemStack(Material.STICK, 2),new ItemStack(Material.IRON_INGOT, 3), new ItemStack(Material.IRON_AXE, 1));
            trade.addTrade(new ItemStack(Material.GOLD_INGOT, 4),new ItemStack(Material.GOLDEN_APPLE, 1));
            trade.openTrade(p);
        }
        if (e.getName().equals("Wizardo_Carlos")){
            event.setCancelled(true);
            OpenMenuTrade trade = new OpenMenuTrade("shop");

            ItemStack POTION_HEALING = new ItemStack(Material.POTION);
            PotionMeta meta_heal = (PotionMeta) POTION_HEALING.getItemMeta();
            meta_heal.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 0), true);
            meta_heal.setDisplayName("POTION_HEALING");
            POTION_HEALING.setItemMeta(meta_heal);
            trade.addTrade(new ItemStack(Material.GOLD_INGOT, 5), (POTION_HEALING));

            ItemStack POTION_INVISIBILITY = new ItemStack(Material.POTION);
            PotionMeta meta_invisible = (PotionMeta) POTION_INVISIBILITY.getItemMeta();
            meta_invisible.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 400, 0), false);
            meta_invisible.setDisplayName("POTION_INVISIBILITY");
            POTION_INVISIBILITY.setItemMeta(meta_invisible);
            trade.addTrade(new ItemStack(Material.GOLD_INGOT, 5), (POTION_INVISIBILITY));

            ItemStack POTION_SPEED = new ItemStack(Material.POTION);
            PotionMeta meta_speed = (PotionMeta) POTION_SPEED.getItemMeta();
            meta_speed.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 400, 0), false);
            meta_speed.setDisplayName("POTION_SPEED");
            POTION_SPEED.setItemMeta(meta_speed);
            trade.addTrade(new ItemStack(Material.GOLD_INGOT, 5), (POTION_SPEED));
            trade.openTrade(p);
        }
    }
    }
}

