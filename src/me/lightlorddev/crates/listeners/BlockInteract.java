package me.lightlorddev.crates.listeners;

import me.lightlorddev.crates.Main;
import me.lightlorddev.crates.handlers.CrateManager;
import me.lightlorddev.crates.utils.Msg;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * © 2017 LightlordDev All Rights Reserved. You may not copy or use or resell any of this code at all without my permission.
 */
public class BlockInteract implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock().getState().getType() == Material.CHEST) {
                Location l1 = e.getClickedBlock().getLocation();

                Main.i().getConfig().getConfigurationSection("crate-locations").getKeys(false).forEach(s -> {
                    Location l2 = deserialize(Main.i().getConfig().getString("crate-locations."+s));
                    if(l1.getWorld().getName().equals(l2.getWorld().getName()) && l1.getBlockX() == l2.getBlockX() && l1.getBlockY() == l2.getBlockY() && l1.getBlockZ() == l2.getBlockZ()) {
                        e.setCancelled(true);
                        if(p.getItemInHand().hasItemMeta() && p.getItemInHand().getType() == Material.ENCHANTED_BOOK && p.getItemInHand().getItemMeta().hasLore()) {
                            if(p.getItemInHand().getItemMeta().getLore().contains(ChatColor.GREEN+""+s+" Crate Key")) {
                                p.setItemInHand(new ItemStack(Material.AIR));
                                CrateManager.i().getCrate(s).start(p);
                            }
                        } else {
                            p.setVelocity(new Vector(p.getLocation().getDirection().multiply(-1).multiply(1.5).getX(), 1, p.getLocation().getDirection().multiply(-1).multiply(2).getZ()));
                            p.playSound(p.getLocation(), Sound.BLAZE_BREATH, 5, 5);
                            new Msg().sendRed(p, "You must have a crate key!");
                        }
                    }
                });
            }
        }

        if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(e.getClickedBlock().getState().getType() == Material.CHEST) {
                Location l1 = e.getClickedBlock().getLocation();

                Main.i().getConfig().getConfigurationSection("crate-locations").getKeys(false).forEach(s -> {
                    Location l2 = deserialize(Main.i().getConfig().getString("crate-locations."+s));
                    if(l1.getWorld().getName().equals(l2.getWorld().getName()) && l1.getBlockX() == l2.getBlockX() && l1.getBlockY() == l2.getBlockY() && l1.getBlockZ() == l2.getBlockZ()) {
                        e.setCancelled(true);
                        p.openInventory(CrateManager.i().getCrate(s).getPrizes());
                    }
                });
            }
        }
    }

    public Location deserialize(String s) {
        String[] st = s.split(":");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
    }

}
