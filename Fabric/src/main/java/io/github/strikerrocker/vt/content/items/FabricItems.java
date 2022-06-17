package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.CommonItems;
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
        if (VanillaTweaksFabric.config.content.enableDynamite)
            DYNAMITE = Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "dynamite"), new DynamiteItem());
        if (VanillaTweaksFabric.config.content.enableStorageBlocks) {
            Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "charcoal_block"), CommonItems.CHARCOAL_BLOCK);
            Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "sugar_block"), CommonItems.SUGAR_BLOCK);
            Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "flint_block"), CommonItems.FLINT_BLOCK);
        }
        if (VanillaTweaksFabric.config.content.enableCraftingPad)
            Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "crafting_pad"), new CraftingPadItem());
        if (VanillaTweaksFabric.config.content.enableSlimeBucket)
            Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "slime_bucket"), new SlimeBucketItem());
        if (VanillaTweaksFabric.config.content.enablePedestal)
            Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "pedestal"), new BlockItem(FabricBlocks.PEDESTAL_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        if (VanillaTweaksFabric.config.content.enableFriedEgg)
            Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "fried_egg"), CommonItems.FRIED_EGG);
    }

    /**
     * Register ItemBlocks and Items
     */
    @Override
    public void initialize() {
        if (VanillaTweaksFabric.config.content.enableDynamite) {
            DispenserBlock.registerBehavior(DYNAMITE, new AbstractProjectileDispenseBehavior() {
                @Override
                protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
                    return new DynamiteEntity(DYNAMITE_TYPE, world);
                }
            });
        }
    }
}