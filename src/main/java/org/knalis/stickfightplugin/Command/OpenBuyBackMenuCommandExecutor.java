package org.knalis.stickfightplugin.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.knalis.stickfightplugin.Game.GuiCreator;

public class OpenBuyBackMenuCommandExecutor implements CommandExecutor {
    private final GuiCreator guiCreator;

    public OpenBuyBackMenuCommandExecutor() {
        this.guiCreator = new GuiCreator();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        player.openInventory(guiCreator.createBuyBackGui(player));
        return true;
    }
}
