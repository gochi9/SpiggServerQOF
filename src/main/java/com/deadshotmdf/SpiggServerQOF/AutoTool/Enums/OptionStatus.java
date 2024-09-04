package com.deadshotmdf.SpiggServerQOF.AutoTool.Enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum OptionStatus {

    ALWAYS(ChatColor.DARK_GREEN + "Always", Material.LIME_STAINED_GLASS_PANE),
    ONLY_ON_AUTO_MODE(ChatColor.GOLD + "Only when auto-tool mode is enabled", Material.YELLOW_STAINED_GLASS_PANE),
    NEVER(ChatColor.DARK_RED + "Never", Material.RED_STAINED_GLASS_PANE),
    SUGGEST(ChatColor.DARK_PURPLE + "Suggest", Material.PURPLE_STAINED_GLASS_PANE),;

    private final String value;
    private final Material material;

    OptionStatus(String value, Material material) {
        this.value = value;
        this.material = material;
    }

    public String getValue() {
        return value;
    }

    public Material getMaterial() {
        return material;
    }

    public static OptionStatus getFromString(String value){
        if(value == null)
            return null;

        switch(value.toUpperCase()){
            case "ALWAYS":
                return ALWAYS;

            case "ONLY_ON_AUTO_MODE":
                return ONLY_ON_AUTO_MODE;

            case "NEVER":
                return NEVER;

            case "SUGGEST":
                return SUGGEST;

            default:
                return null;
        }
    }
}
