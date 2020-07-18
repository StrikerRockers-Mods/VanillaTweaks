package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Sickle extends Feature {
    private ForgeConfigSpec.BooleanValue hoeActsAsSickle;

    private static boolean canHarvest(BlockState state) {
        Block block = state.getBlock();
        return (block instanceof BushBlock && !(block instanceof LilyPadBlock)) || block instanceof SugarCaneBlock;
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
        PlayerEntity player = event.getPlayer();
        World world = player.getEntityWorld();
        ItemStack stack = player.getHeldItemMainhand();
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
                    BlockPos pos = event.getPos().add(i, 0, k);
                    BlockState state = world.getBlockState(pos);
                    if (canHarvest(state)) {
                        Block block = state.getBlock();
                        if (block.canHarvestBlock(state, world, pos, player))
                            block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack);
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        world.playSound(player, player.func_233580_cy_(), state.getSoundType(world, pos, player).getBreakSound(), SoundCategory.BLOCKS, 1f, 1f);
                    }
                }
            }
            stack.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(playerEntity.getActiveHand()));
        }
    }
}
