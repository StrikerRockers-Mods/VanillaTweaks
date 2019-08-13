package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.compat.baubles.BaubleTools;
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
import net.minecraft.entity.IProjectile;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static io.github.strikerrocker.vt.VTModInfo.MODID;

public class Items extends Feature {
    @ObjectHolder(MODID + ":dynamite")
    public static final EntityType<DynamiteEntity> DYNAMITE_TYPE = null;
    private static final IArmorMaterial binocular_material = new BasicArmorMaterial(VTModInfo.MODID + ":binoculars", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, () -> Ingredient.fromItems(net.minecraft.item.Items.IRON_INGOT));
    private static final Item binocular = new ArmorItem(binocular_material, EquipmentSlotType.HEAD, new Item.Properties().maxStackSize(1)).setRegistryName("binoculars");
    private static final Item lens = new Item(new Item.Properties()).setRegistryName("lens");
    private static final Item friedEgg = new Item(new Item.Properties().food(new Food.Builder().hunger(3).saturation(0.6f).build())).setRegistryName("friedegg");
    public static Item craftingPad = new CraftingPadItem();
    public static Item dynamite = new DynamiteItem("dynamite");
    public static ForgeConfigSpec.BooleanValue enablePad;
    static ForgeConfigSpec.BooleanValue enableDynamite;
    static ForgeConfigSpec.BooleanValue enableSlimeBucket;
    static ForgeConfigSpec.DoubleValue binocularZoomAmount;
    private static Item slimeBucket = new SlimeBucketItem();
    private static Item baubleBino;

    @SubscribeEvent
    public void onFOVChange(FOVUpdateEvent event) {
        if (event.getEntity() != null && binocularZoomAmount.get() != 0) {
            ItemStack helmet = event.getEntity().getItemStackFromSlot(EquipmentSlotType.HEAD);
            if ((!helmet.isEmpty() && helmet.getItem() == binocular))
                event.setNewfov((float) (event.getFov() / binocularZoomAmount.get()));
            else if (ModList.get().isLoaded("baubles")) {
                if (BaubleTools.hasProbeGoggle(event.getEntity())) {
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
        binocularZoomAmount = builder
                .translation("config.vanillatweaks:binocularZoomAmount")
                .comment("How much do you want the binoculars to zoom? ( 0 = disabled)")
                .defineInRange("binocularZoomAmount", 4, 0, Double.MAX_VALUE);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {
        VanillaTweaks.LOGGER.info("Registering Items");
        event.getRegistry().registerAll(craftingPad, slimeBucket, dynamite, binocular, lens, friedEgg);
        if (ModList.get().isLoaded("baubles")) {
            baubleBino = BaubleTools.initBinocularBauble();
            event.getRegistry().register(baubleBino);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(DynamiteEntity.class, manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
    }

    @Override
    public void setup() {
        DispenserBlock.registerDispenseBehavior(dynamite, new ProjectileDispenseBehavior() {
            @Override
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return new DynamiteEntity(worldIn, position.getX(), position.getY(), position.getZ());
            }
        });
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(EntityType.Builder.<DynamiteEntity>create(DynamiteEntity::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> DYNAMITE_TYPE.create(world)).setTrackingRange(64).setUpdateInterval(20).build(MODID + ":dynamite").setRegistryName(new ResourceLocation(MODID, "entity_sit")));
        }
    }
}