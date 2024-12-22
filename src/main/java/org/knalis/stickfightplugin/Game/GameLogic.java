package org.knalis.stickfightplugin.Game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.knalis.stickfightplugin.Maps.MapManager;

public class GameLogic {
    @Setter
    @Getter
    private static boolean gameRunning;


    private final JavaPlugin plugin;
    private final MapManager mapManager;
    private BossBar bossBar;
    private BukkitRunnable timerTask;
    private final BorderManager borderManager;
    @Getter
    @Setter
    private World gameWorld;



    public GameLogic(JavaPlugin plugin) {
        this.plugin = plugin;
        this.bossBar = Bukkit.createBossBar("Game Timer", BarColor.BLUE, BarStyle.SOLID);
        this.mapManager = new MapManager(plugin);
        this.borderManager = new BorderManager(this ,plugin);
    }

    public void startGame(int durationInSeconds) {
    gameWorld = mapManager.getWorld();
        if (gameWorld == null) {
            plugin.getLogger().severe("Cannot start game: gameWorld is null");
            return;
        }
        bossBar.removeAll();
        bossBar.setVisible(false);
        initializeBossBar();
        startTimer(durationInSeconds, "Time left: ");
    }

    private void initializeBossBar() {
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);
        for (Player player : gameWorld.getPlayers()) {
            bossBar.addPlayer(player);
        }
    }

    public void startTimer(int timerDuration, String title) {
        bossBar.setVisible(true);

        timerTask = new BukkitRunnable() {
            int timeLeft = timerDuration * 20;

            @Override
            public void run() {
                if (timeLeft <= 0) {
                    endTimer();
                    addPlayersToBossBar();
                    return;
                }
                updateBossBar(title, timerDuration, timeLeft);
                timeLeft--;
            }
        };
        timerTask.runTaskTimer(plugin, 0, 1);
    }

    private void addPlayersToBossBar() {
        for (Player player : gameWorld.getPlayers()) {
            if (!bossBar.getPlayers().contains(player)) {
                bossBar.addPlayer(player);
            }
        }
    }

    private void updateBossBar(String title, int timerDuration, int timeLeft) {
        double progress = Math.max(0.0, Math.min(1.0, (double) timeLeft / (timerDuration * 20)));
        bossBar.setProgress(progress);
        bossBar.setTitle(title + (timeLeft / 20) + " seconds");
    }

    private void endTimer() {
        if (!gameRunning) {
            setGameRunning(true);
            borderManager.startBorder();
            gameWorld.setPVP(true);
        }
        bossBar.setVisible(false);
        timerTask.cancel();
    }


    public void stopGame() {
        gameRunning = false;
        gameWorld.setPVP(false);
        bossBar.setVisible(false);
        gameWorld.getWorldBorder().reset();

        if (timerTask != null) {
            timerTask.cancel();
        }
    }
}