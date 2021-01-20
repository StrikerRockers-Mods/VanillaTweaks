package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.compat.CuriosCompat;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadItem;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteEntity;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.ObjectHolder;

import static io.github.strikerrocker.vt.VTModInfo.MODID;

public class Items extends Feature {
    @ObjectHolder(MODID + ":dynamite")
    public static final EntityType<DynamiteEntity> DYNAMITE_TYPE = null;
    private static final Item BINOCULARS = new BinocularItem();
    private static final Item LENS = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName("lens");
    private static final Item FRIED_EGG = new Item(new Item.Properties().food(new Food.Builder().hunger(5).saturation(0.6f).build()).group(ItemGroup.FOOD)).setRegistryName("friedegg");
    private static final Item SLIME_BUCKET = new SlimeBucketItem();
    public static ForgeConfigSpec.IntValue dynamiteCooldown;
    public static ForgeConfigSpec.IntValue dynamiteExplosionPower;
    public static Item CRAFTING_PAD = new CraftingPadItem();
    public static Item DYNAMITE = new DynamiteItem();
    public static ForgeConfigSpec.BooleanValue enablePad;
    static ForgeConfigSpec.BooleanValue enableDynamite;
    static ForgeConfigSpec.BooleanValue enableSlimeBucket;
    static ForgeConfigSpec.DoubleValue binocularZoomAmount;
    static ForgeConfigSpec.BooleanValue enableFriedEgg;

    @SubscribeEvent
    public void onFOVChange(FOVUpdateEvent event) {
        if (event.getEntity() != null && binocularZoomAmount.get() != 0) {
            ItemStack helmet = event.getEntity().getItemStackFromSlot(EquipmentSlotType.HEAD);
            if (!helmet.isEmpty() && helmet.getItem() == BINOCULARS) {
                event.setNewfov((float) (event.getFov() / binocularZoomAmount.get()));
            } else if (ModList.get().isLoaded("curios")) {
                if (CuriosCompat.isStackInCuriosSlot(event.getEntity(), new ItemStack(Items.BINOCULARS))) {
                    event.setNewfov((float) (event.getFov() / binocularZoomAmount.get()));
                }
            }
        }
    }

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
        binocularZoomAmount = builder
                .translation("config.vanillatweaks:binocularZoomAmount")
                .comment("How much do you want the binoculars to zoom? ( 0 = disabled)")
                .defineInRange("binocularZoomAmount", 4, 0, Double.MAX_VALUE);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setup() {
        DispenserBlock.registerDispenseBehavior(DYNAMITE, new ProjectileDispenseBehavior() {
            @Override
            protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return new DynamiteEntity(worldIn, position.getX(), position.getY(), position.getZ());
            }
        });
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(EntityType.Builder.<DynamiteEntity>create(DynamiteEntity::new, EntityClassification.MISC).
                    setCustomClientFactory((spawnEntity, world) -> DYNAMITE_TYPE.create(world)).setTrackingRange(64).setUpdateInterval(20).build(MODID + ":dynamite").setRegistryName(new ResourceLocation(MODID, "dynamite")));
        }

        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
            CraftingHelper.register(ItemConditions.Serializer.INSTANCE);
        }

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event) {
            VanillaTweaks.LOGGER.info("Registering Items");
            event.getRegistry().registerAll(CRAFTING_PAD, SLIME_BUCKET, DYNAMITE, BINOCULARS, LENS, FRIED_EGG);
        }

        @SubscribeEvent
        public static void sendImc(InterModEnqueueEvent event) {
            if (ModList.get().isLoaded("curios"))
                CuriosCompat.sendImc();
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onModelRegister(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(DYNAMITE_TYPE,
                    manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
        }
    }
}