package me.lightlorddev.crates.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * © 2017 LightlordDev All Rights Reserved. You may not copy or use or resell any of this code at all without my permission.
 */
public class Msg {

    String prefix = ChatColor.RED+"["+ChatColor.WHITE+"MayhemPvP"+ChatColor.RED+"] ";

    public void sendRed(CommandSender s, String msg) {
        s.sendMessage(prefix+msg);
    }

    public void sendGreen(CommandSender s, String msg) {
        s.sendMessage(prefix+ChatColor.GREEN+msg);
    }

    public void sendGold(CommandSender s, String msg) {
        s.sendMessage(prefix+ChatColor.GOLD+msg);
    }

    public void broadcastMsg(String msg) {
        Bukkit.getServer().broadcastMessage(prefix+ChatColor.GOLD+""+ ChatColor.BOLD+msg);
    }


}
