package com.deadshotmdf.SpiggServerQOF.BlockChat.Objects;

import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Managers.GUIManager;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects.SSQOFGUI;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class BlockChatGUI implements SSQOFGUI {

    private final Inventory inventory;
    private boolean locked;
    private final OfflinePlayer offlinePlayer;
    private final BlockProfile blockProfile;
    private final GUIManager guiManager;

    public BlockChatGUI(OfflinePlayer offlinePlayer, BlockProfile blockProfile, GUIManager guiManager) {
        this.inventory = Bukkit.createInventory(null, 9, ConfigSettings.getInventoryName(offlinePlayer.getName()));
        this.offlinePlayer = offlinePlayer;
        this.blockProfile = blockProfile;
        this.guiManager = guiManager;
        refreshInventory();
    }

    @Override
    public void onOpen(InventoryOpenEvent ev) {}

    @Override
    public void onClick(InventoryClickEvent ev) {
        ev.setCancelled(true);

        int slot = ev.getRawSlot();
        boolean b = false;

        if(!(slot == 2 || (b = (slot == 6))))
            return;

        if(!b)
            blockProfile.setChatAllowed(!blockProfile.isChatAllowed());
        else
            blockProfile.setPrivateChatAllowed(!blockProfile.isPrivateChatAllowed());

        refreshInventory();
    }

    @Override
    public void onClose(InventoryCloseEvent ev) {
        this.locked = true;
        this.guiManager.unregisterGUI(this);
    }

    @Override
    public void refreshInventory() {
        this.inventory.clear();

        for(int i = 0; i < 9; i++)
            this.inventory.setItem(i, GeneralUtils.getEmptyFillerNonClone());

        String name = offlinePlayer.getName();

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + name);
        meta.setOwningPlayer(offlinePlayer);
        head.setItemMeta(meta);

        ItemStack chat = new ItemStack(blockProfile.isChatAllowed() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE);
        ItemMeta chatMeta = chat.getItemMeta();
        chatMeta.setDisplayName(ConfigSettings.getChatStatus(name, blockProfile.isChatAllowed() ? GeneralUtils.ALLOWED : GeneralUtils.BLOCKED));
        chat.setItemMeta(chatMeta);

        ItemStack privateChat = new ItemStack(blockProfile.isPrivateChatAllowed() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE);
        ItemMeta privateChatMeta = privateChat.getItemMeta();
        privateChatMeta.setDisplayName(ConfigSettings.getPrivateStatus(name, blockProfile.isPrivateChatAllowed() ? GeneralUtils.ALLOWED : GeneralUtils.BLOCKED));
        privateChat.setItemMeta(privateChatMeta);

        this.inventory.setItem(2, chat);
        this.inventory.setItem(4, head);
        this.inventory.setItem(6, privateChat);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
