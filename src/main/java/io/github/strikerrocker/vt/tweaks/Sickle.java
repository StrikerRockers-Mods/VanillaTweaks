package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Sickle extends Feature {
    private boolean hoeActsAsSickle;

    private static boolean canHarvest(IBlockState state) {
        Block block = state.getBlock();
        return (block instanceof BlockBush && !(block instanceof BlockLilyPad)) || block instanceof BlockReed;
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        hoeActsAsSickle = config.get(category, "hoeActsAsSickle", true, "Want hoe to act like a sickel?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        World world = event.getWorld();
        ItemStack stack = player.getHeldItemMainhand();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemHoe && canHarvest(event.getState()) && hoeActsAsSickle) {

            int range = 1;
            if (stack.getItem() == Items.DIAMOND_HOE)
                range++;

            for (int i = -range; i < range + 1; i++)
                for (int k = -range; k < range + 1; k++) {
                    if (i == 0 && k == 0)
                        continue;

                    BlockPos pos = event.getPos().add(i, 0, k);
                    IBlockState state = world.getBlockState(pos);
                    if (canHarvest(state)) {
                        Block block = state.getBlock();
                        if (block.canHarvestBlock(world, pos, player))
                            block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack);
                        world.setBlockToAir(pos);
                        world.playEvent(2001, pos, Block.getIdFromBlock(block) + (block.getMetaFromState(state) << 12));
                    }
                }

            stack.damageItem(1, player);
        }
    }
}
