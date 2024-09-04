package com.deadshotmdf.SpiggServerQOF.Thrash.Managers;

import org.bukkit.inventory.Inventory;

import java.util.HashSet;

public class ThrashManager {

    private final HashSet<Inventory> invs;

    public ThrashManager() {
        invs = new HashSet<>();
    }

    public void addInventory(Inventory inv) {
        invs.add(inv);
    }

    public boolean containsAndRemoveInventory(Inventory inv) {
        return invs.remove(inv);
    }

}
