package org.knalis.stickfightplugin.Game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HealingTask extends BukkitRunnable {

    private final JavaPlugin plugin;

    public HealingTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().getWorldBorder().isInside(player.getLocation())) {
                double newHealth = Math.min(player.getHealth() + 2.0, player.getMaxHealth());
                player.setHealth(newHealth);
            }
        }
    }
}