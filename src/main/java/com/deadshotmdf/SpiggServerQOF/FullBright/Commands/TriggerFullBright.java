package com.deadshotmdf.SpiggServerQOF.FullBright.Commands;

import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.FullBright.Managers.FullBrightManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TriggerFullBright implements CommandExecutor{

	private FullBrightManager manager;
	
	public TriggerFullBright(FullBrightManager manager) {
		this.manager = manager;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only a player may execute this command");
			return true;
		}
		
		manager.triggerFullBright((Player) sender);
		return true;
	}

}
