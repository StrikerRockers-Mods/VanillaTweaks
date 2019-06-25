package io.github.strikerrocker.vt.tweaks.silkspawner;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SilkSpawner extends Feature {
    private static final String SPAWNER_TAG = "SilkSpawnerData";
    private boolean enableSilkSpawner;

    @Override
    public void syncConfig(Configuration config, String category) {
        enableSilkSpawner = config.get(category, "enableSilkSpawner", true, "Want the ability to move spawners with silk touch?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent event) {
        ItemStack stack = event.getPlayer().getHeldItem(event.getHand());
        if (enableSilkSpawner && stack.getItem() == Item.getItemFromBlock(Blocks.MOB_SPAWNER) && stack.hasTagCompound()) {
            NBTTagCompound stackTag = stack.getTagCompound();
            assert stackTag != null;
            NBTTagCompound spawnerDataNBT = stackTag.getCompoundTag(SPAWNER_TAG);
            if (!spawnerDataNBT.isEmpty()) {
                TileEntity tile = event.getWorld().getTileEntity(event.getPos());
                if (tile instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tile).getSpawnerBaseLogic().readFromNBT(spawnerDataNBT);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBreak(BlockEvent.BreakEvent event) {
        World world = event.getWorld();
        TileEntity tile = world.getTileEntity(event.getPos());
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, event.getPlayer().getHeldItemMainhand());
        if (event.getState().getBlock() instanceof BlockMobSpawner && !world.isRemote && tile instanceof TileEntityMobSpawner && enableSilkSpawner && lvl >= 1) {
            event.setExpToDrop(0);
            ItemStack drop = new ItemStack(Blocks.MOB_SPAWNER);
            NBTTagCompound spawnerData = ((TileEntityMobSpawner) tile).getSpawnerBaseLogic().writeToNBT(new NBTTagCompound());
            NBTTagCompound stackTag = new NBTTagCompound();
            spawnerData.removeTag("Delay");
            stackTag.setTag(SPAWNER_TAG, spawnerData);
            drop.setTagCompound(stackTag);

            Block.spawnAsEntity(world, event.getPos(), drop);
            //Does this cause problems w/ block protection?
            world.destroyBlock(event.getPos(), false);
            world.removeTileEntity(event.getPos());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onToolTipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.hasTagCompound()) {
            NBTTagCompound stackTag = stack.getTagCompound();
            assert stackTag != null;
            NBTTagCompound spawnerDataNBT = stackTag.getCompoundTag(SPAWNER_TAG);
            if (!spawnerDataNBT.isEmpty()) {
                DummyMobSpawnerLogic.DUMMY_MOB_SPAWNER_LOGIC.readFromNBT(spawnerDataNBT);
                Entity ent = DummyMobSpawnerLogic.DUMMY_MOB_SPAWNER_LOGIC.getCachedEntity();
                event.getToolTip().add(I18n.translateToLocalFormatted("tooltip.entity", ent.getName()));
            }
        }
    }

}
