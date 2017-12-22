package com.strikerrocker.vt.handlers;

import com.google.common.collect.Maps;
import com.strikerrocker.vt.enchantments.VTEnchantments;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfigEntries.NumberSliderEntry;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;
import java.util.Map;

import static com.strikerrocker.vt.vt.logInfo;

/**
 * The config handler for VanillaTweaks
 */
public class VTConfigHandler {
    /**
     * The actual configuration containing the configuration file
     */
    public static Configuration config;
    //Drops
    public static double creeperDropTntChance;
    public static double batLeatherDropChance;
    public static boolean creeperBurnInDaylight;
    public static boolean babyZombieBurnInDaylight;
    public static boolean mobSpawnerSilkTouchDrop;
    //Recipes
    public static boolean useBetterStoneToolRecipes;
    public static boolean useBetterStairsRecipes;
    public static boolean useBetterChestRecipe;
    public static boolean useBetterMinecartRecipies;
    //Enchantments
    public static Map<String, Boolean> enchantmentNameToEnable = Maps.newHashMap();
    //Vanillatweaks
    public static float binocularZoomAmount;
    public static boolean storageBlocks;
    public static boolean craftingPad;
    public static boolean pedestal;
    public static boolean NoMoreLavaPocketGen;
    public static boolean endFrameBroken;
    public static boolean slimeChunkFinder;
    //Miscellaneous
    public static boolean editSigns;
    public static boolean clearSigns;
    public static boolean stairSit;
    public static boolean silenceWither;
    public static boolean silenceDragon;
    public static boolean silenceLightning;
    //Miscellaneous: Requires Restart
    public static boolean commandBlockInRedstoneTab;
    public static boolean renameButtons;
    public static double stackSize;

    /**
     * Initializes the config handler for VanillaTweaks
     *
     * @param configFile The configuration file (fetched from the FMLPreInitializationEvent)
     */
    public static void init(File configFile) {
        config = new Configuration(configFile);
        config.load();
        syncConfig();
    }

    /**
     * Syncs the config file
     */
    public static void syncConfig() {
        logInfo("Syncing config file");
        String DropsCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Drops";
        creeperDropTntChance = get(DropsCategory, "Chance of creepers dropping TNT", 1D, "The chance of creepers dropping TNT, out of 10.", true).setMinValue(0).setMaxValue(10).getDouble() / 10;
        batLeatherDropChance = get(DropsCategory, "Chance of bats dropping leather", 10D, "The chance of bats dropping leather, out of 10.", true).setMinValue(0).setMaxValue(10).getDouble() / 10;
        creeperBurnInDaylight = get(DropsCategory, "Creepers burn in daylight", true, "Do creepers burn in daylight?");
        babyZombieBurnInDaylight = get(DropsCategory, "Baby zombies burn in daylight", true, "Do baby zombies burn in daylight?");
        mobSpawnerSilkTouchDrop = get(DropsCategory, "Mob spawner silk touch drop", true, "Do mob spawners drop themselves when harvested with silk touch?");
        config.setCategoryComment(DropsCategory, "Modify mob drops");


        String recipesCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Recipes";
        useBetterStoneToolRecipes = get(recipesCategory, "Stone tools crafted from stone", true, "Are stone tools crafted out of stone?");
        useBetterStairsRecipes = get(recipesCategory, "Better stairs recipe enabled", true, "Is the better stairs recipe enabled?");
        useBetterChestRecipe = get(recipesCategory, "Better Chest recipe enabled", true, "Is the better chest recipe enabled");
        config.setCategoryComment(recipesCategory, "Toggle VanillaTweaks's recipe enhancements");
        config.setCategoryRequiresMcRestart(recipesCategory, true);

        String enchantmentsCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Enchantments";
        for (String enchantmentName : VTEnchantments.enchantmentNames)
            enchantmentNameToEnable.put(enchantmentName, get(enchantmentsCategory, "Enable " + enchantmentName, true, "Is the " + enchantmentName + " enchantment enabled?"));
        config.setCategoryComment(enchantmentsCategory, "Toggles VT Enchantments");
        config.setCategoryRequiresMcRestart(enchantmentsCategory, true);

        String vanillaTweaks = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Blocks and Items from VT";
        binocularZoomAmount = (float) get(vanillaTweaks, "Binocular Zoom Amount", 4, "By how much do binoculars divide your FOV?", false).setMinValue(1D).getDouble();
        craftingPad = get(vanillaTweaks, "Crafting table changes", true, "Is there an way to create a portable crafting device?");
        pedestal = get(vanillaTweaks, "Enable the pedestal", true, "Enabling the pedestal");
        storageBlocks = get(vanillaTweaks, "Enables storage blocks", true, "Enables the Storage Blocks");
        slimeChunkFinder = get(vanillaTweaks, "Enables slime chunk finder", true, "Enables the Slime Chunk Finder");
        config.setCategoryComment(vanillaTweaks, "Config for blocks and items from VT");
        config.setCategoryRequiresMcRestart(vanillaTweaks, true);

        String miscCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Miscellaneous";
        NoMoreLavaPocketGen = get(miscCategory, "No More Lava Pockets", true, "Makes the Nether Less Dangerous by preventing lava pockets to spawn.");
        editSigns = get(miscCategory, "Sign Editing", true, "Right-click to edit signs");
        clearSigns = get(miscCategory, "Clearing Signs", true, "Shift+right-click to clear signs");
        stairSit = get(miscCategory, "Sit on Stair & slabs", true, "Allows the player to sit on slabs & stairs");
        silenceDragon = get(miscCategory, "Silence the Brodcast of ender dragon sound", true, "Silence the Brodcast of ender dragon sound");
        silenceWither = get(miscCategory, "Silence the Brodcast of wither sound", true, "Silence the Brodcast of wither sound");
        silenceLightning = get(miscCategory, "Silence the sound of lightning", true, "Silence the sound of lightning");
        config.setCategoryComment(miscCategory, "Miscellaneous settings");

        String miscRequiresRestartCategory = miscCategory + Configuration.CATEGORY_SPLITTER + "Requires Restart";
        renameButtons = get(miscRequiresRestartCategory, "Rename buttons", true, "Do buttons get renamed based on their material?");
        endFrameBroken = get(miscRequiresRestartCategory, "Breakable End Portal Frame", false, "Makes the End Portal Frame to be broken");
        config.setCategoryComment(miscRequiresRestartCategory, "Settings that require a Minecraft restart");
        config.setCategoryRequiresMcRestart(miscRequiresRestartCategory, true);


        if (config.hasChanged())
            config.save();

    }

    private static Property get(String category, String key, double defaultValue, String comment, boolean slider) {
        Property property = config.get(category, key, defaultValue, comment);
        if (slider && FMLCommonHandler.instance().getEffectiveSide().isClient())
            return property.setConfigEntryClass(NumberSliderEntry.class);
        return property;
    }

    private static boolean get(String category, String key, boolean defaultValue, String comment) {
        return config.get(category, key, defaultValue, comment).getBoolean();
    }

    public Configuration getConfiguration() {
        return config;
    }
}