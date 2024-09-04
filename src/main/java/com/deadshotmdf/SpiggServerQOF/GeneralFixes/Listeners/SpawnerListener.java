package com.deadshotmdf.SpiggServerQOF.GeneralFixes.Listeners;

import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.block.*;
import org.bukkit.event.*;

//J wanted this
//The spawner needs to drop empty as the players can craft eggs
public class SpawnerListener implements Listener{
	
    @EventHandler
    public void onSpawnerListener(final BlockBreakEvent ev) {
        Block block = ev.getBlock();
        
        if (block.getType() != Material.SPAWNER) 
            return;
        
        ItemStack item = ev.getPlayer().getInventory().getItemInMainHand();
        
        if (!item.hasItemMeta() || !item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))
            return;
        
        ev.setCancelled(true);
        block.setType(Material.AIR);
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SPAWNER));
    }
}
