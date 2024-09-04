package com.deadshotmdf.SpiggServerQOF.AutoTool.Listeners;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.OptionStatus;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class BucketListener implements Listener {

    private final AutoToolManager autoToolManager;

    public BucketListener(AutoToolManager autoToolManager) {
        this.autoToolManager = autoToolManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent ev) {
        if(ev.isCancelled() || ev.useItemInHand() == Event.Result.DENY || ev.useInteractedBlock() == Event.Result.DENY)
            return;

        if(ev.getHand() != EquipmentSlot.HAND || (ev.getAction() != Action.RIGHT_CLICK_AIR && ev.getAction() != Action.RIGHT_CLICK_BLOCK))
            return;

        Player player = ev.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        ItemStack item = ev.getItem();

        if(item == null || item.getType() != Material.BUCKET)
            return;

        UUID uuid = player.getUniqueId();
        if(!(autoToolManager.isAutoToolEnabled(uuid) && autoToolManager.getProfile(uuid).getOptionValue(AutoToolOption.SELECT_BEST_TOOL) == OptionStatus.ONLY_ON_AUTO_MODE))
            return;

        Block block = ev.getPlayer().getTargetBlockExact (5, FluidCollisionMode.ALWAYS);

        if(block == null)
            return;

        Material mat = block.getType();

        if(mat != Material.WATER && mat != Material.LAVA)
            return;

        PlayerInventory inventory = player.getInventory();
        autoToolManager.setBlockIfFoundOrBucket(inventory, Material.BUCKET, !inventory.getItemInOffHand().equals(item), 9);
    }

}
