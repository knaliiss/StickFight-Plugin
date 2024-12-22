package org.knalis.stickfightplugin.EventHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.knalis.stickfightplugin.Game.GameLogic;
import org.knalis.stickfightplugin.Game.GuiCreator;
import org.knalis.stickfightplugin.Maps.MapManager;
import org.knalis.stickfightplugin.Player.PlayerManager;
import org.knalis.stickfightplugin.Player.PlayerUtils;

import java.util.Map;
import java.util.UUID;

public class SimpleEventHandler implements Listener {

    private final JavaPlugin plugin;
    private final PlayerManager playerManager = new PlayerManager();
    private final MapManager mapManager;
    private final GameLogic gameLogic;
    private final PlayerUtils playerUtils = new PlayerUtils();
    private final GuiCreator guiCreator = new GuiCreator();

    public SimpleEventHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gameLogic = new GameLogic(plugin);
        this.mapManager = new MapManager(plugin);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        mapManager.loadLobby(player);
        player.sendMessage(ChatColor.GREEN + "Welcome to " + ChatColor.AQUA + "StickFight " + ChatColor.GREEN + "server!");
        playerManager.loadLobbyDefaultParameters(player, guiCreator);
        player.setHealth(player.getMaxHealth());
        playerUtils.increaseAttackSpeed(player, 20);
        playerUtils.IncreaseAttackDamage(player, 0.25);
        event.setJoinMessage(null);

    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (isInvisibility((Player) event.getEntity(), 3000, playerManager.getInvisibility())) {
                Bukkit.getLogger().info("Player is invisible");
                event.setCancelled(true);
            } else if (!gameLogic.isGameRunning() && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);

            }
        }
    }

    @EventHandler
    public void onPlayerTakeBorderDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
                Player player = (Player) event.getEntity();
                player.sendMessage(ChatColor.RED + "You are taking damage from the world border!");
            }
        }
    }
    @EventHandler
    public void onPlayerDie(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (((Player) event.getEntity()).getHealth() - event.getFinalDamage() <= 0) {
                Player player = (Player) event.getEntity();
                player.setGameMode(GameMode.SPECTATOR);
                player.openInventory(guiCreator.createBuyBackGui(player));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerFallIntoVoid(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                Player player = (Player) event.getEntity();
                player.teleport(player.getWorld().getSpawnLocation());
                event.setCancelled(true);
                if (gameLogic.isGameRunning()) {
                    ((Player) event.getEntity()).setGameMode(GameMode.SPECTATOR);
                    player.openInventory(guiCreator.createBuyBackGui(player));
                }
            }
        }
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType() == Material.GOLD_NUGGET) {
                Player player = event.getPlayer();
                event.setCancelled(true);
                player.openInventory(guiCreator.createMainGui(player));
            }
        }
    }

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getWorld().getWorldBorder().isInside(player.getLocation())) {
                event.setCancelled(true);
            }
        }
    }

    private boolean isInvisibility(Player player, long cooldown, Map<UUID, Long> invisibility) {
        final Long startTime = invisibility.get(player.getUniqueId());
        if (startTime == null) {
            return false;
        }
        return System.currentTimeMillis() - startTime < cooldown;
    }
}