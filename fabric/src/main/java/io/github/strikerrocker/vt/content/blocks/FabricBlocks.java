package io.github.strikerrocker.vt.content.blocks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.CommonObjects;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalBlock;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalBlockEntity;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

public class FabricBlocks extends Feature {
    public static final ResourceLocation PEDESTAL_IDENTIFIER = new ResourceLocation(MOD_ID, "pedestal");
    public static final Block PEDESTAL_BLOCK = Registry.register(BuiltInRegistries.BLOCK, PEDESTAL_IDENTIFIER, new PedestalBlock());
    public static MenuType<PedestalScreenHandler> PEDESTAL_SCREEN_HANDLER;
    public static BlockEntityType<PedestalBlockEntity> PEDESTAL_TYPE;

    /**
     * Registers blocks
     */
    @Override
    public void initialize() {
        if (VanillaTweaksFabric.config.content.enableStorageBlocks) {
            FuelRegistry.INSTANCE.add(CommonObjects.CHARCOAL_BLOCK, 16000);
        }
        PEDESTAL_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, PEDESTAL_IDENTIFIER, new MenuType<>(PedestalScreenHandler::new, FeatureFlags.DEFAULT_FLAGS));
        PEDESTAL_TYPE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, PEDESTAL_IDENTIFIER, FabricBlockEntityTypeBuilder.create(PedestalBlockEntity::new, PEDESTAL_BLOCK).build(null));
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MOD_ID, "charcoal_block"), CommonObjects.CHARCOAL_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MOD_ID, "sugar_block"), CommonObjects.SUGAR_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MOD_ID, "flint_block"), CommonObjects.FLINT_BLOCK);
    }
}