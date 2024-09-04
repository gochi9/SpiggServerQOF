package com.deadshotmdf.SpiggServerQOF.AutoTool.Objects;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.OptionStatus;
import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects.SSQOFGUI;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class AutoToolSettingsGUI implements SSQOFGUI {

    private final AutoToolProfile profile;
    private final NamespacedKey key;
    private final Inventory inventory;
    private boolean locked;

    public AutoToolSettingsGUI(AutoToolProfile profile, NamespacedKey key) {
        this.profile = profile;
        this.key = key;
        this.inventory = Bukkit.createInventory(null, ConfigSettings.getSettingsGUISize(), ConfigSettings.getSettingsGUIInventoryName());
        this.locked = false;
        this.refreshInventory();
    }

    @Override
    public void onOpen(InventoryOpenEvent ev) {
        if(locked)
            ev.setCancelled(true);
    }

    @Override
    public void onClick(InventoryClickEvent ev) {
        ev.setCancelled(true);

        if(ev.getRawSlot() >= ConfigSettings.getSettingsGUISize())
            return;

        Boolean forward = null;
        if(ev.getClick() == ClickType.LEFT)
            forward = true;
        else if(ev.getClick() == ClickType.RIGHT)
            forward = false;

        if(forward == null)
            return;

        ItemStack item = ev.getCurrentItem();
        if(item == null)
            return;

        ItemMeta meta = item.getItemMeta();

        if(meta == null)
            return;

        AutoToolOption option = AutoToolOption.getFromString(meta.getPersistentDataContainer().get(key, PersistentDataType.STRING));

        if(option == null || option == AutoToolOption.SUGGEST_MORE)
            return;

        OptionStatus status = profile.getOptionValue(option);

        if(status == null)
            return;

        OptionStatus newStatus = option.getNewStatus(status, forward);

        profile.setOptionValue(option, newStatus);
        meta.setLore(ConfigSettings.getButton(option).getLore(newStatus.getValue()));
        item.setItemMeta(meta);
        item.setType(newStatus.getMaterial());
    }

    @Override
    public void onClose(InventoryCloseEvent ev) {}

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

    @Override
    public void refreshInventory(){
        inventory.clear();

        for(int i = 0; i < ConfigSettings.getSettingsGUISize(); i++)
            inventory.setItem(i, GeneralUtils.getEmptyFillerNonClone());

        for(Map.Entry<AutoToolOption, SettingButton> entry : ConfigSettings.getButtons().entrySet())
            createButton(entry.getKey(), entry.getValue());
    }

    private void createButton(AutoToolOption option, SettingButton button){
        ItemStack item = GeneralUtils.getEmptyFiller();
        ItemMeta meta = item.getItemMeta();

        if(meta == null)
            return;

        meta.setDisplayName(button.getName());

        OptionStatus status = profile.getOptionValue(option);

        meta.setLore(button.getLore(status.getValue()));
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, option.toString());
        item.setItemMeta(meta);
        item.setType(status.getMaterial());
        inventory.setItem(button.getSlot(), item);
    }
}
