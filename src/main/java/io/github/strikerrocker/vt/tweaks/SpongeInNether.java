package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.BlockSponge;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpongeInNether extends Feature {
    private boolean drySpongesInNether;

    @Override
    public void syncConfig(Configuration config, String category) {
        drySpongesInNether = config.get(category, "drySpongesInNether", true, "Want sponge's placed in nether to dry?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent event) {
        World world = event.getWorld();
        if (!world.isRemote && event.getPlacedBlock() == Blocks.SPONGE.getDefaultState().withProperty(BlockSponge.WET, true) &&
                BiomeDictionary.getTypes(world.getBiome(event.getPos())).contains(BiomeDictionary.Type.NETHER) && drySpongesInNether) {
            world.playSound(null, event.getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.4F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.9F);
            world.setBlockState(event.getPos(), Blocks.SPONGE.getDefaultState().withProperty(BlockSponge.WET, false));
        }
    }
}
