package org.knalis.stickfightplugin.EventHandler;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.knalis.stickfightplugin.Game.GameLogic;
import org.knalis.stickfightplugin.Game.GuiCreator;
import org.knalis.stickfightplugin.Maps.MapManager;
import org.knalis.stickfightplugin.Player.PlayerManager;

public class MenuClickHandler implements Listener {

    private final JavaPlugin plugin;
    private final String MENU_TITLE = "MENU";
    private final GuiCreator guiCreator = new GuiCreator();
    private final MapManager mapManager;
    private final PlayerManager playerManager = new PlayerManager();
    private final GameLogic gameLogic;

    public MenuClickHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.mapManager = new MapManager(plugin);
        this.gameLogic = new GameLogic(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        String title = inventory.getTitle();
        Player player = (Player) event.getWhoClicked();
        String itemName = ChatColor.stripColor(event.getCurrentItem().getI18NDisplayName());

        if (MENU_TITLE.equals(title)) {
            event.setCancelled(true);
            handleMenuClick(player, itemName);
        } else if ("Map Selector".equals(title)) {
            event.setCancelled(true);
            handleMapSelectorClick(player, itemName);
        } else if ("BuyBack Menu".equals(title)) {
            event.setCancelled(true);
            handleRespawnClick(player, itemName);
        }
    }

    private void handleMenuClick(Player player, String itemName) {
        switch (itemName) {
            case "Start The Game":
                Inventory mapSelector = guiCreator.createCustomGui(player, "Map Selector", 3);
                mapManager.getMaps().forEach(map -> guiCreator.createItem(map.getMapIcon().getType(), map.getMapName(), 2, mapManager.getMaps().indexOf(map) + 1, mapSelector));
                player.openInventory(mapSelector);
                break;
            case "Spectate The Game":
                mapManager.loadMaps(player);
                player.setGameMode(GameMode.SPECTATOR);
                break;
            case "Join The Game":
                mapManager.loadMaps(player);
                if (gameLogic.isGameRunning()) {
                    player.setGameMode(GameMode.SPECTATOR);
                }else {
                    player.setGameMode(GameMode.ADVENTURE);
                }
                break;
            case "Stop The Game":
                mapManager.closeWorld();
                gameLogic.stopGame();
                break;
        }
    }

    private void handleMapSelectorClick(Player player, String itemName) {
        switch (itemName) {
            case "desert":
                mapManager.createWorld(mapManager.getMaps().get(1).getMapName(), player);
                break;
            case "flowers":
                mapManager.createWorld(mapManager.getMaps().get(2).getMapName(), player);
                break;
            case "lesnayapolyana":
                mapManager.createWorld(mapManager.getMaps().get(3).getMapName(), player);
                break;
            case "newyear":
                mapManager.createWorld(mapManager.getMaps().get(4).getMapName(), player);
                break;
            case "newyear2":
                mapManager.createWorld(mapManager.getMaps().get(5).getMapName(), player);
                break;
        }
        player.closeInventory();
    }

    private void handleRespawnClick(Player player, String itemName) {
        player.sendMessage("handleRespawnClick called with item: " + itemName);
        switch (itemName) {
            case "Buy Back":
                playerManager.getRespawn(player);
                player.sendMessage("You have respawned");
                break;
            case "Cancel":
                player.sendMessage("You have canceled the respawn");
                break;
            default:
                player.sendMessage("Unknown item: " + itemName);
                break;
        }
        player.closeInventory();
    }
}