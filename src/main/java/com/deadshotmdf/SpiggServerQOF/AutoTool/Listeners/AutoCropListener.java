package com.deadshotmdf.SpiggServerQOF.AutoTool.Listeners;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.OptionStatus;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Utils.AutoToolUtils;
import com.deadshotmdf.SpiggServerQOF.SSQOF;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class AutoCropListener implements Listener {

    private final SSQOF main;
    private final AutoToolManager autoToolManager;
    private final BukkitScheduler scheduler;

    public AutoCropListener(SSQOF main, AutoToolManager autoToolManager) {
        this.main = main;
        this.autoToolManager = autoToolManager;
        this.scheduler = Bukkit.getScheduler();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent ev) {
        if(ev.isCancelled())
            return;

        Player player = ev.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        Block block = ev.getBlock();
        Material mat = block.getType();
        Material necessary = AutoToolUtils.getNecessary(mat);
        Location loc = block.getLocation();

        if(necessary == null)
            return;

        boolean isLongBlock = mat == Material.BAMBOO || mat == Material.SUGAR_CANE || mat == Material.KELP_PLANT;

        if(isLongBlock && loc.clone().subtract(0,1,0).getBlock().getType() == mat)
            return;

        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand(), player);

        UUID uuid = player.getUniqueId();
        if(!(autoToolManager.isAutoToolEnabled(uuid) && autoToolManager.getProfile(uuid).getOptionValue(AutoToolOption.AUTO_CROP_PLACE) == OptionStatus.ONLY_ON_AUTO_MODE))
            return;

        if(drops.isEmpty() || !hasEnough(drops, necessary, isLongBlock, loc, mat))
            return;

        block.setType(Material.AIR);
        World w = block.getWorld();
        drops.forEach(item -> w.dropItemNaturally(loc, item));
        scheduler.runTaskLater(main, () -> block.setType(mat), !drops.isEmpty() ? 0 : 3);
    }

    private boolean hasEnough(Collection<ItemStack> items, Material necessary, boolean isLongBlock, Location location, Material mat){
        if(isLongBlock && location.clone().add(0,1,0).getBlock().getType() == mat){
            items.clear();
            return true;
        }

        //Block drops aren't always a combined item, I needed to do something around that
        ItemStack drop = new ItemStack(necessary, 1);
        boolean foundAtLeastOne = false;
        Iterator<ItemStack> iterator = items.iterator();

        while(iterator.hasNext()){
            ItemStack item = iterator.next();

            if (item == null || item.getType() != necessary)
                continue;

            drop.setAmount(foundAtLeastOne ? drop.getAmount() + item.getAmount() : item.getAmount());
            foundAtLeastOne = true;
            iterator.remove();
        }

        if(!foundAtLeastOne)
            return false;

        boolean good = drop.getAmount() > 1;

        if(good)
            drop.setAmount(drop.getAmount() - 1);

        items.add(drop);
        return good;
    }
}
