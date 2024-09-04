package com.deadshotmdf.SpiggServerQOF.GeneralFixes.Listeners;

import java.util.UUID;

import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.GeneralFixes.Managers.GeneralFixesManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

// End crystals were used to tpa kill players
// This listener should fix that issue, it should only damage the player that activated the end crystal, and nobody else
public class FixEndCrystalDmg implements Listener{
	
	private final GeneralFixesManager manager;
	
	public FixEndCrystalDmg(GeneralFixesManager manager) {
		this.manager = manager;
	}
	
	@EventHandler
	public void onCrystalTakeDamage(EntityDamageByEntityEvent ev) {
		if(!ConfigSettings.getStopEnderCrystalDamageToOthers())
			return;

		Entity damager = ev.getDamager();
		EntityType type = ev.getEntityType();
		UUID entUUID = ev.getEntity().getUniqueId();
		
		if(type == EntityType.ENDER_CRYSTAL) {
			manager.markPlayer(damager, entUUID);
			return;
		}
		
		if(type != EntityType.PLAYER || damager.getType() != EntityType.ENDER_CRYSTAL)
			return;
		
		UUID uuid = manager.getCrystalPlayer(damager.getUniqueId());
		
		if(uuid == null || !uuid.equals(entUUID))
			ev.setCancelled(true);
	}

}
