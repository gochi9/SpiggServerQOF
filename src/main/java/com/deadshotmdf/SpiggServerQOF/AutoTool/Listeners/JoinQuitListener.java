package com.deadshotmdf.SpiggServerQOF.AutoTool.Listeners;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    private final AutoToolManager autoToolManager;

    public JoinQuitListener(AutoToolManager autoToolManager) {
        this.autoToolManager = autoToolManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent ev) {
        autoToolManager.loadPlayer(ev.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent ev) {
        autoToolManager.savePlayer(ev.getPlayer().getUniqueId(), true, true);
    }

}
