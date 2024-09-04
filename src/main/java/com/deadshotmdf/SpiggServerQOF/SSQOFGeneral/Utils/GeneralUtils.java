package com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class GeneralUtils {

    private final static ItemStack emptyFiller;
    public final static String ALLOWED = ChatColor.GREEN + "ALLOWED";
    public final static String DENIED = ChatColor.RED + "DENIED";
    public final static String BLOCKED = ChatColor.RED + "BLOCKED";

    static{
        emptyFiller = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = emptyFiller.getItemMeta();
        meta.setDisplayName(" ");
        emptyFiller.setItemMeta(meta);
    }

    public static ItemStack getEmptyFiller(){
        return emptyFiller.clone();
    }

    public static ItemStack getEmptyFillerNonClone(){
        return emptyFiller;
    }

    public static UUID getUUID(String s){
        try{
            return UUID.fromString(s);
        }
        catch(Throwable e){
            return null;
        }
    }

}
