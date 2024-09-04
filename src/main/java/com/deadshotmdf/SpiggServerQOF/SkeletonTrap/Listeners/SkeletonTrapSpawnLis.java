package com.deadshotmdf.SpiggServerQOF.SkeletonTrap.Listeners;

import com.deadshotmdf.SpiggServerQOF.SkeletonTrap.Util.SkeletonTrapUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SkeletonTrapSpawnLis implements Listener{
	
	@EventHandler
	public void onSkeletonTrap(CreatureSpawnEvent ev) {
		if(ev.getSpawnReason() != SpawnReason.TRAP)
			return;
		
		LivingEntity ent = ev.getEntity();
		World w = ent.getWorld();
		Location loc = ent.getLocation();
		
		for(int i = 0; i < 4; i++)
			spawnSkele(w, loc);
	}
	
	private void spawnSkele(World w, Location loc) {
		LivingEntity skele = w.spawn(loc, Skeleton.class, skeleton -> {
			EntityEquipment equip = skeleton.getEquipment();

			if(equip == null)
				return;

			equip.setItemInMainHand(SkeletonTrapUtil.getBow(), true);
			equip.setItemInMainHandDropChance(0.0F);
			equip.setHelmet(SkeletonTrapUtil.getHelmet(), true);
			equip.setHelmetDropChance(0.0F);
		});
		
		w.spawn(loc, ZombieHorse.class, horse -> {
			horse.setTamed(true);
			horse.addPassenger(skele);
			horse.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200,2, false, false, false));
		});
	}

}
