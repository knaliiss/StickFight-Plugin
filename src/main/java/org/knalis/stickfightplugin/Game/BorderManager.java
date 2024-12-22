package org.knalis.stickfightplugin.Game;

import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class BorderManager {
    private final GameLogic gameLogic;
    private final JavaPlugin plugin;
    private List<Integer> borderSize = Arrays.asList(70, 40, 20, 10);
    private WorldBorder worldBorder;

    public BorderManager(GameLogic gameLogic, JavaPlugin plugin) {
        this.gameLogic = gameLogic;
        this.plugin = plugin;
    }

    public void startBorder() {
        this.worldBorder = gameLogic.getGameWorld().getWorldBorder();
        setBorderParameters();
        new BorderShrinkTask().runTaskTimer(plugin, 0, 20);
    }

    private void setBorderParameters() {
        worldBorder.setDamageAmount(1);
        worldBorder.setDamageBuffer(0.0);
        Bukkit.getLogger().info("WorldBorder parameters set: DamageAmount=1.0, DamageBuffer=0.0");
    }

    private class BorderShrinkTask extends BukkitRunnable {
        private int index = 0;
        private int pauseTime = 30;

        @Override
        public void run() {
            if (!gameLogic.isGameRunning()) {
                cancel();
                return;
            }

            if (pauseTime > 0) {
                pauseTime--;
                return;
            }

            if (index < borderSize.size()) {
                int size = borderSize.get(index);
                gameLogic.startTimer(10, "Border is shrinking: ");

                if (size == 70) {
                    worldBorder.setSize(size);
                    index++;
                    size = borderSize.get(index);
                }
                worldBorder.setSize(size, 30);
                Bukkit.getLogger().info("WorldBorder size set to " + size);

                if (size == 10) {
                    worldBorder.setDamageAmount(5.0);
                    Bukkit.getLogger().info("WorldBorder damage amount increased to 3.0");
                }

                index++;
                pauseTime = 70;
            } else {
                cancel();
            }
        }
    }
}