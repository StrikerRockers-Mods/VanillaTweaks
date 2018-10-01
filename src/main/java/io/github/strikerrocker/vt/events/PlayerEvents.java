package io.github.strikerrocker.vt.events;

import com.google.common.collect.Lists;
import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.entities.EntitySitting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static io.github.strikerrocker.vt.events.VTEventHandler.swapSlot;
import static io.github.strikerrocker.vt.handlers.ConfigHandler.miscellanious;

@Mod.EventBusSubscriber(modid = VTModInfo.MODID)
public class PlayerEvents {
    /**
     * Swaps the players armour with armor stand's armour
     *
     * @param event EntityInteractSpecific
     */
    @SubscribeEvent
    public static void onEntityInteractSpecific(EntityInteractSpecific event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if (player.isSneaking() && miscellanious.armourStandSwapping) {
            if (target.world.isRemote || player.isSpectator() || player.isCreative() || !(target instanceof EntityArmorStand) || !miscellanious.armourStandSwapping)
                return;
            event.setCanceled(true);
            EntityArmorStand armorStand = (EntityArmorStand) target;
            swapSlot(player, armorStand, EntityEquipmentSlot.HEAD);
            swapSlot(player, armorStand, EntityEquipmentSlot.CHEST);
            swapSlot(player, armorStand, EntityEquipmentSlot.LEGS);
            swapSlot(player, armorStand, EntityEquipmentSlot.FEET);
        }
    }

    /**
     * @param event The RightClickBlockEvent
     */
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();
        /*
         *Allows the player to edit and clear signs
         */
        boolean success = false;
        TileEntity te = world.getTileEntity(event.getPos());
        if (te instanceof TileEntitySign) {
            TileEntitySign sign = (TileEntitySign) te;
            if (player.isSneaking() && miscellanious.clearSigns) {
                ITextComponent[] text = new ITextComponent[]{new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("")};
                ObfuscationReflectionHelper.setPrivateValue(TileEntitySign.class, sign, text, "signText", "field_145915_a");
                success = true;
            } else if (miscellanious.editSigns) {
                player.openEditSign(sign);
                success = true;
            }
        }
        if (success) {
            event.setCanceled(true);
            event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
        }
        /*
         *Allows the sitting on slab's and stair's.
         */
        if (!world.isRemote && miscellanious.stairSit) {
            BlockPos p = event.getPos();
            IBlockState s = world.getBlockState(p);
            Block b = world.getBlockState(p).getBlock();

            if ((b instanceof BlockSlab || b instanceof BlockStairs) && !EntitySitting.OCCUPIED.containsKey(p) && player.getHeldItemMainhand() == ItemStack.EMPTY) {
                if (b instanceof BlockSlab && s.getValue(BlockSlab.HALF) != BlockSlab.EnumBlockHalf.BOTTOM)
                    return;
                else if (b instanceof BlockStairs && s.getValue(BlockStairs.HALF) != BlockStairs.EnumHalf.BOTTOM)
                    return;

                EntitySitting sit = new EntitySitting(world, p);

                world.spawnEntity(sit);
                player.startRiding(sit);
            }
        }
    }

    /**
     * @param event The EntityInteractEvent
     */
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        ItemStack heldItem = !player.getHeldItemMainhand().isEmpty() ? player.getHeldItemMainhand() : !player.getHeldItemOffhand().isEmpty() ? player.getHeldItemOffhand() : ItemStack.EMPTY;
        /*
          Makes is so that you shift click item frame to reverse it
         */
        if (event.getTarget() instanceof EntityItemFrame && event.getEntityPlayer().isSneaking()) {
            EntityItemFrame frame = (EntityItemFrame) event.getTarget();
            frame.setItemRotation(frame.getRotation() - 2);
        }
        /*
          Allows a player to shear name tags off living entities
         */
        if (!heldItem.isEmpty()) {
            World world = player.world;
            if (heldItem.getItem() instanceof ItemShears && target instanceof EntityLivingBase && target.hasCustomName() && !world.isRemote) {
                target.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1, 1);
                ItemStack nameTag = new ItemStack(Items.NAME_TAG).setStackDisplayName(target.getCustomNameTag());
                target.entityDropItem(nameTag, 0);
                target.setCustomNameTag("");
                heldItem.damageItem(1, player);
            }
        }
    }

    /**
     * Unlocks all the recipes
     *
     * @param event PlayerLoggedIn evemt
     */
    @SubscribeEvent
    public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.unlockRecipes(Lists.newArrayList(CraftingManager.REGISTRY));
    }

}