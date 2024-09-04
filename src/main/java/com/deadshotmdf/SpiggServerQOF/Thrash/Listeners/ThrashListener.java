package com.deadshotmdf.SpiggServerQOF.Thrash.Listeners;

import com.deadshotmdf.SpiggServerQOF.Thrash.Managers.ThrashManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class ThrashListener implements Listener {

    private final ThrashManager thrashManager;

    public ThrashListener(ThrashManager thrashManager) {
        this.thrashManager = thrashManager;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent ev) {
        Inventory inventory = ev.getInventory();
        if(thrashManager.containsAndRemoveInventory(inventory))
            inventory.clear();
    }

}
