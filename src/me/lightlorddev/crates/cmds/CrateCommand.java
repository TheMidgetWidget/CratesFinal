package me.lightlorddev.crates.cmds;

import me.lightlorddev.crates.handlers.Crate;
import me.lightlorddev.crates.handlers.CrateManager;
import me.lightlorddev.crates.utils.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * © 2017 LightlordDev All Rights Reserved. You may not copy or use or resell any of this code at all without my permission.
 */
public class CrateCommand implements CommandExecutor {

    CrateManager cm = CrateManager.i();
    Msg msg = new Msg();

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {

        if (!(commandSender instanceof Player))
            return true;
        Player p = (Player) commandSender;
        if (args.length == 2) {
            if (args[0].equals("create")) {
                if (cm.getCrate(args[1]) != null) {
                    msg.sendRed(p, "This crate already exists.");
                    return true;
                }
                cm.createCrate(ChatColor.translateAlternateColorCodes('&', args[1]));
                msg.sendGreen(p, "A crate with the name " + args[1] + " has been created.");
            }

            if(args[0].equals("prizes")) {
                if (cm.getCrate(args[1]) == null) {
                    msg.sendRed(p, "This crate does not exist.");
                    return true;
                }
                Crate c = cm.getCrate(args[1]);
                if(c.getItems().isEmpty()) {
                    msg.sendRed(p, "This crate has no prizes");
                    return true;
                }
                p.openInventory(c.getPrizes());
            }
            if(args[0].equals("start")) {
                if (cm.getCrate(args[1]) == null) {
                    msg.sendRed(p, "This crate does not exist.");
                    return true;
                }
                Crate c = cm.getCrate(args[1]);
                if(c.getItems().isEmpty()) {
                    msg.sendRed(p, "This crate has no prizes");
                    return true;
                }
                c.start(p);
            }
            if(args[0].equals("spawn")) {
                if (cm.getCrate(args[1]) == null) {
                    msg.sendRed(p, "This crate does not exist.");
                    return true;
                }
                Crate c = cm.getCrate(args[1]);

                ItemStack crate = new ItemStack(Material.CHEST, 1);
                ItemMeta itemMeta = crate.getItemMeta();
                itemMeta.setDisplayName(c.getName()+ChatColor.GREEN+" Crate");
                crate.setItemMeta(itemMeta);

                p.getInventory().setItemInHand(crate);
                msg.sendGreen(p, "spawned a crate");
            }
            if(args[0].equals("getkey")) {
                if (cm.getCrate(args[1]) == null) {
                    msg.sendRed(p, "This crate does not exist.");
                    return true;
                }
                Crate c = cm.getCrate(args[1]);

                ItemStack crate = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta itemMeta = crate.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN+""+c.getName()+" Crate Key");
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GREEN+""+c.getName()+" Crate Key");
                itemMeta.setLore(lore);
                crate.setItemMeta(itemMeta);

                p.setItemInHand(crate);

                msg.sendGreen(p, "You have been given a "+c.getName()+" crate key");
            }
            return true;
        }

        if (args.length == 3) {
            if (args[0].equals("additem")) {
                if (cm.getCrate(args[1]) == null) {
                    msg.sendRed(p, "This crate does not exist.");
                    return true;
                }
                Crate c = cm.getCrate(args[1]);
                if (!isDouble(args[2])) {
                    msg.sendRed(p, "Specify a valid percentage");
                    return true;
                }
                double chance = Double.parseDouble(args[2]);
                if(chance > 100 || chance < 0) {
                    msg.sendRed(p, "Must be less than 100 and more than 0");
                    return true;
                }
                if(p.getItemInHand().getType() == Material.AIR || p.getItemInHand() == null) {
                    msg.sendRed(p, "Hold the specified item.");
                    return true;
                }
                cm.addItem(p.getItemInHand(), chance, c);
                msg.sendGreen(p, "Added item to crate.");
            }
            return true;
        }
        msg.sendRed(p, "/crate create <name>");
        msg.sendRed(p, "/crate additem <crate> <chance>");
        msg.sendRed(p, "/crate prizes <crate>");
        return true;
    }
}
