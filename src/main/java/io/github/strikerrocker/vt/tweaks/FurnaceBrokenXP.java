package io.github.strikerrocker.vt.tweaks;

import com.google.common.collect.Maps;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class FurnaceBrokenXP extends Feature {

    private ForgeConfigSpec.BooleanValue furnaceBrokenXP;

    private static void spawnXp(World world, int size, float xp, BlockPos pos) {
        int value;
        if (xp == 0.0F) {
            size = 0;
        } else if (xp < 1.0F) {
            value = MathHelper.floor((float) size * xp);
            if (value < MathHelper.ceil((float) size * xp) && Math.random() < (double) ((float) size * xp - (float) value)) {
                ++value;
            }
            size = value;
        }

        while (size > 0) {
            value = ExperienceOrbEntity.getXPSplit(size);
            size -= value;
            world.addEntity(new ExperienceOrbEntity(world, pos.getX(), pos.getY() + 0.5D, pos.getZ() + 0.5D, value));
        }

    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        furnaceBrokenXP = builder
                .translation("config.vanillatweaks:furnaceBrokenXP")
                .comment("Want furnace to drop stored xp when broken?")
                .define("furnaceBrokenXP", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onBlockBroken(BlockEvent.BreakEvent event) {
        World world = event.getPlayer().world;
        BlockPos pos = event.getPos();
        if (furnaceBrokenXP.get() && world.getBlockState(pos).getBlock() instanceof AbstractFurnaceBlock) {
            TileEntity blockEntity = world.getTileEntity(pos);
            if (blockEntity instanceof AbstractFurnaceTileEntity && !world.isRemote()) {
                Map<ResourceLocation, Integer> recipesUsed = Maps.newHashMap();
                CompoundNBT tag = new CompoundNBT();
                tag = blockEntity.write(tag);

                for (int i = 0; i < tag.getShort("RecipesUsedSize"); ++i) {
                    ResourceLocation identifier_1 = new ResourceLocation(tag.getString("RecipeLocation" + i));
                    int recipeAmt = tag.getInt("RecipeAmount" + i);
                    recipesUsed.put(identifier_1, recipeAmt);
                }
                for (Map.Entry<ResourceLocation, Integer> map : recipesUsed.entrySet()) {
                    world.getRecipeManager().getRecipe(map.getKey()).ifPresent((recipe_1) -> spawnXp(world, map.getValue(), ((AbstractCookingRecipe) recipe_1).getExperience(), pos));
                }
            }
        }
    }
}