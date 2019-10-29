package io.github.strikerrocker.vt.tweaks.sit;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class Sit extends Feature {
    private boolean enableSit;

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(EntityEntryBuilder.create().entity(EntitySit.class).id(new ResourceLocation(VTModInfo.MODID, "entity_sit"), 1).name("entity_sit").tracker(32, 1, false).build());
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        enableSit = config.get(category, "enableSit", true, "Want to be able to sit on slabs and stairs?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = world.getBlockState(pos).getBlock();
        IBlockState blockAbove = world.getBlockState(pos.up());
        if ((block instanceof BlockSlab || block instanceof BlockStairs) && !EntitySit.OCCUPIED.containsKey(pos) && event.getEntityPlayer().getHeldItemMainhand().isEmpty() && !world.isRemote && enableSit) {
            if (!blockAbove.getBlock().isAir(blockAbove, world, pos.up()))
                return;
            else if (block instanceof BlockSlab && (!state.getProperties().containsKey(BlockSlab.HALF) || state.getValue(BlockSlab.HALF) != BlockSlab.EnumBlockHalf.BOTTOM))
                return;
            else if (block instanceof BlockStairs && (!state.getProperties().containsKey(BlockStairs.HALF) || state.getValue(BlockStairs.HALF) != BlockStairs.EnumHalf.BOTTOM))
                return;
            EntitySit sit = new EntitySit(world, pos);
            world.spawnEntity(sit);
            event.getEntityPlayer().startRiding(sit);
        }
    }

    @SubscribeEvent
    public void onEntityMount(EntityMountEvent event) {
        if (event.isDismounting()) {
            Entity entity = event.getEntityBeingMounted();
            if (entity instanceof EntitySit) {
                entity.setDead();
                EntitySit.OCCUPIED.remove(entity.getPosition());
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (EntitySit.OCCUPIED.containsKey(event.getPos())) {
            EntitySit.OCCUPIED.get(event.getPos()).setDead();
            EntitySit.OCCUPIED.remove(event.getPos());
        }
    }
}