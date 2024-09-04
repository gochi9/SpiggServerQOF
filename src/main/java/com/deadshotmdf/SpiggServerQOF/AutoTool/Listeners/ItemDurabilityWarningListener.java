package com.deadshotmdf.SpiggServerQOF.AutoTool.Listeners;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.OptionStatus;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Objects.AutoToolProfile;
import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Utils.AutoToolUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.UUID;

public class ItemDurabilityWarningListener implements Listener {

    private final AutoToolManager autoToolManager;

    public ItemDurabilityWarningListener(AutoToolManager autoToolManager) {
        this.autoToolManager = autoToolManager;
    }

    @EventHandler
    public void onWarning(PlayerItemDamageEvent ev){
        ItemStack item = ev.getItem();

        if(!item.hasItemMeta())
            return;

        Player player = ev.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        UUID uuid = player.getUniqueId();
        short maxDurability = item.getType().getMaxDurability();
        short damage = (short) (maxDurability - ((Damageable)item.getItemMeta()).getDamage());
        boolean isEnabled = autoToolManager.isAutoToolEnabled(uuid);
        AutoToolProfile profile = autoToolManager.getProfile(uuid);
        OptionStatus warn = profile.getOptionValue(AutoToolOption.WARN_TOOL_LOW_DURABILITY_MESSAGE), prevent = profile.getOptionValue(AutoToolOption.PREVENT_TOOL_BREAK);

        warnMessage(warn, isEnabled, maxDurability, damage, player, item);

        if(damage < 2 && (prevent == OptionStatus.ALWAYS || (isEnabled && prevent == OptionStatus.ONLY_ON_AUTO_MODE)) && AutoToolUtils.isTool(item.getType()))
            ev.setCancelled(true);
    }

    private void warnMessage(OptionStatus warn, boolean isEnabled, short maxDurability, short damage, Player player, ItemStack item){
        if(!(warn == OptionStatus.ALWAYS || (isEnabled && warn == OptionStatus.ONLY_ON_AUTO_MODE)))
            return;

        Integer per = maxDurability - damage == maxDurability - 2 ? Integer.valueOf(1) : getPercentage((int) AutoToolUtils.calculateDurabilityRemaining(maxDurability, damage));

        if(per == null)
            return;

        if(!autoToolManager.wasWarned(player, item, per))
            player.sendMessage(ConfigSettings.getItemWarnMessage(item.getType().toString(), per));
    }

    // I could implement a TreeMap and use floorEntry
    // I could, will I? No. Am I lazy? YES!
    private static Integer getPercentage(int amount) {
        if (amount > 25)
            return null;

        else if (amount > 10)
            return 25;

        else if (amount > 5)
            return 10;

        else if (amount > 1)
            return 5;

        else
            return 1;
    }

}
