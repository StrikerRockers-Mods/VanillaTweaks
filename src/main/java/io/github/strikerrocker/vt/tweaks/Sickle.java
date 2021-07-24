package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Sickle extends Feature {
    private ForgeConfigSpec.BooleanValue hoeActsAsSickle;

    private static boolean canHarvest(BlockState state) {
        Block block = state.getBlock();
        return (block instanceof BushBlock && !(block instanceof WaterlilyBlock)) || block instanceof SugarCaneBlock;
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        hoeActsAsSickle = builder
                .translation("config.vanillatweaks:hoeActsAsSickle")
                .comment("Want hoe to act like a sickle?")
                .define("hoeActsAsSickle", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level world = player.getCommandSenderWorld();
        ItemStack stack = player.getMainHandItem();
        if (!stack.isEmpty() && stack.getItem() instanceof HoeItem && canHarvest(event.getState()) && hoeActsAsSickle.get()) {
            int range = 1;
            if (stack.getItem() == Items.DIAMOND_HOE)
                range++;
            if (stack.getItem() == Items.NETHERITE_HOE)
                range += 2;
            for (int i = -range; i < range + 1; i++) {
                for (int k = -range; k < range + 1; k++) {
                    if (i == 0 && k == 0)
                        continue;
                    BlockPos pos = event.getPos().offset(i, 0, k);
                    BlockState state = world.getBlockState(pos);
                    if (canHarvest(state)) {
                        Block block = state.getBlock();
                        if (block.canHarvestBlock(state, world, pos, player))
                            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), stack);
                        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        world.playSound(player, player.blockPosition(), state.getSoundType(world, pos, player).getBreakSound(), SoundSource.BLOCKS, 1f, 1f);
                    }
                }
            }
            stack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
        }
    }
}
