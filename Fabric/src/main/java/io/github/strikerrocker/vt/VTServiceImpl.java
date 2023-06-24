package io.github.strikerrocker.vt;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

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
    public boolean place(ItemEntity itemEntity, Level level) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            FakePlayer fakePlayer = FakePlayer.get(serverLevel);
            Vec3 entityVec = new Vec3(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
            BlockHitResult rayTraceResult = itemEntity.level.clip(
                    new ClipContext(entityVec.add(0, 2, 0), entityVec.add(0, -1, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, itemEntity));
            InteractionResult result = itemEntity.getItem().getItem().useOn(new UseOnContext(fakePlayer, InteractionHand.MAIN_HAND, rayTraceResult));
            return result == InteractionResult.SUCCESS || result == InteractionResult.CONSUME;
        }
        return false;
    }

    @Override
    public int getSelfPlantingInterval() {
        return VanillaTweaksFabric.config.world.selfPlantingInterval;
    }
}