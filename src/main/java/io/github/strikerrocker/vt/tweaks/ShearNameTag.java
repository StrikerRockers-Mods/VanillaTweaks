package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ShearNameTag extends Feature {
    private boolean shearOffNameTag;

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        ItemStack heldItem = !player.getHeldItemMainhand().isEmpty() ? player.getHeldItemMainhand() : player.getHeldItemOffhand();
        if (shearOffNameTag && !heldItem.isEmpty()) {
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

    @Override
    public void syncConfig(Configuration config, String category) {
        shearOffNameTag = config.get(category, "shearOffNameTag", true, "Want to shear nametag of named entities?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

}