package io.github.strikerrocker.vt.tweaks.silkspawner;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SilkSpawner extends Feature {
    private static final String SPAWNER_TAG = "SilkSpawnerData";
    private ForgeConfigSpec.BooleanValue enableSilkSpawner;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableSilkSpawner = builder
                .translation("config.vanillatweaks:enableSilkSpawner")
                .comment("Want the ability to move spawners with silk touch?")
                .define("enableSilkSpawner", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
            ItemStack stack = playerEntity.getHeldItem(playerEntity.getActiveHand());
            if (enableSilkSpawner.get() && stack.getItem() == Item.getItemFromBlock(Blocks.SPAWNER) && stack.hasTag()) {
                CompoundNBT stackTag = stack.getTag();
                assert stackTag != null;
                CompoundNBT spawnerDataNBT = stackTag.getCompound(SPAWNER_TAG);
                if (!spawnerDataNBT.isEmpty()) {
                    TileEntity tile = event.getWorld().getTileEntity(event.getPos());
                    if (tile instanceof MobSpawnerTileEntity) {
                        ((MobSpawnerTileEntity) tile).getSpawnerBaseLogic().read(spawnerDataNBT);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBreak(BlockEvent.BreakEvent event) {
        IWorld world = event.getWorld();
        TileEntity tile = world.getTileEntity(event.getPos());
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, event.getPlayer().getHeldItemMainhand());
        if (event.getState().getBlock() instanceof SpawnerBlock && !world.isRemote() && tile instanceof MobSpawnerTileEntity && enableSilkSpawner.get() && lvl >= 1) {
            event.setExpToDrop(0);
            ItemStack drop = new ItemStack(Blocks.SPAWNER);
            CompoundNBT spawnerData = ((MobSpawnerTileEntity) tile).getSpawnerBaseLogic().write(new CompoundNBT());
            CompoundNBT stackTag = new CompoundNBT();
            spawnerData.remove("Delay");
            stackTag.put(SPAWNER_TAG, spawnerData);
            drop.setTag(stackTag);

            Block.spawnAsEntity(tile.getWorld(), event.getPos(), drop);
            //Does this cause problems w/ block protection?
            world.destroyBlock(event.getPos(), false);
            //world.removeTileEntity(event.getPos());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onToolTipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.hasTag()) {
            CompoundNBT stackTag = stack.getTag();
            assert stackTag != null;
            CompoundNBT spawnerDataNBT = stackTag.getCompound(SPAWNER_TAG);
            if (!spawnerDataNBT.isEmpty()) {
                DummySpawnerLogic.DUMMY_SPAWNER_LOGIC.read(spawnerDataNBT);
                Entity ent = DummySpawnerLogic.DUMMY_SPAWNER_LOGIC.getCachedEntity();
                event.getToolTip().add(ent.getDisplayName());
            }
        }
    }

}
