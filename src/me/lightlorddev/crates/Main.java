package me.lightlorddev.crates;

import me.lightlorddev.crates.cmds.CrateCommand;
import me.lightlorddev.crates.handlers.RestartHandler;
import me.lightlorddev.crates.listeners.BlockInteract;
import me.lightlorddev.crates.listeners.BlockPlace;
import me.lightlorddev.crates.listeners.InventoryListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * © 2017 LightlordDev All Rights Reserved. You may not copy or use or resell any of this code at all without my permission.
 */
public class Main extends JavaPlugin {

    private static Main m;
    public static Main i() {
        return m;
    }

    RestartHandler rh;

    @Override
    public void onEnable() {
        m = this;
        rh = new RestartHandler();
        rh.onStart();
        getCommand("crate").setExecutor(new CrateCommand());
        Bukkit.getPluginManager().registerEvents(new InventoryListeners(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
        Bukkit.getPluginManager().registerEvents(new BlockInteract(), this);
    }

    @Override
    public void onDisable() {
        rh.onShutDown();
        m = null;
    }
}
