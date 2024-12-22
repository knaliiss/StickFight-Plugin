package org.knalis.stickfightplugin.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.knalis.stickfightplugin.Game.GameLogic;

public class GetRunningStatusCommandExecutor implements CommandExecutor {

    private final JavaPlugin plugin;
    private final GameLogic gameLogic;

    public GetRunningStatusCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gameLogic = new GameLogic(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            if (strings.length != 0) return false;
            if (gameLogic.isGameRunning()) {
                commandSender.sendMessage("Game is running!");
            } else {
                commandSender.sendMessage("Game is not running!");
            }
        }

        return true;
    }
}
