package com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Listeners;

import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Managers.GUIManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GUIListener implements Listener {

    private final GUIManager guiManager;

    public GUIListener(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryOpen(InventoryOpenEvent ev) {
        if(!ev.isCancelled())
            guiManager.onOpen(ev);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent ev) {
        if(!ev.isCancelled())
            guiManager.onClick(ev);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent ev) {
        guiManager.onClose(ev);
    }

}
