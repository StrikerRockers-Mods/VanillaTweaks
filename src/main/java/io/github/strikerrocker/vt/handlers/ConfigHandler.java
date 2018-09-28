package io.github.strikerrocker.vt.handlers;

import io.github.strikerrocker.vt.VTModInfo;
import net.minecraftforge.common.config.Config;

/**
 * Created by StrikerRocker on 30/4/18.
 */
@Config(modid = VTModInfo.MOD_ID, name = VTModInfo.NAME, category = "")
public class ConfigHandler {

    public static Drops drops = new Drops();
    @Config.RequiresMcRestart
    public static Recipes recipes = new Recipes();
    @Config.RequiresMcRestart
    public static Enchantments enchantments = new Enchantments();
    @Config.RequiresMcRestart
    public static VanillaTweaks vanilla_tweaks = new VanillaTweaks();
    public static Miscellanious miscellanious = new Miscellanious();
    public static Miscellanious_Restart miscellanious_restart = new Miscellanious_Restart();
    public static StackSize stackSize = new StackSize();

    public static class Drops {
        @Config.Comment({"The chance of creepers dropping TNT, out of 10."})
        @Config.RangeDouble(min = 0, max = 10)
        public double creeperDropTntChance = 1D;
        @Config.Comment({"The chance of bats dropping leather, out of 10."})
        @Config.RangeDouble(max = 10, min = 0)
        public double batLeatherDropChance = 10D;
        @Config.Comment({"Do creepers burn in daylight?"})
        public boolean creeperBurnInDaylight = true;
        @Config.Comment({"Do baby zombie burn in daylight?"})
        public boolean babyZombieBurnInDaylight = true;
        @Config.Comment({"Do mob spawners drop themselves when harvested with silk touch?"})
        public boolean mobSpawnerSilkTouchDrop = true;
        @Config.Comment({"So sponges dry in nether ?"})
        public boolean spongeDryInNether = true;
        @Config.Comment({"Does Hoe acts as a sickle?"})
        public boolean hoeAsSickle = true;
        @Config.Comment({"Is realistic predator/prey relationships activated?"})
        public boolean realisticRelationship = true;
        @Config.Comment({"Does nametag drop when named mob is killed?"})
        public boolean nameTag = true;
    }

    public static class Recipes {
        @Config.Comment({"Are stone tools crafted out of stone?"})
        public boolean useBetterStoneToolRecipes = true;
        @Config.Comment({"Is the better stairs recipe enabled?"})
        public boolean useBetterStairsRecipes = true;
        @Config.Comment({"Is the better chest recipe enabled?"})
        public boolean useBetterChestRecipe = true;
        @Config.Comment({"Is rotten flesh to leather recipe enabled?"})
        public boolean leather = true;
    }

    public static class Enchantments {
        @Config.Comment({"Enables Vigor Enchantment"})
        public boolean vigor = true;
        @Config.Comment({"Enables Nimble Enchantment"})
        public boolean nimble = true;
        @Config.Comment({"Enables Hops Enchantment"})
        public boolean hops = true;
        @Config.Comment({"Enables Veteran Enchantment"})
        public boolean veteran = true;
        @Config.Comment({"Enables Siphon Enchantment"})
        public boolean siphon = true;
        @Config.Comment({"Enables Homing Enchantment"})
        public boolean homing = true;
        @Config.Comment({"Enables Blazing Enchantment"})
        public boolean blazing = true;
    }

    public static class VanillaTweaks {
        @Config.Comment({"Enables the Slime Chunk Finder"})
        public static boolean slimeChunkFinder = true;
        @Config.RangeDouble(min = 1)
        @Config.Comment({"By how much do binoculars divide your FOV?"})
        public float binocularZoomAmount = 4;
        @Config.Comment({"Enables the Storage Blocks"})
        public boolean storageBlocks = true;
        @Config.Comment({"Is there an way to create a portable crafting device?"})
        public boolean craftingPad = true;
        @Config.Comment({"Enabling the pedestal"})
        public boolean pedestal = true;
    }

    public static class Miscellanious {
        @Config.Comment({"Right-click to edit signs"})
        public boolean editSigns = true;
        @Config.Comment({"Shift+right-click to clear signs"})
        public boolean clearSigns = true;
        @Config.Comment({"Allows the player to sit on slabs & stairs"})
        public boolean stairSit = true;
        @Config.Comment({"Silence the Brodcast of wither sound"})
        public boolean silenceWither = true;
        @Config.Comment({"Silence the Brodcast of ender dragon sound"})
        public boolean silenceDragon = true;
        @Config.Comment({"Silence the sound of lightning"})
        public boolean silenceLightning = true;
        @Config.Comment({"Makes the Nether Less Dangerous by preventing lava pockets to spawn."})
        public boolean noMoreLavaPocketGen = true;
        @Config.Comment({"Makes the End Portal Frame to be broken"})
        public boolean endFrameBroken = false;
        @Config.Comment({"Shift click armour stand swap armor with your own"})
        public boolean armourStandSwapping = true;
        @Config.Comment({"Do dropped seeds plant themselves?"})
        public boolean autoSeedPlanting = true;
    }

    public static class Miscellanious_Restart {
        @Config.Comment({"Command Block can be found in redstone creative tab"})
        public boolean commandBlockInRedstoneTab = true;
        @Config.Comment({"Do buttons get renamed based on their material?"})
        public boolean renameButtons = true;
        @Config.Comment({"Enables 1.13 bark blocks"})
        public boolean barks = true;
    }

    public static class StackSize {
        @Config.RangeInt(min = 1)
        @Config.Comment({"Stack size for beds"})
        @Config.Name("Bed stack size")
        public int bed_stackSize = 4;
        @Config.Comment({"Stack size for Totem of undying"})
        @Config.Name("Totem of undying stack size")
        @Config.RangeInt(min = 1)
        public int totem_of_undying_stackSize = 4;
        @Config.Comment({"Stack size for boat"})
        @Config.Name("boat stack size")
        @Config.RangeInt(min = 1)
        public int boat_stackSize = 4;
        @Config.RangeInt(min = 1)
        @Config.Comment({"Stack size for cake"})
        @Config.Name("cake stack size")
        public int cake_stackSize = 4;
        @Config.RangeInt(min = 1)
        @Config.Comment({"Stack size for ender pearl"})
        @Config.Name("ender pearl stack size")
        public int ender_pearl_stackSize = 64;
    }
}
