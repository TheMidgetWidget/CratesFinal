package me.lightlorddev.crates.handlers;

import me.lightlorddev.crates.Main;
import me.lightlorddev.crates.utils.GsonFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * © 2017 LightlordDev All Rights Reserved. You may not copy or use or resell any of this code at all without my permission.
 */
public class RestartHandler {


    CrateManager cm = CrateManager.i();

    public void onShutDown() {
        if(!cm.getCrates().isEmpty() && cm.getCrates() != null) {
            FileConfiguration c = Main.i().getConfig();
            cm.getCrates().forEach(crate -> {
                c.set("crates."+crate.getName()+".name", crate.getName());
                if(!crate.getItems().isEmpty()) {
                    crate.getItems().forEach((item, chance) -> {
                        c.set("crates."+crate.getName()+".items."+ GsonFactory.getCompactGson().toJson(item), chance);
                    });
                }
            });
            Main.i().saveConfig();
        }
    }

    public void onStart() {
        FileConfiguration c = Main.i().getConfig();
        if(c != null && c.getConfigurationSection("crates") != null) {
            String name;
            HashMap<ItemStack, Double> items = new HashMap<>();

            for(String s : c.getConfigurationSection("crates").getKeys(false)) {
                name = c.getString("crates."+s+".name");
                if(c.getConfigurationSection("crates."+s+".items") != null) {
                    for(String i : c.getConfigurationSection("crates."+s+".items").getKeys(false)) {
                        ItemStack item = GsonFactory.getCompactGson().fromJson(i, ItemStack.class);
                        items.put(item, c.getDouble("crates."+s+".items."+i));
                    }
                }
                cm.createCrate(name, items);
            }
        }
    }

}
