package com.deadshotmdf.SpiggServerQOF.GeneralFixes.Managers;

import com.deadshotmdf.SpiggServerQOF.SSQOF;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class GeneralFixesManager {

    private final SSQOF main;
    private final Logger logger;
    private final BukkitScheduler scheduler;
    private final HashMap<UUID, UUID> crystalPlayer;

    public GeneralFixesManager(SSQOF main) {
        this.main = main;
        this.logger = main.getLogger();
        this.scheduler = Bukkit.getScheduler();
        this.crystalPlayer = new HashMap<>();
    }

    ////////////////////////////////////////////////////////////////////////
    //// Section used in FixPeopleRoofListener class - Read notes there ////
    ////////////////////////////////////////////////////////////////////////

    public void teleportPlayerDelay(UUID uuid, long delayInTicks) {
        scheduler.runTaskLater(main, () -> teleportPlayerDelay(uuid), delayInTicks);
    }

    private void teleportPlayerDelay(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);

        if(player == null || !player.isOnline())
            return;

        String name = player.getName();
        logger.info(name + " teleport to spawn...");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + name);
    }

    /////////////////////
    //// Section end ////
    /////////////////////


    ///////////////////////////////////////////////////////////////////
    //// Section used in FixEndCrystalDmg class - Read notes there ////
    ///////////////////////////////////////////////////////////////////

    public void markPlayer(Entity damager, UUID entUUID){
        Player player = null;

        if(damager instanceof Player)
            player = (Player) damager;

        else if(damager instanceof Projectile){
            ProjectileSource source = ((Projectile) damager).getShooter();

            if(source instanceof Player)
                player = (Player) source;
        }

        if(player == null)
            return;

        crystalPlayer.put(entUUID, player.getUniqueId());
        scheduler.runTaskLater(main, () -> crystalPlayer.remove(entUUID), 100);
    }

    public UUID getCrystalPlayer(UUID uuid) {
        return crystalPlayer.get(uuid);
    }

    /////////////////////
    //// Section end ////
    /////////////////////


}
