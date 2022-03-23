package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TradeMenuListener implements Listener {

    private FightToSurvive main;

    public TradeMenuListener(FightToSurvive main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity e = event.getRightClicked();

        if (event.getRightClicked() instanceof Villager){

            event.setCancelled(true);

            if (e.getName().equals("Lumber_Jack")){

                OpenMenuTrade trade = new OpenMenuTrade("shop");

                trade.addTrade(new ItemStack(Material.LOG, 6), (main.HI().woodPickaxe()));
                trade.addTrade(new ItemStack(Material.LOG, 4),new ItemStack(Material.COBBLESTONE, 4), (main.HI().stoneAxe()));
                trade.addTrade(new ItemStack(Material.LOG, 6),new ItemStack(Material.COBBLESTONE, 8), (main.HI().stonePickaxe()));
                trade.addTrade(new ItemStack(Material.LOG,10),new ItemStack(Material.COBBLESTONE, 10), new ItemStack(Material.PAPER,1));

                trade.openTrade(p);
            }
            /*
            if (e.getName().equals("Wizardo_Carlos")){
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
            }*/
        }
    }
}

