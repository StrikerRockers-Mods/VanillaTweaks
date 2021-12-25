package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TweaksImpl {

    /**
     * Swaps the items in given slot of Player and ArmorStand
     */
    public static void swapSlot(Player player, ArmorStand armorStand, EquipmentSlot slot) {
        ItemStack playerItem = player.getItemBySlot(slot);
        ItemStack armorStandItem = armorStand.getItemBySlot(slot);
        player.setItemSlot(slot, armorStandItem);
        armorStand.setItemSlot(slot, playerItem);
    }

    /**
     * Returns whether the given BlockState is considered for sickle feature
     */
    public static boolean canHarvest(BlockState state) {
        Block block = state.getBlock();
        return (block instanceof BushBlock && !(block instanceof WaterlilyBlock)) || block instanceof SugarCaneBlock;
    }

    /**
     * Get the range for sickle harvesting based on type of hoe
     */
    public static int getRange(Item item) {
        if (item == Items.DIAMOND_HOE)
            return 2;
        else if (item == Items.NETHERITE_HOE)
            return 3;
        else return 1;
    }

    public static void addBeeHiveTooltip(ItemStack stack, List<Component> tooltips) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag beTag = tag.getCompound("BlockEntityTag");
        ListTag bees = beTag.getList("Bees", 10);
        CompoundTag blockStateTag = tag.getCompound("BlockStateTag");
        String honeyLvl = blockStateTag.getString("honey_level");
        tooltips.add(new TranslatableComponent("vanillatweaks.bees").append(new TextComponent(String.format("%d", bees.size()))));
        tooltips.add(new TranslatableComponent("vanillatweaks.honey.lvl").append(new TextComponent(String.format("%s", honeyLvl))));
    }

    public static void triggerSickle(Player player, ItemStack stack, Level world, BlockPos blockPos, BlockState originalState, boolean config) {
        VanillaTweaks.LOGGER.info(stack);
        if (!stack.isEmpty() && stack.getItem() instanceof HoeItem && TweaksImpl.canHarvest(originalState) &&
                config) {
            int range = TweaksImpl.getRange(stack.getItem());
            for (int i = -range; i < range + 1; i++) {
                for (int k = -range; k < range + 1; k++) {
                    if (i == 0 && k == 0)
                        continue;
                    BlockPos pos = blockPos.offset(i, 0, k);
                    BlockState state = world.getBlockState(pos);
                    if (TweaksImpl.canHarvest(state)) {
                        Block block = state.getBlock();
                        if (player.hasCorrectToolForDrops(state))
                            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), stack);
                        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        world.playSound(player, player.blockPosition(), block.getSoundType(state).getBreakSound(), SoundSource.BLOCKS, 1f, 1f);
                    }
                }
            }
            stack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
        }
    }

    public static void triggerShearNametag(Player player, ItemStack stack, Entity target, Level world, boolean config) {
        if (config && !stack.isEmpty() && stack.getItem() instanceof ShearsItem &&
                target instanceof LivingEntity && target.hasCustomName() && !world.isClientSide) {
            target.playSound(SoundEvents.SHEEP_SHEAR, 1, 1);
            ItemStack nameTag = new ItemStack(Items.NAME_TAG).setHoverName(target.getCustomName());
            nameTag.getOrCreateTag().putInt("RepairCost", 0);
            target.spawnAtLocation(nameTag);
            target.setCustomName(null);
            stack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
        }
    }
}
