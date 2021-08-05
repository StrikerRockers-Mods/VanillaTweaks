package io.github.strikerrocker.vt.tweaks.silkspawner;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

    /**
     * Handles spawner block entity placement
     */
    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = player.getUseItem();
            if (enableSilkSpawner.get() && !stack.isEmpty() && stack.hasTag()) {
                CompoundTag stackTag = stack.getTag();
                if (stackTag != null) {
                    CompoundTag spawnerDataNBT = stackTag.getCompound(SPAWNER_TAG);
                    if (!spawnerDataNBT.isEmpty()) {
                        BlockEntity tile = event.getWorld().getBlockEntity(event.getPos());
                        if (tile instanceof SpawnerBlockEntity spawnerBlockEntity) {
                            spawnerBlockEntity.getSpawner().load(player.level, event.getPos(), spawnerDataNBT);
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles Spawner break logic
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBreak(BlockEvent.BreakEvent event) {
        LevelAccessor world = event.getWorld();
        BlockEntity blockEntity = world.getBlockEntity(event.getPos());
        int lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, event.getPlayer().getMainHandItem());
        if (event.getState().getBlock() instanceof SpawnerBlock && !world.isClientSide() && blockEntity instanceof SpawnerBlockEntity && enableSilkSpawner.get() && lvl >= 1) {
            event.setExpToDrop(0);
            ItemStack drop = new ItemStack(Blocks.SPAWNER);
            CompoundTag spawnerData = ((SpawnerBlockEntity) blockEntity).getSpawner().save(blockEntity.getLevel(), event.getPos(), new CompoundTag());
            CompoundTag stackTag = new CompoundTag();
            spawnerData.remove("Delay");
            stackTag.put(SPAWNER_TAG, spawnerData);
            drop.setTag(stackTag);

            Block.popResource(event.getPlayer().level, event.getPos(), drop);
            //Does this cause problems w/ block protection?
            event.getPlayer().level.removeBlockEntity(blockEntity.getBlockPos());
            world.destroyBlock(event.getPos(), false);
            event.setCanceled(true);
        }
    }

    @Mod.EventBusSubscriber(modid = VanillaTweaks.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        /**
         * Shows the name of the mob in tooltip
         */
        @SubscribeEvent
        public static void onToolTipEvent(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            if (stack.hasTag()) {
                CompoundTag stackTag = stack.getTag();
                if (stackTag != null) {
                    CompoundTag spawnerDataNBT = stackTag.getCompound(SPAWNER_TAG);
                    if (!spawnerDataNBT.isEmpty()) {
                        DummySpawnerLogic.DUMMY_SPAWNER_LOGIC.load(event.getEntity().level, new BlockPos(0, 0, 0), spawnerDataNBT);
                        Entity ent = DummySpawnerLogic.DUMMY_SPAWNER_LOGIC.getOrCreateDisplayEntity(event.getEntity().level);
                        if (ent != null) {
                            event.getToolTip().add(ent.getDisplayName());
                        }
                    }
                }
            }
        }
    }
}