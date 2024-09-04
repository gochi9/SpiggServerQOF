package com.deadshotmdf.SpiggServerQOF.AutoTool.Listeners;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.OptionStatus;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class AutoToolBlockListener implements Listener {

    private final AutoToolManager autoToolManager;

    public AutoToolBlockListener(AutoToolManager autoToolManager) {
        this.autoToolManager = autoToolManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent ev) {
        if(ev.isCancelled())
            return;

        Player player = ev.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        ItemStack item = ev.getItemInHand();

        if(!item.getType().isBlock() || item.getAmount() > 1 || item.getType().toString().contains("SHULKER"))
            return;

        UUID uuid = player.getUniqueId();
        if(!(autoToolManager.isAutoToolEnabled(uuid) && autoToolManager.getProfile(uuid).getOptionValue(AutoToolOption.AUTO_BLOCK_REFILL) == OptionStatus.ONLY_ON_AUTO_MODE))
            return;

        PlayerInventory inventory = player.getInventory();
        autoToolManager.setBlockIfFoundOrBucket(inventory, item.getType(), !inventory.getItemInOffHand().equals(item), null);
    }

}
