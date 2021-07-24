package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TNTIgnition extends Feature {
    private ForgeConfigSpec.BooleanValue tntIgnition;

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        BlockState blockState = event.getPlacedBlock();
        LevelAccessor world = event.getWorld();
        BlockPos pos = event.getPos();
        if (!world.isClientSide() && blockState.getBlock() instanceof TntBlock && tntIgnition.get()) {
            for (Direction f : Direction.values())
                if (world.getBlockState(pos.relative(f, 1)).getBlock() instanceof MagmaBlock || world.getBlockState(pos.relative(f, 1)).getMaterial() == Material.LAVA) {
                    TntBlock blockTNT = (TntBlock) blockState.getBlock();
                    TntBlock.explode(event.getEntity().getCommandSenderWorld(), pos);
                    world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
                }
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        tntIgnition = builder
                .translation("config.vanillatweaks:tntIgnition")
                .comment("Want TNT to ignite when next to lava or magma block?")
                .define("tntIgnition", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}
