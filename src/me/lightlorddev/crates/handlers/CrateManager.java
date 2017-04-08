package me.lightlorddev.crates.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * © 2017 LightlordDev All Rights Reserved. You may not copy or use or resell any of this code at all without my permission.
 */
public class CrateManager {

    List<Crate> crates  = new ArrayList<>();

    private static CrateManager cm;
    public static CrateManager i() {
        if(cm == null)
            cm = new CrateManager();
        return cm;
    }

    public void createCrate(String name) {
        HashMap<ItemStack, Double> items = new HashMap<>();
        Crate c = new Crate(name, items);
        crates.add(c);
    }

    public void createCrate(String name, HashMap<ItemStack, Double> items) {
        Crate c = new Crate(name, items);
        crates.add(c);
    }

    public void addItem(ItemStack item, double chance, Crate c){
        HashMap<ItemStack, Double> items = c.getItems();
        items.put(item, chance);
        c.setItems(items);
    }

    public Crate getCrate(String name) {
        for(Crate c : crates) {
            if(ChatColor.stripColor(c.getName()).toLowerCase().equals(name.toLowerCase())) {
                return c;
            }
        }
        return null;
    }
    public List<Crate> getCrates() {
        return this.crates;
    }

    public void createChest(Location loc) {

    }

}
