package org.knalis.stickfightplugin.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.knalis.stickfightplugin.Maps.MapManager;

public class LobbyCommandExecutor implements CommandExecutor {
    private final JavaPlugin plugin;
    private final MapManager mapManager;

    public LobbyCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
        this.mapManager = new MapManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 0) return false;
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            mapManager.loadLobby(player);
            commandSender.sendMessage("Вы перемещены в лобби!");
        }
        return true;
    }
}
