package me.lightlorddev.crates.listeners;

import me.lightlorddev.crates.Main;
import me.lightlorddev.crates.handlers.Crate;
import me.lightlorddev.crates.handlers.CrateManager;
import me.lightlorddev.crates.utils.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * © 2017 LightlordDev All Rights Reserved. You may not copy or use or resell any of this code at all without my permission.
 */
public class BlockPlace implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(p.getItemInHand().getType() == Material.CHEST && p.getItemInHand().hasItemMeta() && ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName()).endsWith("Crate")) {
            if(!p.hasPermission("crate.place"))
                return;
            for(Crate c : CrateManager.i().getCrates()) {
                if(ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName()).replace("Crate", "").replace(" ", "").equals(ChatColor.stripColor(c.getName()))) {
                    Main.i().getConfig().set("crate-locations."+c.getName(), serialize(e.getBlock().getLocation()));
                    Main.i().saveConfig();
                    new Msg().sendGreen(p, "Crate has been placed and is now useable.");
                }
            }
        }
    }

    public String serialize(Location l) {
        return l.getWorld().getName()+":"+l.getBlockX()+":"+l.getBlockY()+":"+l.getBlockZ();
    }

}
