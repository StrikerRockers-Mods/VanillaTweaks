package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.blocks.BlockInit;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadItem;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteEntity;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteItem;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

/**
 * Handles everything related to items
 */
public class ItemInit extends Feature {
    // Deferred Registries
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    // EntityType
    public static final RegistryObject<Item> DYNAMITE = ITEMS.register("dynamite", DynamiteItem::new);
    public static final RegistryObject<EntityType<DynamiteEntity>> DYNAMITE_TYPE = ENTITY_TYPES.register("dynamite", () ->
            EntityType.Builder.<DynamiteEntity>of(DynamiteEntity::new, MobCategory.MISC).
                    setTrackingRange(64).setUpdateInterval(20).build(MOD_ID + ":dynamite"));
    // Items
    public static final RegistryObject<Item> CRAFTING_PAD = ITEMS.register("pad", CraftingPadItem::new);
    private static final RegistryObject<Item> SLIME_BUCKET = ITEMS.register("slime", SlimeBucketItem::new);
    private static final RegistryObject<Item> FRIED_EGG = ITEMS.register("friedegg", () ->
            new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.6f).build()).tab(CreativeModeTab.TAB_FOOD)));
    // BlockItems
    private static final RegistryObject<Item> CHARCOAL_BLOCK_ITEM = ITEMS.register("charcoalblock", () ->
            new BlockItem(BlockInit.CHARCOAL_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    private static final RegistryObject<Item> SUGAR_BLOCK_ITEM = ITEMS.register("sugarblock", () ->
            new BlockItem(BlockInit.SUGAR_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    private static final RegistryObject<Item> FLINT_BLOCK_ITEM = ITEMS.register("flintblock", () ->
            new BlockItem(BlockInit.FLINT_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    // Configs
    public static ForgeConfigSpec.IntValue dynamiteCooldown;
    public static ForgeConfigSpec.IntValue dynamiteExplosionPower;
    public static ForgeConfigSpec.BooleanValue enablePad;
    static ForgeConfigSpec.BooleanValue enableDynamite;
    static ForgeConfigSpec.BooleanValue enableSlimeBucket;
    static ForgeConfigSpec.BooleanValue enableFriedEgg;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
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
                    protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
                        return new DynamiteEntity(DYNAMITE_TYPE.get(), worldIn);
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