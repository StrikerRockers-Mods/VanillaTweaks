package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.CommonObjects;
import io.github.strikerrocker.vt.content.SlimeBucketItem;
import io.github.strikerrocker.vt.content.blocks.FabricBlocks;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadItem;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteEntity;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Position;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

public class FabricItems extends Feature {
    public static final EntityType<DynamiteEntity> DYNAMITE_TYPE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(MOD_ID, "dynamite"),
            FabricEntityTypeBuilder.create(MobCategory.MISC, DynamiteEntity::new).dimensions(EntityDimensions.fixed(0, 0))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );
    public static Item DYNAMITE;
    public static Item CRAFTING_PAD;
    public static Item SLIME_BUCKET_ITEM;
    public static Item PEDESTAL_ITEM;

    static {
        DYNAMITE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "dynamite"), new DynamiteItem());
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "charcoal_block"), CommonObjects.CHARCOAL_BLOCK_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "sugar_block"), CommonObjects.SUGAR_BLOCK_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "flint_block"), CommonObjects.FLINT_BLOCK_ITEM);
        CRAFTING_PAD = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "crafting_pad"), new CraftingPadItem());
        SLIME_BUCKET_ITEM = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "slime_bucket"), new SlimeBucketItem());
        PEDESTAL_ITEM = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "pedestal"), new BlockItem(FabricBlocks.PEDESTAL_BLOCK, new Item.Properties()));
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "fried_egg"), CommonObjects.FRIED_EGG);
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
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.accept(CommonObjects.CHARCOAL_BLOCK_ITEM);
            entries.accept(CommonObjects.SUGAR_BLOCK_ITEM);
            entries.accept(CommonObjects.FLINT_BLOCK_ITEM);
            entries.accept(PEDESTAL_ITEM);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> entries.accept(CommonObjects.FRIED_EGG));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(CRAFTING_PAD);
            entries.accept(SLIME_BUCKET_ITEM);
            entries.accept(DYNAMITE);
        });
    }
}