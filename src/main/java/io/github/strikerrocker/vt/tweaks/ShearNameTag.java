package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShearNameTag extends Feature {
    private ForgeConfigSpec.BooleanValue shearOffNameTag;

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        ItemStack heldItem = !player.getHeldItemMainhand().isEmpty() ? player.getHeldItemMainhand() : player.getHeldItemOffhand();
        if (shearOffNameTag.get() && !heldItem.isEmpty()) {
            World world = player.world;
            if (heldItem.getItem() instanceof ShearsItem && target instanceof LivingEntity && target.hasCustomName() && !world.isRemote) {
                target.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1, 1);
                ItemStack nameTag = new ItemStack(Items.NAME_TAG).setDisplayName(target.getCustomName());
                nameTag.getTag().putInt("RepairCost", 0);
                target.entityDropItem(nameTag, 0);
                target.setCustomName(null);
                heldItem.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(playerEntity.getActiveHand()));
            }
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        shearOffNameTag = builder
                .translation("config.vanillatweaks:shearOffNameTag")
                .comment("Want to shear nametag of named entities?")
                .define("shearOffNameTag", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

}