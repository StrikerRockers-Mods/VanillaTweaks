package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.items.craftingpad.ItemCraftingPad;
import io.github.strikerrocker.vt.content.items.dynamite.EntityDynamite;
import io.github.strikerrocker.vt.content.items.dynamite.ItemDynamite;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Items extends Feature {
    public static Item craftingPad = new ItemCraftingPad("pad");
    static boolean enablePad;
    public static Item dynamite = new ItemDynamite("dynamite");
    static boolean enableSlimeBucket;
    static boolean enableDynamite;
    private static Item slimeBucket = new ItemSlimeBucket("slime");

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        VanillaTweaks.logInfo("Registering Entities");
        event.getRegistry().register(EntityEntryBuilder.create().entity(EntityDynamite.class).id(new ResourceLocation(VTModInfo.MODID, "dynamite"), 0).name("dynamite").tracker(64, 1, false).build());
    }

    @Override
    public void syncConfig(Configuration config, String module) {
        enablePad = config.get(module, "craftingPad", true, "Do you want a portable crafting table?").getBoolean();
        enableSlimeBucket = config.get(module, "slimeBucket", true, "Want to identity slime chunks in-game?").getBoolean();
        enableDynamite = config.get(module, "dynamite", true, "").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onRegister(RegistryEvent.Register<Item> event) {
        VanillaTweaks.logInfo("Registering Items");
        event.getRegistry().registerAll(craftingPad, slimeBucket, dynamite);
    }

    @SubscribeEvent
    public void onModelRegister(ModelRegistryEvent event) {
        registerItemRenderer(craftingPad, 0);
        registerItemRenderer(slimeBucket, 0);
        registerItemRenderer(dynamite, 0);

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

    @SideOnly(Side.CLIENT)
    private void registerItemRenderer(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(VTModInfo.MODID + ":" + item.getRegistryName().getPath(), "inventory"));
    }
}