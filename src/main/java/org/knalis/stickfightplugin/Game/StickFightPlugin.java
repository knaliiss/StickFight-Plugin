package org.knalis.stickfightplugin.Game;

import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;
import org.knalis.stickfightplugin.Command.*;
import org.knalis.stickfightplugin.EventHandler.MenuClickHandler;
import org.knalis.stickfightplugin.EventHandler.SimpleEventHandler;

public final class StickFightPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            this.getServer().getWorld("world").setDifficulty(Difficulty.PEACEFUL);
        }catch (NullPointerException e){
            getLogger().warning("World lobby not found!");
        }
        getServer().getPluginManager().registerEvents(new SimpleEventHandler(this), this);
        getServer().getPluginManager().registerEvents(new MenuClickHandler(this), this);
        getCommand("test").setExecutor(new testCommand());
        getCommand("buyback").setExecutor(new OpenBuyBackMenuCommandExecutor());
        getCommand("gamestart").setExecutor(new GameStartCommandExecutor(this));
        getCommand("gamestop").setExecutor(new GameStopCommandExecutor(this));
        getCommand("lobby").setExecutor(new LobbyCommandExecutor(this));
        getCommand("getdir").setExecutor(new GetWorldDirectory(this));
        getCommand("isrun").setExecutor(new GetRunningStatusCommandExecutor(this));

        new HealingTask(this).runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {

    }
}