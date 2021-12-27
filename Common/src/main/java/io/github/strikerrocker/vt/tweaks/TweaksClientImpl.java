package io.github.strikerrocker.vt.tweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TweaksClientImpl {

    /**
     * Shows the name of the mob in tooltip
     */
    public static void addSilkSpawnerTooltip(ItemStack stack, List<Component> list) {
        if (stack.hasTag()) {
            CompoundTag spawnerDataNBT = stack.getOrCreateTag().getCompound(TweaksImpl.SPAWNER_TAG);
            if (!spawnerDataNBT.isEmpty()) {
                DummySpawnerLogic.DUMMY_SPAWNER_LOGIC.load(Minecraft.getInstance().level, BlockPos.ZERO, spawnerDataNBT);
                Entity ent = DummySpawnerLogic.DUMMY_SPAWNER_LOGIC.getOrCreateDisplayEntity(Minecraft.getInstance().level);
                if (ent != null)
                    list.add(ent.getDisplayName());
            }
        }
    }
}
