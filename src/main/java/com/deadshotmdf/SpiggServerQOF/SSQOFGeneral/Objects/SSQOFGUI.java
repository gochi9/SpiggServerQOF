package com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public interface SSQOFGUI {

    void onOpen(InventoryOpenEvent ev);
    void onClick(InventoryClickEvent ev);
    void onClose(InventoryCloseEvent ev);
    void refreshInventory();
    Inventory getInventory();
    boolean isLocked();
    void setLocked(boolean locked);

}
