package io.github.strikerrocker.vt.tweaks.silkspawner;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.tweaks.DummySpawnerLogic;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class SilkSpawnerClient extends Feature {
    @Override
    public void initialize() {
        // Shows the name of the mob in tooltip
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, list) -> {
            if (stack.hasTag()) {
                CompoundTag spawnerDataNBT = stack.getOrCreateTag().getCompound(SilkSpawner.SPAWNER_TAG);
                if (!spawnerDataNBT.isEmpty()) {
                    DummySpawnerLogic.DUMMY_SPAWNER_LOGIC.load(Minecraft.getInstance().level, BlockPos.ZERO, spawnerDataNBT);
                    Entity ent = DummySpawnerLogic.DUMMY_SPAWNER_LOGIC.getOrCreateDisplayEntity(Minecraft.getInstance().level);
                    if (ent != null)
                        list.add(ent.getDisplayName());
                }
            }
        });
    }
}
