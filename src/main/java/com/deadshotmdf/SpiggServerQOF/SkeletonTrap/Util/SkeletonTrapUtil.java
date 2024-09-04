package com.deadshotmdf.SpiggServerQOF.SkeletonTrap.Util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class SkeletonTrapUtil {

    private static ItemStack bow;
    private static ItemStack helmet;

    static {
        bow = new ItemStack(Material.BOW);
        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 5);
        bow.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 2);
        bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 3);

        helmet = new ItemStack(Material.DIAMOND_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        helmet.addUnsafeEnchantment(Enchantment.THORNS, 4);
    }

    public static ItemStack getBow() {
        return bow.clone();
    }

    public static ItemStack getHelmet() {
        return helmet.clone();
    }

}
