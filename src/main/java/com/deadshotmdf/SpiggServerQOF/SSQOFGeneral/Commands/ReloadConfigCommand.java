package com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Commands;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;
import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.SSQOF;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadConfigCommand implements CommandExecutor {

    private final SSQOF main;
    private final AutoToolManager autoToolManager;

    public ReloadConfigCommand(SSQOF main, AutoToolManager autoToolManager) {
        this.main = main;
        this.autoToolManager = autoToolManager;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player && !sender.hasPermission("spiggserverqof.reload")) {
            sender.sendMessage(ConfigSettings.getNoPermission());
            return true;
        }

        ConfigSettings.reloadConfig(main);
        autoToolManager.onReloadConfig();
        sender.sendMessage(ConfigSettings.getReloadConfig());
        return true;
    }
}
