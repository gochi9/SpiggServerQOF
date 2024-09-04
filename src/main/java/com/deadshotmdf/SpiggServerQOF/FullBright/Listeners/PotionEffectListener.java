package com.deadshotmdf.SpiggServerQOF.FullBright.Listeners;

import com.deadshotmdf.SpiggServerQOF.FullBright.Managers.FullBrightManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectListener implements Listener{
	
	private FullBrightManager manager;
	
	public PotionEffectListener(FullBrightManager manager) {
		this.manager = manager;
	}
	
	@EventHandler
	public void onPotionEffect(EntityPotionEffectEvent ev) {
		if(ev.getEntityType() != EntityType.PLAYER)
			return;
		
		if(!manager.isFullBright(ev.getEntity().getUniqueId()))
			return;
		
		if((ev.getNewEffect() != null && ev.getNewEffect().getType().equals(PotionEffectType.NIGHT_VISION)) || (ev.getOldEffect() != null && ev.getOldEffect().getType().equals(PotionEffectType.NIGHT_VISION))) {
			ev.setCancelled(true);
			ev.setOverride(false);
		}
	}

}
