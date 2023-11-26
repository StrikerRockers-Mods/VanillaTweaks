package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.base.ForgeFeature;
import io.github.strikerrocker.vt.content.CommonObjects;
import io.github.strikerrocker.vt.content.SlimeBucketItem;
import io.github.strikerrocker.vt.content.blocks.ForgeBlocks;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadItem;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteEntity;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteItem;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

/**
 * Handles everything related to items
 */
public class ForgeItems extends ForgeFeature {
    // Deferred Registries
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MOD_ID);

    // EntityType
    public static final Supplier<Item> DYNAMITE = ITEMS.register("dynamite", DynamiteItem::new);
    public static final Supplier<EntityType<DynamiteEntity>> DYNAMITE_TYPE = ENTITY_TYPES.register("dynamite", () ->
            EntityType.Builder.of(DynamiteEntity::new, MobCategory.MISC).
                    setTrackingRange(64).setUpdateInterval(20).build(MOD_ID + ":dynamite"));

    // Items
    public static final Supplier<Item> CRAFTING_PAD = ITEMS.register("crafting_pad", CraftingPadItem::new);
    public static final Supplier<Item> SLIME_BUCKET = ITEMS.register("slime_bucket", SlimeBucketItem::new);
    public static final Supplier<Item> FRIED_EGG = ITEMS.register("fried_egg", () -> CommonObjects.FRIED_EGG);

    // BlockItems
    public static final Supplier<Item> CHARCOAL_BLOCK_ITEM = ITEMS.register("charcoal_block", () -> CommonObjects.CHARCOAL_BLOCK_ITEM);
    public static final Supplier<Item> FLINT_BLOCK_ITEM = ITEMS.register("flint_block", () -> CommonObjects.FLINT_BLOCK_ITEM);
    public static final Supplier<Item> SUGAR_BLOCK_ITEM = ITEMS.register("sugar_block", () -> CommonObjects.SUGAR_BLOCK_ITEM);
    public static final Supplier<Item> PEDESTAL_ITEM = ITEMS.register("pedestal", () ->
            new BlockItem(ForgeBlocks.PEDESTAL_BLOCK.get(), new Item.Properties()));

    // Configs
    public static ModConfigSpec.IntValue dynamiteCooldown;
    public static ModConfigSpec.IntValue dynamiteExplosionPower;
    public static ModConfigSpec.BooleanValue enablePad;
    static ModConfigSpec.BooleanValue enableDynamite;
    static ModConfigSpec.BooleanValue enableSlimeBucket;
    static ModConfigSpec.BooleanValue enableFriedEgg;

    public static void itemGroup(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(CommonObjects.CHARCOAL_BLOCK_ITEM);
            event.accept(CommonObjects.FLINT_BLOCK_ITEM);
            event.accept(CommonObjects.SUGAR_BLOCK_ITEM);
            event.accept(PEDESTAL_ITEM.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(CommonObjects.FRIED_EGG);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(CRAFTING_PAD.get());
            event.accept(SLIME_BUCKET.get());
            event.accept(DYNAMITE.get());
        }
    }

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
        enablePad = builder
                .translation("config.vanillatweaks:enablePad")
                .comment("Do you want a portable crafting table?")
                .define("enablePad", true);
        enableSlimeBucket = builder
                .translation("config.vanillatweaks:enableSlimeBucket")
                .comment("Want to identity slime chunks in-game?")
                .define("enableSlimeBucket", true);
        enableDynamite = builder
                .translation("config.vanillatweaks:enableDynamite")
                .comment("Want a less effective but throwable TNT?")
                .define("enableDynamite", true);
        dynamiteCooldown = builder
                .translation("config.vanillatweaks:dynamiteCooldown")
                .comment("Cooldown for the dynamite in ticks")
                .defineInRange("dynamiteCooldown", 20, 0, Integer.MAX_VALUE);
        dynamiteExplosionPower = builder
                .translation("config.vanillatweaks:dynamiteExplosionPower")
                .comment("Explosion power for dynamite. TNT has 4, Ender Crystal has 6")
                .defineInRange("dynamiteExplosionPower", 3, 0, 10);
        enableFriedEgg = builder
                .translation("config.vanillatweaks:enableFriedEgg")
                .comment("Want a food which can be obtained with eggs and heals 2.5 hunger?")
                .define("enableFriedEgg", true);
    }

    @Override
    public void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() ->
                DispenserBlock.registerBehavior(DYNAMITE.get(), new AbstractProjectileDispenseBehavior() {
                    @Override
                    protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
                        DynamiteEntity entity = new DynamiteEntity(DYNAMITE_TYPE.get(), level);
                        entity.setPos(position.x(), position.y(), position.z());
                        return entity;
                    }
                }));
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents {
        /**
         * Registers entity renderer
         *
         * @param event Registry event for Renderers
         */
        @SubscribeEvent
        public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(DYNAMITE_TYPE.get(), ThrownItemRenderer::new);
        }
    }
}