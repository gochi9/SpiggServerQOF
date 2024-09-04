package com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Managers;

import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects.SSQOFGUI;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.HashSet;

public class GUIManager {

    private final HashMap<Inventory, SSQOFGUI> guis;

    public GUIManager() {
        guis = new HashMap<>();
    }

    public void registerNewGUI(SSQOFGUI gui) {
        this.guis.put(gui.getInventory(), gui);
    }

    public void lockGUI(SSQOFGUI gui){
        if(gui == null)
            return;

        gui.setLocked(true);
        new HashSet<>(gui.getInventory().getViewers()).forEach(HumanEntity::closeInventory);
    }

    public void unregisterGUI(SSQOFGUI gui) {
        this.guis.remove(gui.getInventory());
    }

    public void removeAll(){
        for(SSQOFGUI gui : this.guis.values())
            lockGUI(gui);

        this.guis.clear();
    }

    public void onOpen(InventoryOpenEvent ev){
        SSQOFGUI gui = this.guis.get(ev.getInventory());

        if(gui != null)
            gui.onOpen(ev);
    }

    public void onClick(InventoryClickEvent ev){
        SSQOFGUI gui = this.guis.get(ev.getInventory());

        if(gui != null)
            gui.onClick(ev);
    }

    public void onClose(InventoryCloseEvent ev){
        SSQOFGUI gui = this.guis.get(ev.getInventory());

        if(gui != null)
            gui.onClose(ev);
    }

}
