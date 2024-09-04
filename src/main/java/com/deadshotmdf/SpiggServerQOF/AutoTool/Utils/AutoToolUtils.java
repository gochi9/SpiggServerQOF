package com.deadshotmdf.SpiggServerQOF.AutoTool.Utils;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.ToolType;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Objects.EnumMapWrapper;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Objects.Tool;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class AutoToolUtils {

    private static final EnumMap<Material, Tool> tools;
    private static final HashMap<Enchantment, Integer> getEnchantValues;
    private static final EnumMapWrapper<Material, Tool> getRequiredTool;
    private static final Set<Material> requiresSilkTouch;
    private static final EnumMap<Material, Material> crops;

    static{
        tools = new EnumMap<>(Material.class);

        Tool woodPickaxe = new Tool(ToolType.PICKAXE, 1, 1), stonePickax = new Tool(ToolType.PICKAXE, 2, 3), ironPickaxe = new Tool(ToolType.PICKAXE, 3, 5), goldPickaxe = new Tool(ToolType.PICKAXE, 1, 11), diamondPickaxe = new Tool(ToolType.PICKAXE, 5, 11), netheritePickaxe = new Tool(ToolType.PICKAXE, 6, 14);
        tools.put(Material.WOODEN_PICKAXE, woodPickaxe);
        tools.put(Material.STONE_PICKAXE, stonePickax);
        tools.put(Material.IRON_PICKAXE, ironPickaxe);
        tools.put(Material.GOLDEN_PICKAXE, goldPickaxe);
        tools.put(Material.DIAMOND_PICKAXE, diamondPickaxe);
        tools.put(Material.NETHERITE_PICKAXE, netheritePickaxe);

        Tool woodAxe = new Tool(ToolType.AXE, 1, 1), stoneAxe = new Tool(ToolType.AXE, 2, 3), ironAxe = new Tool(ToolType.AXE, 3, 5), goldAxe = new Tool(ToolType.AXE, 1, 11), diamondAxe = new Tool(ToolType.AXE, 5, 11), netheriteAxe = new Tool(ToolType.AXE, 6, 14);
        tools.put(Material.WOODEN_AXE, woodAxe);
        tools.put(Material.STONE_AXE, stoneAxe);
        tools.put(Material.IRON_AXE, ironAxe);
        tools.put(Material.GOLDEN_AXE, goldAxe);
        tools.put(Material.DIAMOND_AXE, diamondAxe);
        tools.put(Material.NETHERITE_AXE, netheriteAxe);

        Tool woodShovel = new Tool(ToolType.SHOVEL, 1, 1), stoneShovel = new Tool(ToolType.SHOVEL, 2, 3), ironShovel = new Tool(ToolType.SHOVEL, 3, 5), goldShovel = new Tool(ToolType.SHOVEL, 1, 11), diamondShovel = new Tool(ToolType.SHOVEL, 5, 11), netheriteShovel = new Tool(ToolType.SHOVEL, 6, 14);
        tools.put(Material.WOODEN_SHOVEL, woodShovel);
        tools.put(Material.STONE_SHOVEL, stoneShovel);
        tools.put(Material.IRON_SHOVEL, ironShovel);
        tools.put(Material.GOLDEN_SHOVEL, goldShovel);
        tools.put(Material.DIAMOND_SHOVEL, diamondShovel);
        tools.put(Material.NETHERITE_SHOVEL, netheriteShovel);

        Tool woodSword = new Tool(ToolType.SWORD, 1, 1), stoneSword = new Tool(ToolType.SWORD, 2, 3), ironSword = new Tool(ToolType.SWORD, 3, 5), goldSword = new Tool(ToolType.SWORD, 1, 11), diamondSword = new Tool(ToolType.SWORD, 5, 11), netheriteSword = new Tool(ToolType.SWORD, 6, 14);
        tools.put(Material.WOODEN_SWORD, woodSword);
        tools.put(Material.STONE_SWORD, stoneSword);
        tools.put(Material.IRON_SWORD, ironSword);
        tools.put(Material.GOLDEN_SWORD, goldSword);
        tools.put(Material.DIAMOND_SWORD, diamondSword);
        tools.put(Material.NETHERITE_SWORD, netheriteSword);

        Tool woodHoe = new Tool(ToolType.HOE, 1, 1), stoneHoe = new Tool(ToolType.HOE, 2, 3), ironHoe = new Tool(ToolType.HOE, 3, 5), goldHoe = new Tool(ToolType.HOE, 1, 11), diamondHoe = new Tool(ToolType.HOE, 5, 11), netheriteHoe = new Tool(ToolType.HOE, 6, 14);
        tools.put(Material.WOODEN_HOE, woodHoe);
        tools.put(Material.STONE_HOE, stoneHoe);
        tools.put(Material.IRON_HOE, ironHoe);
        tools.put(Material.GOLDEN_HOE, goldHoe);
        tools.put(Material.DIAMOND_HOE, diamondHoe);
        tools.put(Material.NETHERITE_HOE, netheriteHoe);

        Tool shears = new Tool(ToolType.SHEARS, 0, 250), bucket = new Tool(ToolType.BUCKET, 0, 1), any = new Tool(ToolType.ANY, 100, 1);
        tools.put(Material.SHEARS, shears);
        tools.put(Material.BUCKET, bucket);

        getEnchantValues = new HashMap<>();
        getEnchantValues.put(Enchantment.DURABILITY, 1);
        getEnchantValues.put(Enchantment.LOOT_BONUS_BLOCKS, 1);
        getEnchantValues.put(Enchantment.DIG_SPEED, 3);

        getRequiredTool = new EnumMapWrapper<>(Material.class);
        //Axe


        for(Material material : Material.values()){
            String name = material.toString();
            if(name.contains("VINE") || name.contains("HYPHAE") || name.endsWith("_BANNER") || name.endsWith("_STEM") || name.endsWith("_WOOD"))
                getRequiredTool.put(material, woodAxe);
        }

        getRequiredTool.put(Material.BEE_NEST, woodAxe);
        getRequiredTool.put(Material.CARVED_PUMPKIN, woodAxe);
        getRequiredTool.put(Material.COCOA, woodAxe);
        getRequiredTool.put(Material.GLOW_LICHEN, woodAxe);
        getRequiredTool.put(Material.JACK_O_LANTERN, woodAxe);
        getRequiredTool.put(Material.MELON, woodAxe);
        getRequiredTool.put(Material.RED_MUSHROOM_BLOCK, woodAxe);
        getRequiredTool.put(Material.BROWN_MUSHROOM_BLOCK, woodAxe);
        getRequiredTool.put(Material.PUMPKIN, woodAxe);
        getRequiredTool.put(Material.BAMBOO_BLOCK, woodAxe);
        getRequiredTool.put(Material.BAMBOO, woodAxe);
        getRequiredTool.put(Material.BAMBOO_MOSAIC, woodAxe);
        getRequiredTool.put(Material.BARREL, woodAxe);
        getRequiredTool.put(Material.BEEHIVE, woodAxe);
        getRequiredTool.put(Material.BOOKSHELF, woodAxe);
        getRequiredTool.put(Material.CAMPFIRE, woodAxe);
        getRequiredTool.put(Material.CARTOGRAPHY_TABLE, woodAxe);
        getRequiredTool.put(Material.CHEST, woodAxe);
        getRequiredTool.put(Material.CHISELED_BOOKSHELF, woodAxe);
        getRequiredTool.put(Material.COMPOSTER, woodAxe);
        getRequiredTool.put(Material.CRAFTING_TABLE, woodAxe);
        getRequiredTool.put(Material.DAYLIGHT_DETECTOR, woodAxe);
        fillWithTag(Tag.WOODEN_FENCES, woodAxe, null, null);
        fillWithTag(Tag.FENCE_GATES, woodAxe, null, null);
        getRequiredTool.put(Material.FLETCHING_TABLE, woodAxe);
        fillWithTag(Tag.ALL_HANGING_SIGNS, woodAxe, null, null);
        getRequiredTool.put(Material.JUKEBOX, woodAxe);
        getRequiredTool.put(Material.LADDER, woodAxe);
        getRequiredTool.put(Material.LECTERN, woodAxe);
        fillWithTag(Tag.LOGS, woodAxe, null, null);
        getRequiredTool.put(Material.LOOM, woodAxe);
        getRequiredTool.put(Material.MANGROVE_ROOTS, woodAxe);
        getRequiredTool.put(Material.NOTE_BLOCK, woodAxe);
        fillWithTag(Tag.PLANKS, woodAxe, null, null);
        fillWithTag(Tag.ALL_SIGNS, woodAxe, null, null);
        getRequiredTool.put(Material.SOUL_CAMPFIRE, woodAxe);
        getRequiredTool.put(Material.SMITHING_TABLE, woodAxe);
        getRequiredTool.put(Material.TRAPPED_CHEST, woodAxe);
        fillWithTag(Tag.WOODEN_BUTTONS, woodAxe, null, null);
        fillWithTag(Tag.WOODEN_DOORS, woodAxe, null, null);
        fillWithTag(Tag.WOODEN_PRESSURE_PLATES, woodAxe, null, null);
        fillWithTag(Tag.WOODEN_SLABS, woodAxe, null, null);
        fillWithTag(Tag.WOODEN_STAIRS, woodAxe, null, null);
        fillWithTag(Tag.WOODEN_TRAPDOORS, woodAxe, null, null);

        //Pickaxes
        //Wood

        for(Material material : Material.values()){
            String name = material.toString();
            if((name.contains("CONCRETE") && !name.contains("POWDER")) || name.endsWith("_AMETHYST_BUD") || name.endsWith("TERRACOTTA"))
                getRequiredTool.put(material, woodPickaxe);
        }

        getRequiredTool.put(Material.BLUE_ICE, woodPickaxe);
        getRequiredTool.put(Material.ICE, woodPickaxe);
        getRequiredTool.put(Material.PACKED_ICE, woodPickaxe);
        getRequiredTool.put(Material.ANVIL, woodPickaxe);
        getRequiredTool.put(Material.BELL, woodPickaxe);
        getRequiredTool.put(Material.REDSTONE_BLOCK, woodPickaxe);
        getRequiredTool.put(Material.BREWING_STAND, woodPickaxe);
        getRequiredTool.put(Material.CAULDRON, woodPickaxe);
        getRequiredTool.put(Material.CHAIN, woodPickaxe);
        getRequiredTool.put(Material.HOPPER, woodPickaxe);
        getRequiredTool.put(Material.IRON_BARS, woodPickaxe);
        getRequiredTool.put(Material.IRON_DOOR, woodPickaxe);
        getRequiredTool.put(Material.IRON_TRAPDOOR, woodPickaxe);
        getRequiredTool.put(Material.LANTERN, woodPickaxe);
        getRequiredTool.put(Material.SOUL_LANTERN, woodPickaxe);
        getRequiredTool.put(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, woodPickaxe);
        getRequiredTool.put(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, woodPickaxe);
        getRequiredTool.put(Material.PISTON, woodPickaxe);
        getRequiredTool.put(Material.STICKY_PISTON, woodPickaxe);
        getRequiredTool.put(Material.CONDUIT, woodPickaxe);
        getRequiredTool.put(Material.SHULKER_BOX, woodPickaxe);
        getRequiredTool.put(Material.ACTIVATOR_RAIL, woodPickaxe);
        getRequiredTool.put(Material.DETECTOR_RAIL, woodPickaxe);
        getRequiredTool.put(Material.POWERED_RAIL, woodPickaxe);
        getRequiredTool.put(Material.RAIL, woodPickaxe);
        getRequiredTool.put(Material.AMETHYST_CLUSTER, woodPickaxe);
        getRequiredTool.put(Material.ANDESITE, woodPickaxe);
        getRequiredTool.put(Material.BASALT, woodPickaxe);
        getRequiredTool.put(Material.POLISHED_BASALT, woodPickaxe);
        getRequiredTool.put(Material.SMOOTH_BASALT, woodPickaxe);
        getRequiredTool.put(Material.BLACKSTONE, woodPickaxe);
        getRequiredTool.put(Material.BLAST_FURNACE, woodPickaxe);
        getRequiredTool.put(Material.AMETHYST_BLOCK, woodPickaxe);
        getRequiredTool.put(Material.COAL_BLOCK, woodPickaxe);
        getRequiredTool.put(Material.QUARTZ_BLOCK, woodPickaxe);
        getRequiredTool.put(Material.BONE_BLOCK, woodPickaxe);
        getRequiredTool.put(Material.BRICKS, woodPickaxe);
        getRequiredTool.put(Material.BUDDING_AMETHYST, woodPickaxe);
        getRequiredTool.put(Material.COAL_ORE, woodPickaxe);
        getRequiredTool.put(Material.COBBLED_DEEPSLATE, woodPickaxe);
        getRequiredTool.put(Material.COBBLESTONE, woodPickaxe);
        fillWithTag(Tag.CORAL_BLOCKS, woodPickaxe, null, null);
        getRequiredTool.put(Material.DARK_PRISMARINE, woodPickaxe);
        getRequiredTool.put(Material.DEEPSLATE, woodPickaxe);
        getRequiredTool.put(Material.DEEPSLATE_COAL_ORE, woodPickaxe);
        getRequiredTool.put(Material.DIORITE, woodPickaxe);
        getRequiredTool.put(Material.DISPENSER, woodPickaxe);
        getRequiredTool.put(Material.DRIPSTONE_BLOCK, woodPickaxe);
        getRequiredTool.put(Material.DROPPER, woodPickaxe);
        getRequiredTool.put(Material.ENCHANTING_TABLE, woodPickaxe);
        getRequiredTool.put(Material.END_STONE, woodPickaxe);
        getRequiredTool.put(Material.ENDER_CHEST, woodPickaxe);
        getRequiredTool.put(Material.FURNACE, woodPickaxe);
        getRequiredTool.put(Material.GILDED_BLACKSTONE, woodPickaxe);
        getRequiredTool.put(Material.GRANITE, woodPickaxe);
        getRequiredTool.put(Material.GRINDSTONE, woodPickaxe);
        getRequiredTool.put(Material.LODESTONE, woodPickaxe);
        getRequiredTool.put(Material.SPAWNER, woodPickaxe);
        getRequiredTool.put(Material.MOSSY_COBBLESTONE, woodPickaxe);
        getRequiredTool.put(Material.MOSSY_STONE_BRICKS, woodPickaxe);
        getRequiredTool.put(Material.MUD_BRICKS, woodPickaxe);
        getRequiredTool.put(Material.NETHER_BRICKS, woodPickaxe);
        getRequiredTool.put(Material.NETHER_BRICK_FENCE, woodPickaxe);
        getRequiredTool.put(Material.NETHER_GOLD_ORE, woodPickaxe);
        getRequiredTool.put(Material.NETHER_QUARTZ_ORE, woodPickaxe);
        getRequiredTool.put(Material.NETHERRACK, woodPickaxe);
        getRequiredTool.put(Material.OBSERVER, woodPickaxe);
        getRequiredTool.put(Material.PACKED_MUD, woodPickaxe);
        getRequiredTool.put(Material.PRISMARINE, woodPickaxe);
        getRequiredTool.put(Material.PRISMARINE_BRICKS, woodPickaxe);
        getRequiredTool.put(Material.POINTED_DRIPSTONE, woodPickaxe);
        getRequiredTool.put(Material.POLISHED_ANDESITE, woodPickaxe);
        getRequiredTool.put(Material.POLISHED_BLACKSTONE, woodPickaxe);
        getRequiredTool.put(Material.POLISHED_BLACKSTONE_BRICKS, woodPickaxe);
        getRequiredTool.put(Material.POLISHED_BLACKSTONE_BUTTON, woodPickaxe);
        getRequiredTool.put(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE, woodPickaxe);
        getRequiredTool.put(Material.POLISHED_DIORITE, woodPickaxe);
        getRequiredTool.put(Material.POLISHED_GRANITE, woodPickaxe);
        getRequiredTool.put(Material.RED_SANDSTONE, woodPickaxe);
        getRequiredTool.put(Material.SANDSTONE, woodPickaxe);
        getRequiredTool.put(Material.SMOKER, woodPickaxe);
        getRequiredTool.put(Material.STONECUTTER, woodPickaxe);
        fillWithTag(Tag.SLABS, woodPickaxe, Tag.WOODEN_SLABS, "COPPER_SLAB");
        getRequiredTool.put(Material.SMOOTH_STONE, woodPickaxe);
        fillWithTag(Tag.STAIRS, woodPickaxe, Tag.WOODEN_STAIRS, "COPPER_STAIRS");
        getRequiredTool.put(Material.STONE, woodPickaxe);
        getRequiredTool.put(Material.STONE_BRICKS, woodPickaxe);
        getRequiredTool.put(Material.STONE_BUTTON, woodPickaxe);
        getRequiredTool.put(Material.STONE_PRESSURE_PLATE, woodPickaxe);
        getRequiredTool.put(Material.TERRACOTTA, woodPickaxe);
        fillWithTag(Tag.WALLS, woodPickaxe, null, null);

        //Stone

        for(Material material : Material.values()){
            String name = material.toString();
            if(name.endsWith("COPPER_BLOCK"))
                getRequiredTool.put(material, stonePickax);
        }

        getRequiredTool.put(Material.IRON_BLOCK, stonePickax);
        getRequiredTool.put(Material.LAPIS_BLOCK, stonePickax);
        getRequiredTool.put(Material.CUT_COPPER, stonePickax);
        getRequiredTool.put(Material.CUT_COPPER_SLAB, stonePickax);
        getRequiredTool.put(Material.CUT_COPPER_STAIRS, stonePickax);
        getRequiredTool.put(Material.LIGHTNING_ROD, stonePickax);
        getRequiredTool.put(Material.RAW_IRON_BLOCK, stonePickax);
        getRequiredTool.put(Material.COPPER_ORE, stonePickax);
        getRequiredTool.put(Material.DEEPSLATE_COPPER_ORE, stonePickax);
        getRequiredTool.put(Material.DEEPSLATE_IRON_ORE, stonePickax);
        getRequiredTool.put(Material.DEEPSLATE_LAPIS_ORE, stonePickax);
        getRequiredTool.put(Material.IRON_ORE, stonePickax);
        getRequiredTool.put(Material.LAPIS_ORE, stonePickax);

        //Iron
        getRequiredTool.put(Material.DIAMOND_BLOCK, ironPickaxe);
        getRequiredTool.put(Material.EMERALD_BLOCK, ironPickaxe);
        getRequiredTool.put(Material.GOLD_BLOCK, ironPickaxe);
        getRequiredTool.put(Material.RAW_GOLD_BLOCK, ironPickaxe);
        getRequiredTool.put(Material.DEEPSLATE_DIAMOND_ORE, ironPickaxe);
        getRequiredTool.put(Material.DEEPSLATE_EMERALD_ORE, ironPickaxe);
        getRequiredTool.put(Material.DEEPSLATE_GOLD_ORE, ironPickaxe);
        getRequiredTool.put(Material.DEEPSLATE_REDSTONE_ORE, ironPickaxe);
        getRequiredTool.put(Material.DIAMOND_ORE, ironPickaxe);
        getRequiredTool.put(Material.EMERALD_ORE, ironPickaxe);
        getRequiredTool.put(Material.GOLD_ORE, ironPickaxe);
        getRequiredTool.put(Material.REDSTONE_ORE, ironPickaxe);

        //Diamond
        getRequiredTool.put(Material.NETHERITE_BLOCK, diamondPickaxe);
        getRequiredTool.put(Material.ANCIENT_DEBRIS, diamondPickaxe);
        getRequiredTool.put(Material.CRYING_OBSIDIAN, diamondPickaxe);
        getRequiredTool.put(Material.OBSIDIAN, diamondPickaxe);
        getRequiredTool.put(Material.RESPAWN_ANCHOR, diamondPickaxe);

        //
        //Shovel

        for(Material material : Material.values()){
            String name = material.toString();
            if((name.contains("CONCRETE") && name.contains("POWDER")))
                getRequiredTool.put(material, woodShovel);
        }

        getRequiredTool.put(Material.CLAY, woodShovel);
        getRequiredTool.put(Material.COARSE_DIRT, woodShovel);
        getRequiredTool.put(Material.DIRT, woodShovel);
        getRequiredTool.put(Material.DIRT_PATH, woodShovel);
        getRequiredTool.put(Material.FARMLAND, woodShovel);
        getRequiredTool.put(Material.GRASS_BLOCK, woodShovel);
        getRequiredTool.put(Material.GRAVEL, woodShovel);
        getRequiredTool.put(Material.MUD, woodShovel);
        getRequiredTool.put(Material.MUDDY_MANGROVE_ROOTS, woodShovel);
        getRequiredTool.put(Material.MYCELIUM, woodShovel);
        getRequiredTool.put(Material.PODZOL, woodShovel);
        getRequiredTool.put(Material.RED_SAND, woodShovel);
        getRequiredTool.put(Material.ROOTED_DIRT, woodShovel);
        getRequiredTool.put(Material.SAND, woodShovel);
        getRequiredTool.put(Material.SOUL_SAND, woodShovel);
        getRequiredTool.put(Material.SOUL_SOIL, woodShovel);
        getRequiredTool.put(Material.SUSPICIOUS_GRAVEL, woodShovel);
        getRequiredTool.put(Material.SUSPICIOUS_SAND, woodShovel);
        getRequiredTool.put(Material.SNOW, woodShovel);
        getRequiredTool.put(Material.SNOW_BLOCK, woodShovel);


        //Sword
        getRequiredTool.put(Material.COBWEB, woodSword);
        getRequiredTool.put(Material.BAMBOO, woodSword);

        //Hoe
        getRequiredTool.put(Material.CALIBRATED_SCULK_SENSOR, woodHoe);
        getRequiredTool.put(Material.HAY_BLOCK, woodHoe);
        fillWithTag(Tag.LEAVES, woodHoe, null, null);
        getRequiredTool.put(Material.MOSS_BLOCK, woodHoe);
        getRequiredTool.put(Material.MOSS_CARPET, woodHoe);
        getRequiredTool.put(Material.NETHER_WART_BLOCK, woodHoe);
        getRequiredTool.put(Material.SCULK, woodHoe);
        getRequiredTool.put(Material.SCULK_CATALYST, woodHoe);
        getRequiredTool.put(Material.SCULK_SENSOR, woodHoe);
        getRequiredTool.put(Material.SCULK_SHRIEKER, woodHoe);
        getRequiredTool.put(Material.SCULK_VEIN, woodHoe);
        getRequiredTool.put(Material.SHROOMLIGHT, woodHoe);
        getRequiredTool.put(Material.SPONGE, woodHoe);
        getRequiredTool.put(Material.TARGET, woodHoe);
        getRequiredTool.put(Material.WARPED_WART_BLOCK, woodHoe);
        getRequiredTool.put(Material.WET_SPONGE, woodHoe);

        //Shears
        for(Material material : Material.values()){
            String name = material.toString();
            if(name.endsWith("_GRASS"))
                getRequiredTool.put(material, shears);
        }

        fillWithTag(Tag.LEAVES, shears, null, null);
        getRequiredTool.put(Material.COBWEB, shears);

        //Any

        for(Material material : Material.values()){
            String name = material.toString();
            if(name.contains("GLASS") || name.contains("FROGLIGHT"))
                getRequiredTool.put(material, woodShovel);
        }

        getRequiredTool.put(Material.BEACON, any);
        getRequiredTool.put(Material.GLASS, any);
        getRequiredTool.put(Material.GLASS_PANE, any);
        getRequiredTool.put(Material.GLOWSTONE, any);
        getRequiredTool.put(Material.REDSTONE_LAMP, any);
        getRequiredTool.put(Material.SEA_LANTERN, any);
        fillWithTag(Tag.BEDS, shears, null, null);
        getRequiredTool.put(Material.CAKE, any);
        fillWithTag(Tag.WOOL_CARPETS, shears, null, null);
        getRequiredTool.put(Material.DRAGON_EGG, any);
        getRequiredTool.put(Material.FROSTED_ICE, any);
        getRequiredTool.put(Material.PLAYER_HEAD, any);
        getRequiredTool.put(Material.HONEYCOMB_BLOCK, any);
        getRequiredTool.put(Material.REINFORCED_DEEPSLATE, any);
        getRequiredTool.put(Material.SNIFFER_EGG, any);
        getRequiredTool.put(Material.TURTLE_EGG, any);


        //Silk touch values
        requiresSilkTouch = EnumSet.noneOf(Material.class);

        for(Material material : Material.values()){
            String name = material.toString();
            if(name.contains("AMETHYST_BUD") || name.contains("CORAL_BLOCK") || name.endsWith("_LEAVES") || name.contains("SCULK") || name.contains("GLASS"))
                requiresSilkTouch.add(material);
        }

        requiresSilkTouch.add(Material.MELON);
        requiresSilkTouch.add(Material.BEE_NEST);
        requiresSilkTouch.add(Material.BROWN_MUSHROOM_BLOCK);
        requiresSilkTouch.add(Material.RED_MUSHROOM_BLOCK);
        requiresSilkTouch.add(Material.MUSHROOM_STEM);
        requiresSilkTouch.add(Material.BOOKSHELF);
        requiresSilkTouch.add(Material.CAMPFIRE);
        requiresSilkTouch.add(Material.SOUL_CAMPFIRE);
        requiresSilkTouch.add(Material.CHISELED_BOOKSHELF);

        requiresSilkTouch.add(Material.BLUE_ICE);
        requiresSilkTouch.add(Material.ICE);
        requiresSilkTouch.add(Material.PACKED_ICE);
        requiresSilkTouch.add(Material.AMETHYST_CLUSTER);
        requiresSilkTouch.add(Material.ENDER_CHEST);
        requiresSilkTouch.add(Material.COBWEB);

        requiresSilkTouch.add(Material.CLAY);
        requiresSilkTouch.add(Material.GRASS_BLOCK);
        requiresSilkTouch.add(Material.MYCELIUM);
        requiresSilkTouch.add(Material.PODZOL);

        requiresSilkTouch.add(Material.GLOWSTONE);
        requiresSilkTouch.add(Material.SEA_LANTERN);
        requiresSilkTouch.add(Material.TURTLE_EGG);
        requiresSilkTouch.add(Material.DECORATED_POT);
        requiresSilkTouch.add(Material.TWISTING_VINES);
        requiresSilkTouch.add(Material.WEEPING_VINES);
        requiresSilkTouch.add(Material.TWISTING_VINES_PLANT);
        requiresSilkTouch.add(Material.WEEPING_VINES_PLANT);

        crops = new EnumMap<>(Material.class);
        crops.put(Material.WHEAT, Material.WHEAT_SEEDS);
        crops.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
        crops.put(Material.CARROTS, Material.CARROT);
        crops.put(Material.POTATOES, Material.POTATO);
        crops.put(Material.MELON_STEM, Material.MELON_SEEDS);
        crops.put(Material.ATTACHED_MELON_STEM, Material.MELON_SEEDS);
        crops.put(Material.PUMPKIN_STEM, Material.PUMPKIN_SEEDS);
        crops.put(Material.ATTACHED_PUMPKIN_STEM, Material.PUMPKIN_SEEDS);
        crops.put(Material.BAMBOO, Material.BAMBOO);
        crops.put(Material.COCOA, Material.COCOA_BEANS);
        crops.put(Material.SUGAR_CANE, Material.SUGAR_CANE);
        crops.put(Material.CACTUS, Material.CACTUS);
        crops.put(Material.KELP_PLANT, Material.KELP);
        crops.put(Material.NETHER_WART, Material.NETHER_WART);
    }

    public static boolean isCorrectTool(Material block, Material inHand){
        List<Tool> required = getRequiredTool.get(block);
        Tool tool = tools.get(inHand);

        if((required == null || required.isEmpty()) && tool == null)
            return true;

        if (required == null || required.isEmpty() || tool == null)
            return false;

        for(Tool req : required)
            if(req.getType() == ToolType.ANY || req.getType() == tool.getType() && tool.getHardness() >= req.getHardness())
                return true;

        return false;
    }

    public static int getValue(ItemStack item, Material block){
        int value = 0;

        if(item == null)
            return value;

        Tool tool = tools.get(item.getType());
        value += tool.getHardness() + tool.getPower();

        for(Enchantment enchantment : item.getEnchantments().keySet())
            value += (enchantment.equals(Enchantment.SILK_TOUCH) && requiresSilkTouch.contains(block)) ? 250 : getEnchantValues.getOrDefault(enchantment, 0) * item.getEnchantmentLevel(enchantment);

        return value;
    }

    public static double calculateDurabilityRemaining(short maxDurability, short currentDurability) {
        if (maxDurability <= 0)
            throw new IllegalArgumentException("Maximum durability must be greater than zero.");

        if (currentDurability > maxDurability)
            throw new IllegalArgumentException("Current durability cannot exceed maximum durability.");

        return 100.0 * currentDurability / maxDurability;
    }

    public static boolean isTool(Material type){
        return tools.get(type) != null;
    }

    private static void fillWithTag(Tag<Material> tag, Tool tool, Tag<Material> excludeTag, String exclude){
        Set<Material> excludeTags = excludeTag != null ? excludeTag.getValues() : null;
        for(Material mat : tag.getValues())
            if(!((excludeTags != null && excludeTags.contains(mat)) || (exclude != null && mat.toString().contains(exclude))))
                getRequiredTool.put(mat, tool);
    }

    public static ItemStack markItem(ItemStack i, NamespacedKey key, int value){
        if(i == null)
            return null;

        ItemStack item = i.clone();
        ItemMeta meta = item.getItemMeta();

        if(meta == null)
            return null;

        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
        return item;
    }

    public static Integer getMark(ItemStack item, NamespacedKey key){
        if(item == null)
            return null;

        ItemMeta meta = item.getItemMeta();

        if(meta == null)
            return null;

        return meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    public static Material getNecessary(Material mat){
        return crops.get(mat);
    }

}
