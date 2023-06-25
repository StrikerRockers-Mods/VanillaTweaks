package io.github.strikerrocker.vt;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class VTServiceImpl implements IVTService {
    @Override
    public boolean isSelfPlantingEnabled() {
        return VanillaTweaksFabric.config.world.selfPlanting;
    }

    @Override
    public List<? extends String> selfPlantingBlackList() {
        return VanillaTweaksFabric.config.world.selfPlantingBlackList;
    }

    @Override
    public int getSelfPlantingInterval() {
        return VanillaTweaksFabric.config.world.selfPlantingInterval;
    }

    @Override
    public boolean place(ItemEntity itemEntity, Level level) {
        ItemStack stack = itemEntity.getItem();
        // Checked already in the mixin
        BushBlock plantBlock = (BushBlock) ((BlockItem) stack.getItem()).getBlock();
        BlockPos pos = itemEntity.blockPosition();
        if (level.getBlockState(pos).getBlock() instanceof FarmBlock || level.getBlockState(pos).getBlock() instanceof SoulSandBlock)
            pos = pos.relative(Direction.UP);
        BlockState state = level.getBlockState(pos);
        if (plantBlock.canSurvive(plantBlock.defaultBlockState(), level, pos) && state.getBlock() instanceof AirBlock) {
            level.setBlockAndUpdate(pos, ((BlockItem) stack.getItem()).getBlock().defaultBlockState());
            return true;
        }
        return false;
    }
}
