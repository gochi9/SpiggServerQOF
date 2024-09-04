package com.deadshotmdf.SpiggServerQOF.BlockChat.Listeners;

import com.deadshotmdf.SpiggServerQOF.BlockChat.Managers.BlockChatManager;
import com.deadshotmdf.SpiggServerQOF.BlockChat.Objects.BlockProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Iterator;
import java.util.UUID;

public class BlockChatMessagesListener implements Listener {

    private final BlockChatManager blockChatManager;

    public BlockChatMessagesListener(BlockChatManager blockChatManager) {
        this.blockChatManager = blockChatManager;
    }

    @EventHandler
    public void onBlockChat(AsyncPlayerChatEvent ev) {
        UUID uuid = ev.getPlayer().getUniqueId();

        Iterator<Player> iterator = ev.getRecipients().iterator();
        while(iterator.hasNext()) {
            BlockProfile profile = blockChatManager.getBlockProfile(iterator.next().getUniqueId(), uuid);

            if(profile != null && !profile.isChatAllowed())
                iterator.remove();
        }
    }

}
