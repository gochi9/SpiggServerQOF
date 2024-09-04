package com.deadshotmdf.SpiggServerQOF.AutoTool.Managers;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.OptionStatus;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Objects.AutoToolProfile;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Objects.AutoToolSettingsGUI;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Utils.AutoToolUtils;

import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Managers.GUIManager;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects.InformationHolder;
import com.deadshotmdf.SpiggServerQOF.SSQOF;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects.SSQOFGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class AutoToolManager extends InformationHolder {

    private final SSQOF main;
    private final GUIManager guiManager;
    private final NamespacedKey key, guiKey;
    private final HashSet<UUID> autoToolEnabled;
    private final HashMap<UUID, AutoToolProfile> loadedProfiles;
    private final BukkitScheduler scheduler;

    public AutoToolManager(SSQOF main, GUIManager guiManager) {
        super(main, "AutoToolList.yml");
        this.main = main;
        this.guiManager = guiManager;
        this.key = new NamespacedKey(main, "SSQOFWarnMarginDFsd");
        this.guiKey = new NamespacedKey(main, "SSQOFGuiOptionButtonMarkFGDFg");
        this.autoToolEnabled = new HashSet<>();
        this.loadedProfiles = new HashMap<>();
        this.scheduler = Bukkit.getScheduler();

        for(Player player : Bukkit.getOnlinePlayers())
            loadPlayer(player.getUniqueId());
    }

    public boolean isAutoToolEnabled(UUID uuid) {
        return this.autoToolEnabled.contains(uuid);
    }

    public boolean toggleAutoTool(UUID uuid) {
        boolean b = !autoToolEnabled.remove(uuid);

        return b && autoToolEnabled.add(uuid);
    }

    public AutoToolProfile getProfile(UUID uuid) {
        AutoToolProfile profile = this.loadedProfiles.get(uuid);

        if(profile != null)
            return profile;

        profile = new AutoToolProfile(uuid);
        SSQOFGUI gui = new AutoToolSettingsGUI(profile, guiKey);
        guiManager.registerNewGUI(gui);
        profile.setGUI(gui);
        return profile;
    }

    public void onReloadConfig(){
        guiManager.removeAll();

        for(AutoToolProfile profile : loadedProfiles.values()){
            SSQOFGUI gui = new AutoToolSettingsGUI(profile, guiKey);
            guiManager.registerNewGUI(gui);
            profile.setGUI(gui);
        }
    }

    public boolean wasWarned(Player player, ItemStack item, int warnMargin){
        Integer value = AutoToolUtils.getMark(item, key);

        if(value != null && value == warnMargin)
            return true;

        ItemStack mark = AutoToolUtils.markItem(item, key, warnMargin);

        if(mark == null)
            return true;

        PlayerInventory inventory = player.getInventory();
        int slot = -1;
        for(int i = 0; i < inventory.getSize(); i++)
            if(item.equals(inventory.getItem(i)))
                slot = i;

        if(slot == -1)
            return true;

        if(player.isGliding())
            scheduler.runTaskLater(main, () -> player.setGliding(true), 3);

        inventory.setItem(slot, mark);
        return false;
    }

    public void setBlockIfFoundOrBucket(PlayerInventory inventory, Material mat, boolean mainHand, Integer limit){
        ItemStack[] item = inventory.getContents().clone();
        int size = limit != null ? limit : item.length;

        for(int slot = 0; slot < size; slot++){
            if((mainHand ? inventory.getHeldItemSlot() : 40) == slot)
                continue;

            ItemStack current = item[slot];

            if(current == null || mat != current.getType())
                continue;

            ItemMeta meta = current.getItemMeta();
            if(meta != null && current.hasItemMeta() && (meta.hasDisplayName() || meta.hasLore()))
                continue;

            if(limit != null){
                inventory.setHeldItemSlot(slot);
                return;
            }

            ItemStack clone = current.clone();
            inventory.setItem(slot, null);

            if(mainHand)
                inventory.setItemInMainHand(clone);
            else
                inventory.setItemInOffHand(clone);

            return;
        }
    }

    public void getBestToolHotbar(Player player, Material block){
        PlayerInventory inv = player.getInventory();
        int currentValue = 0;
        int slot = -1;
        int emptySlot = -1;
        int currentSlot = inv.getHeldItemSlot();

        for(int i = 0; i < 9; i++){
            ItemStack item = inv.getItem(i);

            ItemMeta meta;
            Material material;

            if(item == null || (meta = item.getItemMeta()) == null || !AutoToolUtils.isTool(material = item.getType())) {
                if(emptySlot == -1 || currentSlot == i)
                    emptySlot = i;

                continue;
            }

            if(!AutoToolUtils.isCorrectTool(block, material))
                continue;

            if((material.getMaxDurability() - ((Damageable) meta).getDamage()) < 2)
                continue;

            int value = AutoToolUtils.getValue(item, block);

            if(value < currentValue)
                continue;

            currentValue = value;
            slot = i;
        }

        if(slot == -1){
            if(emptySlot != -1)
                inv.setHeldItemSlot(emptySlot);
        }
        else
            inv.setHeldItemSlot(slot);
    }

    public void savePlayers(){
        for(Player player : Bukkit.getOnlinePlayers())
            savePlayer(player.getUniqueId(), false, false);
    }

    public void savePlayer(UUID uuid, boolean async, boolean saveOnce){
        AutoToolProfile profile = getProfile(uuid);
        boolean contained = autoToolEnabled.remove(uuid);
        SSQOFGUI gui = profile.getGUI();
        guiManager.lockGUI(gui);

        if(async)
            scheduler.runTask(main, () -> savePlayer(profile, uuid, saveOnce, contained, gui, async));
        else
            savePlayer(profile, uuid, saveOnce, contained, gui, async);

        if(!saveOnce)
            saveC();
    }

    private void savePlayer(AutoToolProfile profile, UUID uuid, boolean saveOnce, boolean contained, SSQOFGUI gui, boolean async){
        config.set("Players." + uuid.toString() + ".options.enabled", contained);
        profile.toFile(config);

        if(async)
            scheduler.runTask(main, () -> removeStuff(uuid, gui));
        else
            removeStuff(uuid, gui);

        if(saveOnce)
            saveC();
    }

    public void loadPlayer(UUID uuid){
        scheduler.runTaskAsynchronously(main, () -> {
            boolean enabled = config.getBoolean("Players." + uuid.toString() + ".options.enabled", false);
            ConfigurationSection sec = config.getConfigurationSection("Players." + uuid.toString() + ".options");

            scheduler.runTask(main, () -> triggerCommand(
                    sec == null ? new AutoToolProfile(uuid) : new AutoToolProfile(uuid, config, sec.getKeys(false)),
                    enabled,
                    uuid));
        });
    }

    private void triggerCommand(AutoToolProfile profile, boolean enabled, UUID uuid){
        Player player = Bukkit.getPlayer(uuid);

        if(player == null || !player.isOnline())
            return;

        loadedProfiles.putIfAbsent(uuid, profile);
        SSQOFGUI gui = new AutoToolSettingsGUI(profile, guiKey);
        guiManager.registerNewGUI(gui);
        profile.setGUI(gui);

        if(enabled && profile.getOptionValue(AutoToolOption.SAVE_AUTO_TOOL_STATUS_ON_REJOIN) == OptionStatus.ALWAYS)
            Bukkit.dispatchCommand(player, "sautotool");
    }

    private void removeStuff(UUID uuid, SSQOFGUI gui){
        loadedProfiles.remove(uuid);
        guiManager.unregisterGUI(gui);
    }

}
