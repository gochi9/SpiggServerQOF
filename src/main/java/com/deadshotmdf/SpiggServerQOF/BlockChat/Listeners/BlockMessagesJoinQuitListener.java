package com.deadshotmdf.SpiggServerQOF.BlockChat.Listeners;

import com.deadshotmdf.SpiggServerQOF.BlockChat.Commands.ManageBlockCommand;
import com.deadshotmdf.SpiggServerQOF.BlockChat.Managers.BlockChatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class BlockMessagesJoinQuitListener implements Listener {

    private final BlockChatManager blockChatManager;

    public BlockMessagesJoinQuitListener(BlockChatManager blockChatManager) {
        this.blockChatManager = blockChatManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev) {
        blockChatManager.loadPlayerAsync(ev.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent ev) {
        UUID uuid = ev.getPlayer().getUniqueId();
        ManageBlockCommand.executing.remove(uuid);
        blockChatManager.savePlayerAsync(uuid);
    }

}
