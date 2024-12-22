package org.knalis.stickfightplugin.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class GetWorldDirectory implements CommandExecutor {
    private final JavaPlugin plugin;

    public GetWorldDirectory(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String worldName = "world";
        String worldDirectory = plugin.getServer().getWorld(worldName).getWorldFolder().getAbsolutePath();
        commandSender.sendMessage("World directory: " + worldDirectory);
        return true;
    }
}