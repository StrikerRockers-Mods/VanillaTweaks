package io.github.strikerrocker.vt.handlers;

import io.github.strikerrocker.vt.VTModInfo;
import net.minecraftforge.common.config.Config;

/**
 * Created by StrikerRocker on 30/4/18.
 */
@Config(modid = VTModInfo.MOD_ID, name = VTModInfo.NAME, category = "")
public class ConfigHandler {

    public static final Drops drops = new Drops();
    @Config.RequiresMcRestart
    public static final Recipes recipes = new Recipes();
    @Config.RequiresMcRestart
    public static final Enchantments enchantments = new Enchantments();
    @Config.RequiresMcRestart
    public static final VanillaTweaks vanilla_tweaks = new VanillaTweaks();
    public static final Miscellanious miscellanius = new Miscellanious();

    public static class Drops {
        @Config.Comment({"The chance of creepers dropping TNT, out of 10."})
        @Config.RangeDouble(min = 0, max = 10)
        public static double creeperDropTntChance = 1D;
        @Config.Comment({"The chance of bats dropping leather, out of 10."})
        @Config.RangeDouble(max = 10, min = 0)
        public static double batLeatherDropChance = 10D;
        @Config.Comment({"Do creepers burn in daylight?"})
        public static boolean creeperBurnInDaylight = true;
        @Config.Comment({"Do baby zombie burn in daylight?"})
        public static boolean babyZombieBurnInDaylight = true;
        @Config.Comment({"Do mob spawners drop themselves when harvested with silk touch?"})
        public static boolean mobSpawnerSilkTouchDrop = true;
        @Config.Comment({"So sponges dry in nether ?"})
        public static boolean spongeDryInNether = true;
        @Config.Comment({"Does Hoe acts as a sickle?"})
        public static boolean hoeAsSickle = true;
        @Config.Comment({"Is realistic predator/prey relationships activated?"})
        public static boolean realisticRelationship = true;
        @Config.Comment({"Does nametag drop when named mob is killed?"})
        public static boolean nameTag = true;
    }

    public static class Recipes {
        @Config.Comment({"Are stone tools crafted out of stone?"})
        public static boolean useBetterStoneToolRecipes = true;
        @Config.Comment({"Is the better stairs recipe enabled?"})
        public static boolean useBetterStairsRecipes = true;
        @Config.Comment({"Is the better chest recipe enabled?"})
        public static boolean useBetterChestRecipe = true;
        @Config.Comment({"Is rotten flesh to leathEnables the Slime Chunk Finderer recipe enabled?"})
        public static boolean leather = true;
    }

    public static final class Enchantments {
        @Config.Comment({"Enables Vigor Enchantment"})
        public static boolean vigor = true;
        @Config.Comment({"Enables Nimble Enchantment"})
        public static boolean nimble = true;
        @Config.Comment({"Enables Hops Enchantment"})
        public static boolean hops = true;
        @Config.Comment({"Enables Veteran Enchantment"})
        public static boolean veteran = true;
        @Config.Comment({"Enables Siphon Enchantment"})
        public static boolean siphon = true;
        @Config.Comment({"Enables Homing Enchantment"})
        public static boolean homing = true;
        @Config.Comment({"Enables Blazing Enchantment"})
        public static boolean blazing = true;
    }

    public static class VanillaTweaks {
        @Config.RangeDouble(min = 1)
        @Config.Comment({"By how much do binoculars divide your FOV?"})
        public static float binocularZoomAmount = 4;
        @Config.Comment({"Enables the Storage Blocks"})
        public static boolean storageBlocks = true;
        @Config.Comment({"Is there an way to create a portable crafting device?"})
        public static boolean craftingPad = true;
        @Config.Comment({"Enabling the pedestal"})
        public static boolean pedestal = true;
        @Config.Comment({"Enables the Slime Chunk Finder"})
        public static boolean slimeChunkFinder = true;
    }

    public static class Miscellanious {
        @Config.Comment({"Right-click to edit signs"})
        public static boolean editSigns = true;
        @Config.Comment({"Shift+right-click to clear signs"})
        public static boolean clearSigns = true;
        @Config.Comment({"Allows the player to sit on slabs & stairs"})
        public static boolean stairSit = true;
        @Config.Comment({"Silence the Brodcast of wither sound"})
        public static boolean silenceWither = true;
        @Config.Comment({"Silence the Brodcast of ender dragon sound"})
        public static boolean silenceDragon = true;
        @Config.Comment({"Silence the sound of lightning"})
        public static boolean silenceLightning = true;
        @Config.Comment({"Makes the Nether Less Dangerous by preventing lava pockets to spawn."})
        public static boolean noMoreLavaPocketGen = true;
        @Config.Comment({"Makes the End Portal Frame to be broken"})
        public static boolean endFrameBroken = false;
        @Config.Comment({"Shift click armour stand swap armor with your own"})
        public static boolean armourStandSwapping = true;
        @Config.Comment({"Do dropped seeds plant themselves?"})
        public static boolean autoSeedPlanting = true;
    }

    public static class Miscellanious_Restart {
        @Config.Comment({"Command Block can be found in redstone creative tab"})
        public static boolean commandBlockInRedstoneTab = true;
        @Config.Comment({"Do buttons get renamed based on their material?"})
        public static boolean renameButtons = true;
        @Config.RangeInt(min = 3, max = 16)
        @Config.Comment({"changes stack sizes for various item's"})
        public static double stackSize = 4;
        @Config.Comment({"Enables 1.13 bark blocks"})
        public static boolean barks = true;
    }
}
