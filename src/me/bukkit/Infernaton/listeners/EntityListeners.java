package me.bukkit.Infernaton.listeners;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class EntityListeners implements Listener {

    /**
     * Prevent Villager from taking damage
     * That will prevent it from being killed by player or zombie
     * 
     * @param event EntityDamageByEntityEvent
     */
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Villager) {
            event.setCancelled(true);
        }
    }

    /**
     * Prevent Player from giving item to invisible armor stand
     * 
     * @param event PlayerArmorStandManipulateEvent
     */
    @EventHandler
    public void onManipulateArmorStand(PlayerArmorStandManipulateEvent event) {
        if (!event.getRightClicked().isVisible()) {
            event.setCancelled(true);
        }
    }
}
