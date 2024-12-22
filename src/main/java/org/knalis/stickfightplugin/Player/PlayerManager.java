package org.knalis.stickfightplugin.Player;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.knalis.stickfightplugin.Game.GuiCreator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    @Getter
    private static Map<UUID, Long> invisibility = new HashMap<>();
    public void loadLobbyDefaultParameters(Player player, GuiCreator guiCreator) {
        player.getInventory().clear();
        player.getInventory().setItem(0, guiCreator.createItem(Material.GOLD_NUGGET, ChatColor.AQUA + "Menu"));
        player.getInventory().setHeldItemSlot(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.setCanPickupItems(false);
        player.setAllowFlight(true);
    }
    public void loadGameDefaultParameters(Player player, GuiCreator guiCreator) {
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setCanPickupItems(false);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setExp(0);
        player.setLevel(0);
        player.getInventory().clear();
        player.getInventory().setHeldItemSlot(0);
        player.getInventory().setItem(0, guiCreator.createItem(Material.STICK, ChatColor.AQUA + "Stick", Enchantment.KNOCKBACK, 1));
    }

    public void getRespawn(Player player){
        player.teleport(player.getWorld().getSpawnLocation());
        player.setGameMode(GameMode.ADVENTURE);
        setInvisibility(player, invisibility);

    }



    private void setInvisibility(Player player, Map<UUID, Long> invisibility) {
        invisibility.merge(player.getUniqueId(), System.currentTimeMillis(), (oldValue, newValue) -> System.currentTimeMillis());
    }
}
