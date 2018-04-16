package io.github.strikerrocker.vt.handlers;

import io.github.strikerrocker.vt.vtModInfo;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfigEntries.NumberSliderEntry;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

import static io.github.strikerrocker.vt.vt.logInfo;

/**
 * The config handler for VanillaTweaks
 */
@Mod.EventBusSubscriber(modid = vtModInfo.MOD_ID)
public class VTConfigHandler
{
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
    public static boolean spongeDryInNether;
    public static boolean hoeAsSickle;
    public static boolean realisticRelationship;
    public static boolean nameTag;
    //Recipes
    public static boolean useBetterStoneToolRecipes;
    public static boolean useBetterStairsRecipes;
    public static boolean useBetterChestRecipe;
    public static boolean leather;
    //Enchantments
    public static boolean vigor;
    public static boolean nimble;
    public static boolean hops;
    public static boolean veteran;
    public static boolean siphon;
    public static boolean homing;
    public static boolean blazing;
    //Vanillatweaks
    public static float binocularZoomAmount;
    public static boolean storageBlocks;
    public static boolean craftingPad;
    public static boolean pedestal;
    public static boolean slimeChunkFinder;
    //Miscellaneous
    public static boolean editSigns;
    public static boolean clearSigns;
    public static boolean stairSit;
    public static boolean silenceWither;
    public static boolean silenceDragon;
    public static boolean silenceLightning;
    public static boolean noMoreLavaPocketGen;
    public static boolean endFrameBroken;
    public static boolean armourStandSwapping;
    public static boolean autoSeedPlanting;
    //Miscellaneous: Requires Restart
    public static boolean commandBlockInRedstoneTab;
    public static boolean renameButtons;
    public static double stackSize;
    public static boolean barks;

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
     * Syncs the config file if it changes
     *
     * @param event The OnConfigChangedEvent
     */
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(vtModInfo.MOD_ID))
            VTConfigHandler.syncConfig();
    }

    /**
     * Syncs the config file
     */
    private static void syncConfig() {
        logInfo("Syncing config file");
        String DropsCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Drops";
        creeperDropTntChance = get(DropsCategory, "Chance of creepers dropping TNT", 1D, "The chance of creepers dropping TNT, out of 10.", true).setMinValue(0).setMaxValue(10).getDouble() / 10;
        batLeatherDropChance = get(DropsCategory, "Chance of bats dropping leather", 10D, "The chance of bats dropping leather, out of 10.", true).setMinValue(0).setMaxValue(10).getDouble() / 10;
        creeperBurnInDaylight = get(DropsCategory, "Creepers burn in daylight", true, "Do creepers burn in daylight?");
        babyZombieBurnInDaylight = get(DropsCategory, "Baby zombies burn in daylight", true, "Do baby zombies burn in daylight?");
        mobSpawnerSilkTouchDrop = get(DropsCategory, "Mob spawner silk touch drop", true, "Do mob spawners drop themselves when harvested with silk touch?");
        spongeDryInNether = get(DropsCategory, "Sponges dry in nether", true, "Sponges dry in nether");
        hoeAsSickle = get(DropsCategory, "Hoe acts as a sickle", true, "Does Hoe acts as a sickle");
        realisticRelationship = get(DropsCategory, "realistic predator/prey relationships", true, "realistic predator/prey relationships");
        nameTag = get(DropsCategory, "nametag drop", true, "to enable nametg drop or not");
        config.setCategoryComment(DropsCategory, "Modify mob drops");


        String recipesCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Recipes";
        useBetterStoneToolRecipes = get(recipesCategory, "Stone tools crafted from stone", true, "Are stone tools crafted out of stone?");
        useBetterStairsRecipes = get(recipesCategory, "Better stairs recipe enabled", true, "Is the better stairs recipe enabled?");
        useBetterChestRecipe = get(recipesCategory, "Better Chest recipe enabled", true, "Is the better chest recipe enabled");
        leather = get(recipesCategory, "rotten fresh to leather", true, "whether to enable rotten flesh to leather recipe");
        config.setCategoryComment(recipesCategory, "Toggle VanillaTweaks's recipe enhancements");
        config.setCategoryRequiresMcRestart(recipesCategory, true);

        String enchantmentsCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Enchantments";
        vigor = get(enchantmentsCategory, "Enables Vigor Enchantment", true, "Enables Vigor Enchantment");
        nimble = get(enchantmentsCategory, "Enables Nimble Enchantment", true, "Enables Nimble Enchantment");
        hops = get(enchantmentsCategory, "Enables Hops Enchantment", true, "Enables Hops Enchantment");
        veteran = get(enchantmentsCategory, "Enables Veteran Enchantment", true, "Enables Veteran Enchantment");
        siphon = get(enchantmentsCategory, "Enables siphon Enchantment", true, "Enables siphon Enchantment");
        homing = get(enchantmentsCategory, "Enables Homing Enchantment", true, "Enables Homing Enchantment");
        blazing = get(enchantmentsCategory, "Enables Blazing Enchantment", true, "Enables Blazing Enchantment");
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
        noMoreLavaPocketGen = get(miscCategory, "No More Lava Pockets", true, "Makes the Nether Less Dangerous by preventing lava pockets to spawn.");
        editSigns = get(miscCategory, "Sign Editing", true, "Right-click to edit signs");
        clearSigns = get(miscCategory, "Clearing Signs", true, "Shift+right-click to clear signs");
        stairSit = get(miscCategory, "Sit on Stair & slabs", true, "Allows the player to sit on slabs & stairs");
        silenceDragon = get(miscCategory, "Silence the Brodcast of ender dragon sound", true, "Silence the Brodcast of ender dragon sound");
        silenceWither = get(miscCategory, "Silence the Brodcast of wither sound", true, "Silence the Brodcast of wither sound");
        silenceLightning = get(miscCategory, "Silence the sound of lightning", true, "Silence the sound of lightning");
        armourStandSwapping = get(miscCategory, "Swap armour in armour stand", true, "Shift click armour stand swap armor");
        autoSeedPlanting = get(miscCategory, "Enable automatic seed planting", true, "Do dropped seeds plant themselves?");
        config.setCategoryComment(miscCategory, "Miscellaneous settings");

        String miscRequiresRestartCategory = miscCategory + Configuration.CATEGORY_SPLITTER + "Requires Restart";
        renameButtons = get(miscRequiresRestartCategory, "Rename buttons", true, "Do buttons get renamed based on their material?");
        commandBlockInRedstoneTab = get(miscRequiresRestartCategory, "Commadn Block in redstone tab", true, "Command Block can be found in redstone creative tab");
        endFrameBroken = get(miscRequiresRestartCategory, "Breakable End Portal Frame", false, "Makes the End Portal Frame to be broken");
        barks = get(miscRequiresRestartCategory, "1.13 barks", true, "Enables 1.13 bark blocks");
        stackSize = get(miscRequiresRestartCategory, "changes stack sizes for various item's", 4D, "changes stack sizes for various item's", true).setMinValue(3).setMaxValue(16).getDouble();
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
}