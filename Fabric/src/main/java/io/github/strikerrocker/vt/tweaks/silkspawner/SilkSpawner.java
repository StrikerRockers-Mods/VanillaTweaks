package io.github.strikerrocker.vt.tweaks.silkspawner;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.BlockBreakCallback;
import io.github.strikerrocker.vt.events.BlockPlaceCallback;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public class SilkSpawner extends Feature {
    protected static final String SPAWNER_TAG = "SilkSpawnerData";

    @Override
    public void initialize() {
        // Handles spawner block entity placement
        BlockPlaceCallback.EVENT.register((world, pos, blockState, entity, stack) -> {
            if (entity instanceof Player playerEntity && VanillaTweaksFabric.config.tweaks.enableSilkSpawner &&
                    !stack.isEmpty() && stack.getItem() == Items.SPAWNER) {
                CompoundTag spawnerDataNBT = stack.getOrCreateTag().getCompound(SPAWNER_TAG);
                if (!spawnerDataNBT.isEmpty()) {
                    BlockEntity tile = world.getBlockEntity(pos);
                    if (tile instanceof SpawnerBlockEntity spawner) {
                        spawner.getSpawner().load(world, pos, spawnerDataNBT);
                    }
                }
            }
        });
        //Handles spawner breaking logic
        BlockBreakCallback.EVENT.register((world, pos, blockState, playerEntity) -> {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            int lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, playerEntity.getMainHandItem());
            if (blockState.getBlock() instanceof SpawnerBlock && !world.isClientSide() && blockEntity instanceof SpawnerBlockEntity && VanillaTweaksFabric.config.tweaks.enableSilkSpawner && lvl >= 1) {
                ItemStack drop = new ItemStack(Blocks.SPAWNER);
                CompoundTag spawnerData = ((SpawnerBlockEntity) blockEntity).getSpawner().save(new CompoundTag());
                CompoundTag stackTag = new CompoundTag();
                spawnerData.remove("Delay");
                stackTag.put(SPAWNER_TAG, spawnerData);
                drop.setTag(stackTag);

                Block.popResource(playerEntity.getCommandSenderWorld(), pos, drop);
            }
        });
    }
}