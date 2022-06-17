package io.github.strikerrocker.vt.content.blocks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.CommonBlocks;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalBlock;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalBlockEntity;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

public class FabricBlocks extends Feature {
    public static final ResourceLocation PEDESTAL_IDENTIFIER = new ResourceLocation(MOD_ID, "pedestal");
    public static final Block PEDESTAL_BLOCK = Registry.register(Registry.BLOCK, PEDESTAL_IDENTIFIER, new PedestalBlock());
    public static final MenuType<PedestalScreenHandler> PEDESTAL_SCREEN_HANDLER;
    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL_TYPE;

    static {
        PEDESTAL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(PEDESTAL_IDENTIFIER, PedestalScreenHandler::new);
        PEDESTAL_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, PEDESTAL_IDENTIFIER, FabricBlockEntityTypeBuilder.create(PedestalBlockEntity::new, PEDESTAL_BLOCK).build(null));
        if (VanillaTweaksFabric.config.content.enableStorageBlocks) {
            Registry.register(Registry.BLOCK, new ResourceLocation(MOD_ID, "charcoal_block"), CommonBlocks.CHARCOAL_BLOCK);
            Registry.register(Registry.BLOCK, new ResourceLocation(MOD_ID, "sugar_block"), CommonBlocks.SUGAR_BLOCK);
            Registry.register(Registry.BLOCK, new ResourceLocation(MOD_ID, "flint_block"), CommonBlocks.FLINT_BLOCK);
        }
    }

    /**
     * Registers blocks
     */
    @Override
    public void initialize() {
        if (VanillaTweaksFabric.config.content.enableStorageBlocks) {
            FuelRegistry.INSTANCE.add(CommonBlocks.CHARCOAL_BLOCK, 16000);
        }
    }
}