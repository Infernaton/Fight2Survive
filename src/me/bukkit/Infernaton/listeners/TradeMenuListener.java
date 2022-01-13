package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.OpenMenuTrade;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class TradeMenuListener implements Listener {
    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity e = event.getRightClicked();
        if (event.getRightClicked() instanceof Villager){
        if (e.getName().equals("Lumber_Jack")){
            event.setCancelled(true);
            OpenMenuTrade trade = new OpenMenuTrade("shop");
            trade.addTrade(new ItemStack(Material.COBBLESTONE, 10),new ItemStack(Material.WOOL, 30));
            trade.addTrade(new ItemStack(Material.STICK, 2),new ItemStack(Material.WOOD, 3), new ItemStack(Material.WOOD_AXE, 1));
            trade.addTrade(new ItemStack(Material.STICK, 2),new ItemStack(Material.WOOD, 3), new ItemStack(Material.WOOD_PICKAXE, 1));
            trade.openTrade(p);

            ItemStack POTION_HEALING = new ItemStack(Material.POTION);
            PotionMeta meta_heal = (PotionMeta) POTION_HEALING.getItemMeta();
            meta_heal.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 0), true);
            POTION_HEALING.setItemMeta(meta_heal);
            p.getInventory().addItem(POTION_HEALING);

            ItemStack POTION_INVISIBILITY = new ItemStack(Material.POTION);
            PotionMeta meta_invisible = (PotionMeta) POTION_INVISIBILITY.getItemMeta();
            meta_invisible.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 400, 0), false);
            POTION_INVISIBILITY.setItemMeta(meta_invisible);
            p.getInventory().addItem(POTION_INVISIBILITY);

            ItemStack POTION_SPEED = new ItemStack(Material.POTION);
            PotionMeta meta_speed = (PotionMeta) POTION_SPEED.getItemMeta();
            meta_speed.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 400, 0), false);
            POTION_SPEED.setItemMeta(meta_speed);
            p.getInventory().addItem(POTION_SPEED);
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
            trade.addTrade(new ItemStack(Material.GOLD_INGOT, 2),new ItemStack(Material.POTION, 1));
            trade.addTrade(new ItemStack(Material.GOLD_INGOT, 2),new ItemStack(Material.POTION, 1));
            trade.addTrade(new ItemStack(Material.GOLD_INGOT, 2),new ItemStack(Material.POTION, 1));

            trade.openTrade(p);
        }
    }
    }
}

