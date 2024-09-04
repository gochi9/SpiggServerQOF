package com.deadshotmdf.SpiggServerQOF.AutoTool.Listeners;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.OptionStatus;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;

public class AutoToolSwitchListener implements Listener {

    private final AutoToolManager autoToolManager;

    public AutoToolSwitchListener(AutoToolManager autoToolManager) {
        this.autoToolManager = autoToolManager;
    }

    @EventHandler
    public void onToolSwitch(PlayerInteractEvent ev) {
        if(ev.getAction() != Action.LEFT_CLICK_BLOCK || ev.getHand() != EquipmentSlot.HAND)
            return;

        Player player = ev.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        UUID uuid = player.getUniqueId();
        if(!(autoToolManager.isAutoToolEnabled(uuid) && autoToolManager.getProfile(uuid).getOptionValue(AutoToolOption.SELECT_BEST_TOOL) == OptionStatus.ONLY_ON_AUTO_MODE))
            return;

        autoToolManager.getBestToolHotbar(player, ev.getClickedBlock().getType());
    }

}
