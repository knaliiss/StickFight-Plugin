package org.knalis.stickfightplugin.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.knalis.stickfightplugin.Game.GameLogic;
import org.knalis.stickfightplugin.Maps.MapManager;

public class GameStopCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;
    private final MapManager mapManager;
    private final GameLogic gameLogic;

    public GameStopCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
        this.mapManager = new MapManager(plugin);
        this.gameLogic = new GameLogic(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 0) return false;
        if (!gameLogic.isGameRunning()) {
            commandSender.sendMessage(ChatColor.RED + "Game already stopped!");
            return true;
        }
        gameLogic.setGameRunning(false);
        mapManager.closeWorld();
        gameLogic.stopGame();
        return true;
    }
}
