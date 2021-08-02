package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SelfPlanting extends Feature {

    private ForgeConfigSpec.IntValue despawnTime;
    private ForgeConfigSpec.IntValue chanceToPlant;
    private ForgeConfigSpec.BooleanValue selfPlanting;

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        selfPlanting = builder
                .translation("config.vanillatweaks:selfPlanting")
                .comment("Want seeds to auto-plant themselves when broken?")
                .define("selfPlanting", true);
        despawnTime = builder
                .translation("config.vanillatweaks:despawnTime")
                .comment("How long a plant item should take to despawn (and attempt to plant) Default Minecraft is 6000.")
                .defineInRange("despawnTime", 6000, 0, Integer.MAX_VALUE);
        chanceToPlant = builder
                .translation("config.vanillatweaks:chanceToPlant")
                .comment("Percentage chance to plant")
                .defineInRange("chanceToPlant", 100, 0, 100);
    }

    /**
     * Check if the despawning item can plant if not restore default lifespan of the item
     */
    @SubscribeEvent
    public void itemDecay(ItemExpireEvent event) {
        if (!plant(event.getEntityItem()))
            event.setExtraLife(6000 - event.getEntityItem().lifespan);
    }

    /**
     * If item is of plantable then change the lifespan
     */
    @SubscribeEvent
    public void itemToss(EntityJoinWorldEvent event) {
        if (selfPlanting.get() && !event.getWorld().isClientSide() && event.getEntity() instanceof ItemEntity itemEntity) {
            Item item = itemEntity.getItem().getItem();
            Block block = Block.byItem(item);
            if (item instanceof BlockItem && block instanceof IPlantable && !(block instanceof FlowerBlock)) {
                itemEntity.lifespan = despawnTime.get();
            }
        }
    }

    /**
     * Handles the planting logic
     *
     * @param entity The item entity to be planted
     * @return if the item lifespan is to be set as default
     */
    public boolean plant(ItemEntity entity) {
        Level world = entity.getCommandSenderWorld();
        ItemStack stack = entity.getItem().copy();
        Item item = stack.getItem();
        Block block = Block.byItem(item);
        BlockPos entityPos = new BlockPos(entity.blockPosition());
        if (selfPlanting.get() && item instanceof BlockItem && block instanceof IPlantable && !(block instanceof FlowerBlock)) {
            if (world.random.nextInt() > chanceToPlant.get()) {
                FakePlayer player = Utils.getFakePlayer(world);
                Vec3 entityVec = new Vec3(entityPos.getX(), entityPos.getY(), entityPos.getZ());
                BlockHitResult rayTraceResult = entity.level.clip(
                        new ClipContext(entityVec.add(0, 2, 0), entityVec.add(0, -1, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
                InteractionResult result = item.useOn(new UseOnContext(player, InteractionHand.MAIN_HAND, rayTraceResult));
                if (result == InteractionResult.SUCCESS || result == InteractionResult.CONSUME) {
                    if (stack.getCount() > 0) {
                        stack.shrink(1);
                    }
                    return true;
                }
                if (stack.getCount() > 0) {
                    world.addFreshEntity(new ItemEntity(world, entityPos.getX(), entityPos.getY() + 1, entityPos.getZ(), stack));
                }
            }
            return false;
        }
        return true;
    }
}