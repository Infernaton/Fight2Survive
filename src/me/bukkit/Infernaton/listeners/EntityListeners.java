package me.bukkit.Infernaton.listeners;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityListeners implements Listener {

    /**
     * Prevent Villager from taking damage
     * That will prevent it from being killed by player or zombie
     * 
     * @param event
     */
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Villager) {
            event.setCancelled(true);
        }
    }
}
