package com.deadshotmdf.SpiggServerQOF;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Objects.SettingButton;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigSettings {

    private static String reloadConfig;
    private static String noPermission;

    //Auto tool
    private static String itemCannotBreak;
    private static String itemWarnMessage;
    private static String toggleAutoTool;
    private static String settingsGUIInventoryName;
    private static int settingsGUISize;
    private static EnumMap<AutoToolOption, SettingButton> buttons;

    //Thrash
    private static String thrashInventoryName;

    //Full bright
    private static String turnOnFullBright;
    private static String turnOffFullBright;

    //General fixed
    private static boolean stopEnderCrystalDamageToOthers;

    //Block messages
    private static String inventoryName;
    private static String chatStatus;
    private static String privateStatus;
    private static String messageFailed;
    private static String blockCmdUsage;
    private static String cannotExecuteYet;
    private static String neverPlayed;
    private static String blockCmdError;


    public static String getReloadConfig(){
        return reloadConfig;
    }

    public static String getNoPermission(){
        return noPermission;
    }

    //Auto tool
    public static String getItemCannotBreak(){
        return itemCannotBreak;
    }

    public static String getItemWarnMessage(String item, int percentage){
        return itemWarnMessage.replace("{item}", item).replace("{percentage}", s(percentage));
    }

    public static String getToggleAutoTool(String status){
        return toggleAutoTool.replace("{status}", status);
    }

    public static String getSettingsGUIInventoryName(){
        return settingsGUIInventoryName;
    }

    public static int getSettingsGUISize(){
        return settingsGUISize;
    }

    public static SettingButton getButton(AutoToolOption option){
        return buttons.get(option);
    }

    public static EnumMap<AutoToolOption, SettingButton> getButtons(){
        return buttons;
    }

    //Thrash
    public static String getThrashInventoryName() {
        return thrashInventoryName;
    }


    //Full bright
    public static String getTurnOnFullBright(){
        return turnOnFullBright;
    }

    public static String getTurnOffFullBright(){
        return turnOffFullBright;
    }

    //General fixes
    public static boolean getStopEnderCrystalDamageToOthers() {
        return stopEnderCrystalDamageToOthers;
    }

    //Block messages
    public static String getInventoryName(String name) {
        return inventoryName.replace("{name}", name);
    }

    public static String getChatStatus(String name, String status) {
        return chatStatus.replace("{name}", name).replace("{status}", status);
    }

    public static String getPrivateStatus(String name, String status) {
        return privateStatus.replace("{name}", name).replace("{status}", status);
    }

    public static String getMessageFailed(){
        return messageFailed;
    }

    public static String getBlockCmdUsage() {
        return blockCmdUsage;
    }

    public static String getCannotExecuteYet() {
        return cannotExecuteYet;
    }

    public static String getNeverPlayed(String player) {
        return neverPlayed.replace("{player}", player);
    }

    public static String getBlockCmdError() {
        return blockCmdError;
    }

    public static void reloadConfig(SSQOF main){
        main.reloadConfig();
        main.saveDefaultConfig();

        FileConfiguration config = main.getConfig();

        reloadConfig = color(config.getString("reloadConfig"));
        noPermission = color(config.getString("noPermission"));

        //Auto tool
        itemCannotBreak = color(config.getString("itemCannotBreak"));
        itemWarnMessage = color(config.getString("itemWarnMessage"));
        toggleAutoTool = color(config.getString("toggleAutoTool"));
        settingsGUIInventoryName = color(config.getString("settingsGUIInventoryName"));
        settingsGUISize = config.getInt("settingsGUISize");

        //Thrash
        thrashInventoryName = color(config.getString("thrashInventoryName"));

        //Full bright
        turnOnFullBright = color(config.getString("turnOnFullBright"));
        turnOffFullBright = color(config.getString("turnOffFullBright"));

        //General fixes
        stopEnderCrystalDamageToOthers = config.getBoolean("stopEnderCrystalDamageToOthers", true);

        //Block messages
        inventoryName = color(config.getString("inventoryName"));
        chatStatus = color(config.getString("chatStatus"));
        privateStatus = color(config.getString("privateStatus"));
        messageFailed = color(config.getString("messageFailed"));
        blockCmdUsage = color(config.getString("blockCmdUsage"));
        cannotExecuteYet = color(config.getString("cannotExecuteYet"));
        neverPlayed = color(config.getString("neverPlayed"));
        blockCmdError = color(config.getString("blockCmdError"));

        populateButtons(config);
    }

    private static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s != null ? s : "invalid_string");
    }

    private static List<String> color(List<String> list) {
        return list == null || list.isEmpty() ? new ArrayList<>() : list.stream().map(ConfigSettings::color).collect(Collectors.toList());
    }

    private static String s(Object o){
        return o != null ? String.valueOf(o) : "invalid_string";
    }

    private static void populateButtons(FileConfiguration config){
        buttons = new EnumMap<>(AutoToolOption.class);
        ConfigurationSection sec = config.getConfigurationSection("settingsGUI");

        if(sec == null)
            return;

        Set<String> keys = sec.getKeys(false);

        if(keys.isEmpty())
            return;

        for(String key : keys){
            AutoToolOption option = AutoToolOption.getFromString(key);

            if(option != null)
                buttons.put(option, new SettingButton(
                        color(config.getString("settingsGUI." + key + ".displayName")),
                        color(config.getStringList("settingsGUI." + key + ".lore")),
                        config.getInt("settingsGUI." + key + ".slot")));
        }
    }

}
