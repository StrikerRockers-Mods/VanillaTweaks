package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadItem;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteEntity;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteItem;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static io.github.strikerrocker.vt.VanillaTweaks.MODID;

public class Items extends Feature {
    @ObjectHolder(MODID + ":dynamite")
    public static final EntityType<DynamiteEntity> DYNAMITE_TYPE = null;
    private static final Item FRIED_EGG = new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.6f).build()).tab(CreativeModeTab.TAB_FOOD)).setRegistryName("friedegg");
    private static final Item SLIME_BUCKET = new SlimeBucketItem();
    public static ForgeConfigSpec.IntValue dynamiteCooldown;
    public static ForgeConfigSpec.IntValue dynamiteExplosionPower;
    public static Item CRAFTING_PAD = new CraftingPadItem();
    public static Item DYNAMITE = new DynamiteItem();
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
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setup() {
        DispenserBlock.registerBehavior(DYNAMITE, new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
                return new DynamiteEntity(worldIn, position.x(), position.y(), position.z());
            }
        });
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(EntityType.Builder.<DynamiteEntity>of(DynamiteEntity::new, MobCategory.MISC).
                    setCustomClientFactory((spawnEntity, world) -> DYNAMITE_TYPE.create(world)).setTrackingRange(64).setUpdateInterval(20).build(MODID + ":dynamite").setRegistryName(new ResourceLocation(MODID, "dynamite")));
        }

        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
            CraftingHelper.register(ItemConditions.Serializer.INSTANCE);
        }

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event) {
            VanillaTweaks.LOGGER.info("Registering Items");
            event.getRegistry().registerAll(CRAFTING_PAD, SLIME_BUCKET, DYNAMITE, FRIED_EGG);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onModelRegister(ModelRegistryEvent event) {
            //TODO change to when possible RenderingRegistry.registerEntityRenderingHandler(DYNAMITE_TYPE, ThrownItemRenderer::new);
            EntityRenderers.register(DYNAMITE_TYPE, ThrownItemRenderer::new);
        }
    }
}