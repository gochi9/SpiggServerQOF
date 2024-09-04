package com.deadshotmdf.SpiggServerQOF.Thrash.Commands;

import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.Thrash.Managers.ThrashManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ThrashCommand implements CommandExecutor {

    private final ThrashManager thrashManager;

    public ThrashCommand(ThrashManager thrashManager) {
        this.thrashManager = thrashManager;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("This command may only be executed by a player");
            return true;
        }

        Inventory inv = Bukkit.createInventory(null, 54, ConfigSettings.getThrashInventoryName());
        thrashManager.addInventory(inv);
        ((Player)sender).openInventory(inv);
        return true;
    }
}
