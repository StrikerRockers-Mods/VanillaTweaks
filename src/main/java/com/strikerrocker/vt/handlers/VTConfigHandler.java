package com.strikerrocker.vt.handlers;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfigEntries.NumberSliderEntry;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;

import static com.strikerrocker.vt.vt.logInfo;

/**
 * The config handler for VanillaTweaks
 */
public class VTConfigHandler {
    /**
     * The actual configuration containing the configuration file
     */
    public static Configuration config;
    //Mob Drops
    public static double creeperDropTntChance;
    public static double batLeatherDropChance;
    //Recipes
    public static boolean useBetterStoneToolRecipes;
    public static boolean useBetterStairsRecipes;
    public static boolean useBetterChestRecipe;
    //Enchantments
    public static boolean vigor;
    public static boolean Nimble;
    public static boolean Hops;
    public static boolean Veteran;
    public static boolean siphon;
    public static boolean Homing;
    //Miscellaneous
    public static boolean creeperBurnInDaylight;
    public static boolean babyZombieBurnInDaylight;
    public static float binocularZoomAmount;
    public static boolean mobSpawnerSilkTouchDrop;
    public static boolean autoClimbLadder;
    public static boolean editSigns;
    public static boolean clearSigns;
    //Miscellaneous: Requires Restart
    public static boolean commandBlockInRedstoneTab;
    public static boolean storageBlocks;
    public static boolean renameButtons;
    public static boolean craftingPad;
    public static boolean pedestal;
    public static boolean NoMoreLavaPocketGen;
    public static boolean endFrameBroken;
    public static boolean slimeChunkFinder;

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
        String mobDropsCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Mob Drops";
        creeperDropTntChance = get(mobDropsCategory, "Chance of creepers dropping TNT", 1D, "The chance of creepers dropping TNT, out of 10.", true).setMinValue(0).setMaxValue(10).getDouble() / 10;
        batLeatherDropChance = get(mobDropsCategory, "Chance of bats dropping leather", 10D, "The chance of bats dropping leather, out of 10.", true).setMinValue(0).setMaxValue(10).getDouble() / 10;
        config.setCategoryComment(mobDropsCategory, "Modify mob drops");


        String recipesCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Recipes";
        useBetterStoneToolRecipes = get(recipesCategory, "Stone tools crafted from stone", true, "Are stone tools crafted out of stone?");
        useBetterStairsRecipes = get(recipesCategory, "Better stairs recipe enabled", true, "Is the better stairs recipe enabled?");
        useBetterChestRecipe = get(recipesCategory, "Better Chest recipe enabled", true, "Is the better chest recipe enabled");
        config.setCategoryComment(recipesCategory, "Toggle VanillaTweaks's recipe enhancements");
        config.setCategoryRequiresMcRestart(recipesCategory, true);

        String enchantmentsCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Enchantments";
        vigor = get(enchantmentsCategory, "Enables Vigor Enchantment", true, "Enables Vigor Enchantment");
        Nimble = get(enchantmentsCategory, "Enables Nimble Enchantment", true, "Enables Nimble Enchantment");
        Hops = get(enchantmentsCategory, "Enables Hops Enchantment", true, "Enables Hops Enchantment");
        Veteran = get(enchantmentsCategory, "Enables Veteran Enchantment", true, "Enables Veteran Enchantment");
        siphon = get(enchantmentsCategory, "Enables siphon Enchantment", true, "Enables siphon Enchantment");
        Homing = get(enchantmentsCategory, "Enables Homing Enchantment", true, "Enables Homing Enchantment");
        config.setCategoryRequiresMcRestart(enchantmentsCategory, true);

        String miscCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Miscellaneous";
        creeperBurnInDaylight = get(miscCategory, "Creepers burn in daylight", true, "Do creepers burn in daylight?");
        babyZombieBurnInDaylight = get(miscCategory, "Baby zombies burn in daylight", true, "Do baby zombies burn in daylight?");
        binocularZoomAmount = (float) get(miscCategory, "Binocular Zoom Amount", 4, "By how much do binoculars divide your FOV?", false).setMinValue(1D).getDouble();
        mobSpawnerSilkTouchDrop = get(miscCategory, "Mob spawner silk touch drop", true, "Do mob spawners drop themselves when harvested with silk touch?");
        NoMoreLavaPocketGen = get(miscCategory, "No More Lava Pockets", true, "Makes the Nether Less Dangerous by preventing lava pockets to spawn.");
        autoClimbLadder = get(miscCategory, "Auto climbs Ladders", true, "Makes you climb ladders automatically by just looking upwards, rather than requiring a key to be held down.");
        editSigns = get(miscCategory, "Sign Editing", true, "Right-click to edit signs");
        clearSigns = get(miscCategory, "Clearing Signs", true, "Shift+right-click to clear signs");
        config.setCategoryComment(miscCategory, "Miscellaneous settings");

        String miscRequiresRestartCategory = miscCategory + Configuration.CATEGORY_SPLITTER + "Requires Restart";
        commandBlockInRedstoneTab = get(miscRequiresRestartCategory, "Command Blocks in creative menu", true, "Can command blocks be obtained from the redstone creative tab?");
        renameButtons = get(miscRequiresRestartCategory, "Rename buttons", true, "Do buttons get renamed based on their material?");
        craftingPad = get(miscRequiresRestartCategory, "Crafting table changes", true, "Is there an way to create a portable crafting device?");
        pedestal = get(miscRequiresRestartCategory, "Enable the pedestal", true, "Enabling the pedestal");
        endFrameBroken = get(miscRequiresRestartCategory, "Breakable End Portal Frame", false, "Makes the End Po    rtal Frame to be broken");
        storageBlocks=get(miscRequiresRestartCategory,"Enables storage blocks",true,"Enables the Storage Blocks");
        slimeChunkFinder = get(miscRequiresRestartCategory, "Enables slime chunk finder", true, "Enables the Slime Chunk Finder");
        config.setCategoryComment(miscRequiresRestartCategory, "Settings that require a Minecraft restart");
        config.setCategoryRequiresMcRestart(miscRequiresRestartCategory, true);

        if (config.hasChanged())
            config.save();
    }

    public static Property get(String category, String key, double defaultValue, String comment, boolean slider) {
        Property property = config.get(category, key, defaultValue, comment);
        if (slider && FMLCommonHandler.instance().getEffectiveSide().isClient())
            return property.setConfigEntryClass(NumberSliderEntry.class);
        return property;
    }

    public static boolean get(String category, String key, boolean defaultValue, String comment) {
        return config.get(category, key, defaultValue, comment).getBoolean();
    }

    public Configuration getConfiguration() {
        return config;
    }
}