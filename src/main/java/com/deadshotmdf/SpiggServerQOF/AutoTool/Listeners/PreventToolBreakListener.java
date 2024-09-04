package com.deadshotmdf.SpiggServerQOF.AutoTool.Listeners;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.OptionStatus;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Objects.AutoToolProfile;
import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.UUID;

public class PreventToolBreakListener implements Listener {

    private final AutoToolManager autoToolManager;

    public PreventToolBreakListener(AutoToolManager autoToolManager) {
        this.autoToolManager = autoToolManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent ev) {
        if(ev.isCancelled())
            return;

        Player player = ev.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        ItemStack item = player.getInventory().getItemInMainHand();

        if(!item.hasItemMeta() || !(item.getItemMeta() instanceof Damageable) || (item.getType().getMaxDurability() - (((Damageable)item.getItemMeta()).getDamage())) > 1)
            return;

        UUID uuid = player.getUniqueId();
        boolean isEnabled = autoToolManager.isAutoToolEnabled(uuid);
        AutoToolProfile profile = autoToolManager.getProfile(uuid);
        OptionStatus prevent = profile.getOptionValue(AutoToolOption.PREVENT_TOOL_BREAK);

        if(!(prevent == OptionStatus.ALWAYS || (isEnabled && prevent == OptionStatus.ONLY_ON_AUTO_MODE)))
            return;

        if(!autoToolManager.wasWarned(player, item, 1))
            player.sendMessage(ConfigSettings.getItemWarnMessage(item.getType().toString(), 1));

        player.sendMessage(ConfigSettings.getItemCannotBreak());
        ev.setCancelled(true);
    }

}
