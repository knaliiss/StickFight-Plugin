package org.knalis.stickfightplugin.Maps;

import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.knalis.stickfightplugin.Game.GameLogic;
import org.knalis.stickfightplugin.Game.GuiCreator;
import org.knalis.stickfightplugin.Player.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private final GuiCreator guiCreator = new GuiCreator();
    private final JavaPlugin plugin;
    private final PlayerManager playerManager = new PlayerManager();
    @Getter
    private List<Maps> maps;
    @Getter
    private static World world = null;

    public MapManager(JavaPlugin plugin) {
        this.plugin = plugin;
        maps = new ArrayList<>();
        maps.add(new Maps("lobby", "lobby", guiCreator.createItem(Material.BARRIER, ChatColor.GOLD + "lobby")));
        maps.get(0).setSpawnLocation(new Location(plugin.getServer().getWorlds().get(0), 0, 9, 0));
        maps.add(new Maps("desert", "desert", guiCreator.createItem(Material.SAND, ChatColor.GOLD + "desert")));
        maps.add(new Maps("flowers", "flowers", guiCreator.createItem(Material.DOUBLE_PLANT, ChatColor.GOLD + "flowers")));
        maps.add(new Maps("lesnayapolyana", "lesnayapolyana", guiCreator.createItem(Material.GRASS, ChatColor.GOLD + "lesnayapolyana")));
        maps.add(new Maps("newyear", "newyear", guiCreator.createItem(Material.SNOW_BLOCK, ChatColor.GOLD + "newyear")));
        maps.add(new Maps("newyear2", "newyear2", guiCreator.createItem(Material.SNOW_BALL, ChatColor.GOLD + "newyear2")));
    }



    public Maps getMap(String mapName) {
        for (Maps map : maps) {
            if (map.getMapName().equals(mapName)) {
                return map;
            }
        }
        return null;
    }
    public Maps getMap(int index) {
        return maps.get(index);
    }

    public void loadMaps(Player player) {
        if(world == null) {
            player.sendMessage(ChatColor.RED + "StickFight is not started :( ");
        }
        else {
            Location location = new Location(world, 0, 9, 0);
            player.teleport(location);
            playerManager.loadGameDefaultParameters(player, guiCreator);
        }
    }
    public void loadLobby(Player player) {
        Location location = new Location(plugin.getServer().getWorlds().get(0), -1, 9, 0);
        player.teleport(location);
        playerManager.loadLobbyDefaultParameters(player, guiCreator);
    }

    public void closeWorld() {
        if(world != null) {
            Location location = new Location(plugin.getServer().getWorlds().get(0), -1, 9, -1);
            world.getPlayers().forEach(player -> { player.teleport(location)
            ; playerManager.loadLobbyDefaultParameters(player, guiCreator); });
            plugin.getServer().unloadWorld(world, false);
            world = null;
        }
    }


    public void createWorld(String mapName, Player player) {
        if (world != null) {
            player.sendMessage(ChatColor.RED + "StickFight is already started :( ");
            return;
        }
        WorldCreator worldCreator = new WorldCreator(mapName);
        world = worldCreator.createWorld();
        if (world == null) {
            player.sendMessage(ChatColor.RED + "Failed to create world :( ");
        } else {
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setTime(6000);
            world.setGameRuleValue("DO_DAYLIGHT_CYCLE", "false");

            world.getWorldBorder().reset();

            player.sendMessage(ChatColor.GREEN + "StickFight is started! ");
        }
    }

}
