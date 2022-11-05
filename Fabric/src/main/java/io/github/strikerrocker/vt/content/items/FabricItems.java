package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.CommonObjects;
import io.github.strikerrocker.vt.content.SlimeBucketItem;
import io.github.strikerrocker.vt.content.blocks.FabricBlocks;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadItem;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteEntity;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteItem;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Position;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

public class FabricItems extends Feature {
    public static final EntityType<DynamiteEntity> DYNAMITE_TYPE = Registry.register(Registry.ENTITY_TYPE,
            new ResourceLocation(MOD_ID, "dynamite"),
            FabricEntityTypeBuilder.create(MobCategory.MISC, DynamiteEntity::new).dimensions(EntityDimensions.fixed(0, 0))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );
    public static Item DYNAMITE;

    static {
        DYNAMITE = Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "dynamite"), new DynamiteItem());
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "charcoal_block"), CommonObjects.CHARCOAL_BLOCK_ITEM);
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "sugar_block"), CommonObjects.SUGAR_BLOCK_ITEM);
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "flint_block"), CommonObjects.FLINT_BLOCK_ITEM);
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "crafting_pad"), new CraftingPadItem());
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "slime_bucket"), new SlimeBucketItem());
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "pedestal"), new BlockItem(FabricBlocks.PEDESTAL_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "fried_egg"), CommonObjects.FRIED_EGG);
    }

    /**
     * Register ItemBlocks and Items
     */
    @Override
    public void initialize() {
        if (VanillaTweaksFabric.config.content.enableDynamite) {
            DispenserBlock.registerBehavior(DYNAMITE, new AbstractProjectileDispenseBehavior() {
                @Override
                protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
                    DynamiteEntity entity = new DynamiteEntity(DYNAMITE_TYPE, level);
                    entity.setPos(position.x(), position.y(), position.z());
                    return entity;
                }
            });
        }
    }
}