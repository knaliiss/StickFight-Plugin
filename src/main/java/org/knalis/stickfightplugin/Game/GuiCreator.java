package org.knalis.stickfightplugin.Game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiCreator {
    public Inventory createMainGui(Player player) {
        Inventory menu = Bukkit.createInventory(player, getRows(3), "MENU");

        createItem(Material.DIAMOND_SWORD, "Start The Game", 2, 5, menu);
        createItem(Material.IRON_SWORD, "Join The Game", 2, 3, menu);
        createItem(Material.GOLD_SWORD, "Spectate The Game", 2, 7, menu);
        createItem(Material.BARRIER, "Stop The Game", 3, 5, menu);

        return menu;
    }

    public Inventory createBuyBackGui(Player player) {
        Inventory buyBackInventory = Bukkit.createInventory(player, getRows(1), "BuyBack Menu");
        createItem(Material.GREEN_GLAZED_TERRACOTTA, "Buy Back", 1, 4, buyBackInventory);
        createItem(Material.RED_GLAZED_TERRACOTTA, "Cancel", 1, 6, buyBackInventory);


        return buyBackInventory;
    }

    public Inventory createCustomGui(Player player, String title, int rows) {
        return Bukkit.createInventory(player, getRows(rows), title);
    }

    public void createItem(Material item, String name, int row, int column, Inventory inv) {
        ItemStack itemStack = new ItemStack(item, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        inv.setItem(getIndex(row, column), itemStack);
    }

    public ItemStack createItem(Material item, String name) {
        ItemStack itemStack = new ItemStack(item, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack createItem(Material item, String name, Enchantment enchantment, int level) {
        ItemStack itemStack = new ItemStack(item, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private int getRows(int n) {
        return n * 9;
    }

    private int getIndex(int rows, int columns) {
        return (rows - 1) * 9 + columns - 1;
    }

}
