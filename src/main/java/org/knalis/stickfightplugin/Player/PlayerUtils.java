package org.knalis.stickfightplugin.Player;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class PlayerUtils {

    public void increaseAttackSpeed(Player player, double amount) {
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) {
            attribute.setBaseValue(attribute.getBaseValue() + amount);
        }
    }

    public void IncreaseAttackDamage(Player player, double amount) {
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (attribute != null) {
            attribute.setBaseValue(attribute.getBaseValue() + amount);
        }
    }
}
