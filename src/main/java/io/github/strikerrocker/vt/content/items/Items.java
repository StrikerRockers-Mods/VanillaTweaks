package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.compat.baubles.BaubleTools;
import io.github.strikerrocker.vt.content.items.craftingpad.ItemCraftingPad;
import io.github.strikerrocker.vt.content.items.dynamite.EntityDynamite;
import io.github.strikerrocker.vt.content.items.dynamite.ItemDynamite;
import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Items extends Feature {
    private static final ItemArmor.ArmorMaterial binocular_material = EnumHelper.addArmorMaterial("binoculars", VTModInfo.MODID + ":binoculars", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    private static final Item binocular = new ItemArmor(binocular_material, 0, EntityEquipmentSlot.HEAD).setMaxStackSize(1).setRegistryName("binoculars").setTranslationKey("binoculars");
    private static final Item lens = new Item().setTranslationKey("lens").setRegistryName("lens");
    private static final Item friedEgg = new ItemFood(3, 0.6f, false).setRegistryName("friedegg").setTranslationKey("friedegg");
    public static Item craftingPad = new ItemCraftingPad("pad");
    public static Item dynamite = new ItemDynamite("dynamite");
    public static boolean enablePad;
    static boolean enableDynamite;
    static boolean enableSlimeBucket;
    static float binocularZoomAmount;
    private static Item slimeBucket = new ItemSlimeBucket("slime");
    private static Item baubleBino;

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        VanillaTweaks.logInfo("Registering Entities");
        event.getRegistry().register(EntityEntryBuilder.create().entity(EntityDynamite.class).id(new ResourceLocation(VTModInfo.MODID, "dynamite"), 0).name("dynamite").tracker(64, 1, false).build());
    }

    @SubscribeEvent
    public void onFOVChange(FOVUpdateEvent event) {
        if (event.getEntity() != null && binocularZoomAmount != 0) {
            ItemStack helmet = event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            if ((!helmet.isEmpty() && helmet.getItem() == binocular))
                event.setNewfov(event.getFov() / binocularZoomAmount);
            else if (Loader.isModLoaded("baubles")) {
                if (BaubleTools.hasProbeGoggle(event.getEntity())) {
                    event.setNewfov(event.getFov() / binocularZoomAmount);
                }
            }
        }
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        enablePad = config.get(category, "craftingPad", true, "Do you want a portable crafting table?").getBoolean();
        enableSlimeBucket = config.get(category, "slimeBucket", true, "Want to identity slime chunks in-game?").getBoolean();
        enableDynamite = config.get(category, "dynamite", true, "Want a less effective but throwable TNT?").getBoolean();
        binocularZoomAmount = (float) Utils.get(config, category, "binocularZoomAmount", 4, "How much do you want the binoculars to zoom? ( 0 = disabled)", true).setMinValue(0).setRequiresMcRestart(false).getDouble();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {
        VanillaTweaks.logInfo("Registering Items");
        event.getRegistry().registerAll(craftingPad, slimeBucket, dynamite, binocular, lens, friedEgg);
        if (Loader.isModLoaded("baubles")) {
            baubleBino = BaubleTools.initBinocularBauble();
            event.getRegistry().register(baubleBino);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        Utils.registerItemRenderer(craftingPad, 0);
        Utils.registerItemRenderer(slimeBucket, 0);
        Utils.registerItemRenderer(dynamite, 0);
        Utils.registerItemRenderer(binocular, 0);
        Utils.registerItemRenderer(lens, 0);
        Utils.registerItemRenderer(friedEgg, 0);

        RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, manager -> new RenderSnowball<>(manager, dynamite, Minecraft.getMinecraft().getRenderItem()));
    }

    @Override
    public void preInit() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(dynamite, new BehaviorProjectileDispense() {
            @Override
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return new EntityDynamite(worldIn, position.getX(), position.getY(), position.getZ());
            }
        });
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GameRegistry.addSmelting(net.minecraft.init.Items.EGG, new ItemStack(friedEgg), 0.35F);
    }
}