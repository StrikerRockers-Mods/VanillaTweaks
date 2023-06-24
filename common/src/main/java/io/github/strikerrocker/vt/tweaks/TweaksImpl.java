package io.github.strikerrocker.vt.tweaks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.LavaFluid;

import java.util.List;

public class TweaksImpl {

    public static final String SPAWNER_TAG = "SilkSpawnerData";

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
     * Returns if entity can burn. Copied from Mob#isSunBurnTick
     */
    public static boolean canBurn(LivingEntity entity) {
        Level world = entity.level();
        if (world.isDay() && !world.isClientSide) {
            float f = entity.getLightLevelDependentMagicValue();
            boolean bl = entity.isInWaterRainOrBubble() || entity.isInPowderSnow || entity.wasInPowderSnow;
            return f > 0.5F && entity.getRandom().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !bl && world.canSeeSky(entity.blockPosition());
        }
        return false;
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

    /**
     * Shows the number of bees and honey level of bee hives
     */
    public static void addBeeHiveTooltip(ItemStack stack, List<Component> tooltips) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag beTag = tag.getCompound("BlockEntityTag");
        ListTag bees = beTag.getList("Bees", 10);
        CompoundTag blockStateTag = tag.getCompound("BlockStateTag");
        String honeyLvl = blockStateTag.getString("honey_level");
        tooltips.add(Component.translatable("vanillatweaks.bees").append(Component.literal(String.format("%d", bees.size()))));
        tooltips.add(Component.translatable("vanillatweaks.honey.lvl").append(Component.literal(String.format("%s", honeyLvl))));
    }

    /**
     * Harvests large area of crops when different hoe types are used.
     */
    public static void triggerSickle(Player player, ItemStack stack, Level world, BlockPos blockPos, BlockState originalState, boolean config) {
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

    /**
     * Make the nametag item drop when right-clicked with a shear
     */
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

    /**
     * Explode TNT when it is beside lava or magma block
     */
    public static void triggerTNTIgnition(Level level, BlockPos pos, BlockState blockState, boolean config) {
        if (!level.isClientSide() && config) {
            if (blockState.getBlock() instanceof TntBlock) {
                for (Direction f : Direction.values()) {
                    BlockState state = level.getBlockState(pos.relative(f));
                    if (state.getBlock() instanceof MagmaBlock || state.getFluidState().getType() instanceof LavaFluid) {
                        TntBlock.explode(level, pos);
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                        return;
                    }
                }
            } else if (blockState.getBlock() instanceof MagmaBlock) {
                for (Direction f : Direction.values()) {
                    BlockPos offsetPos = pos.relative(f);
                    if (level.getBlockState(offsetPos).getBlock() instanceof TntBlock) {
                        TntBlock.explode(level, offsetPos);
                        level.setBlock(offsetPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Swaps all the armor slots of armor stand and player when shift right-clicked
     */
    public static boolean triggerArmorStandSwap(Player player, Entity target, boolean config) {
        if (player.isCrouching() && config && !player.level().isClientSide() && !player.isSpectator()
                && target instanceof ArmorStand armorStand) {
            for (EquipmentSlot equipmentSlotType : EquipmentSlot.values()) {
                if (equipmentSlotType.getType() == EquipmentSlot.Type.ARMOR) {
                    swapSlot(player, armorStand, equipmentSlotType);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Handles burning of baby zombie and creepers in daylight
     */
    public static void triggerMobsBurnInSun(LivingEntity livingEntity, boolean creeperConfig, boolean babyZombieConfig) {
        if (livingEntity instanceof Creeper && creeperConfig ||
                livingEntity instanceof Zombie && livingEntity.isBaby() && babyZombieConfig) {
            boolean flag = canBurn(livingEntity);
            if (flag) {
                ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
                // Damages the helmet if its present
                if (!itemStack.isEmpty()) {
                    if (itemStack.isDamageableItem()) {
                        itemStack.setDamageValue(itemStack.getDamageValue() + livingEntity.getRandom().nextInt(2));
                        if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) {
                            livingEntity.broadcastBreakEvent(EquipmentSlot.HEAD);
                            livingEntity.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }
                    flag = false;
                }
                if (flag) {
                    livingEntity.setSecondsOnFire(8);
                }
            }
        }
    }

    /**
     * Rotates the item frame in reverse when shift right-clicked
     */
    public static boolean triggerItemFrameReverse(Entity target, Player player, boolean config) {
        if (target instanceof ItemFrame frame && player.isShiftKeyDown() && config) {
            int rotation = frame.getRotation() - 1;
            if (rotation < 0)
                rotation = 7;
            frame.setRotation(rotation);
            return true;
        }
        return false;
    }

    /**
     * Handles spawner block entity placement
     */
    public static void triggerSpawnerPlacement(Level level, BlockPos pos, ItemStack stack, boolean config) {
        if (!level.isClientSide() && config && !stack.isEmpty() && stack.getItem() == Items.SPAWNER) {
            CompoundTag spawnerDataNBT = stack.getOrCreateTag().getCompound(SPAWNER_TAG);
            if (!spawnerDataNBT.isEmpty()) {
                BlockEntity tile = level.getBlockEntity(pos);
                if (tile instanceof SpawnerBlockEntity spawner) {
                    spawner.getSpawner().load(level, pos, spawnerDataNBT);
                }
            }
        }
    }

    /**
     * Handles Spawner break logic
     */
    public static boolean triggerSpawnerBreak(Level level, BlockPos pos, BlockState state, Player player, boolean config, boolean forge) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        int lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem());
        if (state.getBlock() instanceof SpawnerBlock && !level.isClientSide() && blockEntity instanceof SpawnerBlockEntity && config && lvl >= 1) {
            ItemStack drop = new ItemStack(Blocks.SPAWNER);
            CompoundTag spawnerData = ((SpawnerBlockEntity) blockEntity).getSpawner().save(new CompoundTag());
            CompoundTag stackTag = new CompoundTag();
            spawnerData.remove("Delay");
            stackTag.put(SPAWNER_TAG, spawnerData);
            drop.setTag(stackTag);

            Block.popResource(player.getCommandSenderWorld(), pos, drop);
            level.removeBlockEntity(pos);
            level.destroyBlock(pos, false);
            return true;
        }
        return !forge;
    }
}