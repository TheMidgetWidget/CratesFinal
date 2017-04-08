package me.lightlorddev.crates.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * © 2017 LightlordDev All Rights Reserved. You may not copy or use or resell any of this code at all without my permission.
 */
public class InventoryListeners implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getWhoClicked() instanceof Player) {
            Player p = (Player)e.getWhoClicked();
            if(ChatColor.stripColor(e.getInventory().getTitle()).endsWith("Crate:") || ChatColor.stripColor(e.getInventory().getTitle()).endsWith("Prizes")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onThrow(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if ((p.getOpenInventory() != null) && (ChatColor.stripColor(p.getOpenInventory().getTitle()).endsWith("Crate:") || ChatColor.stripColor(p.getOpenInventory().getTitle()).endsWith("Prizes"))) {
            e.setCancelled(true);
        }
    }
}
