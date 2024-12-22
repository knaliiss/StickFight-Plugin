package org.knalis.stickfightplugin.Maps;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;


@Setter
@Getter
public class Maps {
    private final String mapName;
    private final String mapPath;
    private final ItemStack mapIcon;
    private Location spawnLocation;



    public Maps(String mapName, String mapPath, ItemStack mapIcon) {
        this.mapName = mapName;
        this.mapPath = mapPath;
        this.mapIcon = mapIcon;
    }


}
