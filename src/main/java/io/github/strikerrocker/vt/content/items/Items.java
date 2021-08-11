package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadItem;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteEntity;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteItem;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Arrays;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;
import static io.github.strikerrocker.vt.content.blocks.Blocks.BLOCKS;

/**
 * Handles everything related to items
 */
public class Items extends Feature {
    @ObjectHolder(MOD_ID + ":dynamite")
    public static final EntityType<DynamiteEntity> DYNAMITE_TYPE = null;
    public static final Item CRAFTING_PAD = new CraftingPadItem();
    public static final Item DYNAMITE = new DynamiteItem();
    private static final Item SLIME_BUCKET = new SlimeBucketItem();
    private static final Item FRIED_EGG = new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.6f).build()).tab(CreativeModeTab.TAB_FOOD)).setRegistryName("friedegg");
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
                DispenserBlock.registerBehavior(DYNAMITE, new AbstractProjectileDispenseBehavior() {
                    @Override
                    protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
                        return new DynamiteEntity(DYNAMITE_TYPE, worldIn);
                    }
                }));
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        /**
         * Registers EntityType
         *
         * @param event Registry event for EntityType
         */
        @SubscribeEvent
        public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(EntityType.Builder.<DynamiteEntity>of(DynamiteEntity::new, MobCategory.MISC).
                    setCustomClientFactory((spawnEntity, world) -> DYNAMITE_TYPE.create(world)).setTrackingRange(64).setUpdateInterval(20).build(MOD_ID + ":dynamite").setRegistryName(new ResourceLocation(MOD_ID, "dynamite")));
        }

        /**
         * Registers RecipeSerializer for blocks
         *
         * @param event Registry event for RecipeSerializer
         */
        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
            CraftingHelper.register(ItemConditions.Serializer.INSTANCE);
        }

        /**
         * Register ItemBlocks and Items
         *
         * @param event Registry event for Item
         */
        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event) {
            VanillaTweaks.LOGGER.info("Registering Items");
            event.getRegistry().registerAll(CRAFTING_PAD, SLIME_BUCKET, DYNAMITE, FRIED_EGG);
            Arrays.stream(BLOCKS).map(block -> new BlockItem(block, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)).setRegistryName(block.getRegistryName())).forEach(item -> event.getRegistry().register(item));
        }
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
            event.registerEntityRenderer(DYNAMITE_TYPE, ThrownItemRenderer::new);
        }
    }
}