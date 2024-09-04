package com.deadshotmdf.SpiggServerQOF.GeneralFixes.Listeners;

import com.deadshotmdf.SpiggServerQOF.GeneralFixes.Managers.GeneralFixesManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

//New players sometimes don't spawn in the correct spawn place
//This class will simply run the spawn command shortly after a new player joins for the first time
public class FixPeopleRoofListener implements Listener{
	
	private final GeneralFixesManager manager;
	
	public FixPeopleRoofListener(GeneralFixesManager manager) {
		this.manager = manager;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent ev) {
		Player player = ev.getPlayer();
		
		if(!player.hasPlayedBefore())
			manager.teleportPlayerDelay(player.getUniqueId(), 10);
	}

}
