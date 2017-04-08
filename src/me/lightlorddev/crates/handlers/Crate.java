package me.lightlorddev.crates.handlers;

import me.lightlorddev.crates.Main;
import me.lightlorddev.crates.utils.RandomCollection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

/**
 * © 2017 LightlordDev All Rights Reserved. You may not copy or use or resell any of this code at all without my permission.
 */
public class Crate {

    private String name;
    private HashMap<ItemStack, Double> items;

    public Crate(String name, HashMap<ItemStack, Double> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<ItemStack, Double> getItems() {
        return items;
    }

    public void setItems(HashMap<ItemStack, Double> items) {
        this.items = items;
    }

    public Inventory getPrizes() {
        Inventory i = Bukkit.createInventory(null, 27, ChatColor.BLUE + this.name + " Prizes");
        items.forEach((item, chance) -> {
            ItemStack it = item.clone();
            ItemMeta im = it.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (im.getLore() == null) {

            } else {
                lore = im.getLore();
            }
            lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "" + chance + "%");
            im.setLore(lore);
            it.setItemMeta(im);

            i.addItem(it);
        });
        return i;
    }

    public void start(Player p) {

        Inventory i = Bukkit.createInventory(null, 27, ChatColor.RED + this.name + " Crate:");

        p.openInventory(i);

        IntStream.rangeClosed(0, 8).forEach(s -> i.setItem(s, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)));
        IntStream.rangeClosed(18, 21).forEach(s -> i.setItem(s, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)));
        IntStream.rangeClosed(23, 26).forEach(s -> i.setItem(s, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)));
        i.setItem(9, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
        i.setItem(17, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
        i.setItem(22, new ItemStack(Material.LEVER, 1));

        p.updateInventory();

        RandomCollection<ItemStack> itc = new RandomCollection<>();
        this.items.forEach((item, chance) -> {
            itc.add(chance, item);
        });

        int[] counter = {0};

        new BukkitRunnable() {
            @Override
            public void run() {
                counter[0]++;
                IntStream.rangeClosed(10, 16).forEach(s -> {
                    if (i.getItem(s) == null || i.getItem(s).getType() == Material.AIR) {
                        i.setItem(s, itc.next());
                    }
                    if(s == 16) {
                        i.setItem(s, itc.next());
                        p.playSound(p.getLocation(), Sound.WOOD_CLICK, 2, 1);
                    } else {
                        i.setItem(s, i.getItem(s+1));
                        p.playSound(p.getLocation(), Sound.WOOD_CLICK, 2, 1);
                    }
                });
                if(counter[0] == 10) {
                    cancel();
                    counter[0] = 0;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            counter[0]++;
                            IntStream.rangeClosed(10, 16).forEach(s -> {
                                if (i.getItem(s) == null || i.getItem(s).getType() == Material.AIR) {
                                    i.setItem(s, itc.next());
                                }
                                if(s == 16) {
                                    i.setItem(s, itc.next());
                                    p.playSound(p.getLocation(), Sound.WOOD_CLICK, 2, 1);
                                } else {
                                    i.setItem(s, i.getItem(s+1));
                                    p.playSound(p.getLocation(), Sound.WOOD_CLICK, 2, 1);
                                }
                            });
                            if(counter[0] == 8) {
                                cancel();
                                counter[0] = 0;
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        counter[0]++;
                                        IntStream.rangeClosed(10, 16).forEach(s -> {
                                            if (i.getItem(s) == null || i.getItem(s).getType() == Material.AIR) {
                                                i.setItem(s, itc.next());
                                            }
                                            if(s == 16) {
                                                i.setItem(s, itc.next());
                                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 2, 1);
                                            } else {
                                                i.setItem(s, i.getItem(s+1));
                                                p.playSound(p.getLocation(), Sound.WOOD_CLICK, 2, 1);
                                            }
                                        });
                                        if(counter[0] == 4) {
                                            cancel();
                                            counter[0] = 0;
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    counter[0]++;
                                                    IntStream.rangeClosed(10, 16).forEach(s -> {
                                                        if (i.getItem(s) == null || i.getItem(s).getType() == Material.AIR) {
                                                            i.setItem(s, itc.next());
                                                        }
                                                        if(s == 16) {
                                                            i.setItem(s, itc.next());
                                                            p.playSound(p.getLocation(), Sound.WOOD_CLICK, 2, 1);
                                                        } else {
                                                            i.setItem(s, i.getItem(s+1));
                                                            p.playSound(p.getLocation(), Sound.WOOD_CLICK, 2, 1);
                                                        }
                                                    });
                                                    if(counter[0] == 2) {
                                                        cancel();
                                                        counter[0] = 0;
                                                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 2);

                                                        new BukkitRunnable() {
                                                            @Override
                                                            public void run() {
                                                                p.getInventory().addItem(i.getItem(13));
                                                                p.closeInventory();
                                                            }
                                                        }.runTaskLater(Main.i(), 20);

                                                    }
                                                }
                                            }.runTaskTimerAsynchronously(Main.i(), 6, 10);

                                        }
                                    }
                                }.runTaskTimerAsynchronously(Main.i(), 3, 6);

                            }
                        }
                    }.runTaskTimerAsynchronously(Main.i(), 1, 3);

                }
            }
        }.runTaskTimerAsynchronously(Main.i(), 0, 1);
    }
}
