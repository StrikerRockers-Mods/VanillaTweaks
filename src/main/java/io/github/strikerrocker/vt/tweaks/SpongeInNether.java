package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Blocks;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpongeInNether extends Feature {
    private ForgeConfigSpec.BooleanValue drySpongesInNether;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        drySpongesInNether = builder
                .translation("config.vanillatweaks:drySpongesInNether")
                .comment("Want sponge's placed in nether to dry?")
                .define("drySpongesInNether", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        IWorld world = event.getWorld();
        if (!world.isRemote() && event.getPlacedBlock() == Blocks.WET_SPONGE.getDefaultState() &&
                BiomeDictionary.getTypes(world.getBiome(event.getPos())).contains(BiomeDictionary.Type.NETHER) && drySpongesInNether.get()) {
            world.playSound(null, event.getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.4F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.9F);
            world.setBlockState(event.getPos(), Blocks.SPONGE.getDefaultState(), 1);
        }
    }
}