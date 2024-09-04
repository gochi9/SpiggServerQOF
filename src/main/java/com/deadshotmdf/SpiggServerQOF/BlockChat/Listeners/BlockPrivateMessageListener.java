package com.deadshotmdf.SpiggServerQOF.BlockChat.Listeners;

import com.deadshotmdf.SpiggServerQOF.BlockChat.Managers.BlockChatManager;
import com.deadshotmdf.SpiggServerQOF.BlockChat.Objects.BlockProfile;
import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import net.ess3.api.events.PrivateMessagePreSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

public class BlockPrivateMessageListener implements Listener {

    private final BlockChatManager blockChatManager;

    public BlockPrivateMessageListener(BlockChatManager blockChatManager) {
        this.blockChatManager = blockChatManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrivateMessage(PrivateMessagePreSendEvent ev){
        UUID sender = ev.getSender().getUUID(), receiver = ev.getRecipient().getUUID();

        if(ev.isCancelled() || staffMessage(sender) || staffMessage(receiver))
            return;

        BlockProfile blockProfile = blockChatManager.getBlockProfile(sender, receiver);
        BlockProfile blockProfile2 = blockChatManager.getBlockProfile(receiver, sender);

        if((blockProfile != null && !blockProfile.isPrivateChatAllowed()) || (blockProfile2 != null && !blockProfile2.isPrivateChatAllowed())){
            ev.setCancelled(true);
            ev.getSender().sendMessage(ConfigSettings.getMessageFailed());
        }
    }

    private boolean staffMessage(UUID uuid){
        try{
            return Bukkit.getPlayer(uuid).hasPermission("ssqof.staffmsg");
        }
        catch(Throwable e){
            return false;
        }
    }

}
