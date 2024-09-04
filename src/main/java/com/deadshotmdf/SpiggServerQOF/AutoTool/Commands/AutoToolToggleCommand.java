package com.deadshotmdf.SpiggServerQOF.AutoTool.Commands;

import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects.SSQOFGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AutoToolToggleCommand implements CommandExecutor, TabCompleter {

    private final List<String> defaultValue ;
    private final AutoToolManager autoToolManager;

    public AutoToolToggleCommand(AutoToolManager autoToolManager) {
        this.autoToolManager = autoToolManager;
        this.defaultValue = Collections.singletonList("settings");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("This command may only be executed by a player");
            return true;
        }

        if(args.length > 0 && args[0].equalsIgnoreCase("settings")) {
            SSQOFGUI gui = autoToolManager.getProfile(((Player) sender).getUniqueId()).getGUI();

            if(gui != null)
                ((Player)sender).openInventory(gui.getInventory());

            return true;
        }

        if(autoToolManager.toggleAutoTool(((Player) sender).getUniqueId()))
            sender.sendMessage(ConfigSettings.getToggleAutoTool("ENABLED"));
        else
            sender.sendMessage(ConfigSettings.getToggleAutoTool("DISABLED"));

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length > 0)
            return defaultValue;

        return null;
    }
}
