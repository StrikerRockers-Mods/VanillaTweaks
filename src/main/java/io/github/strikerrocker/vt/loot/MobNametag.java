package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MobNametag extends Feature {
    private ForgeConfigSpec.BooleanValue namedMobsDropNameTag;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        namedMobsDropNameTag = builder
                .translation("config.vanillatweaks:namedMobsDropNameTag")
                .comment("Does a nametag drop when named mob dies?")
                .define("namedMobsDropNameTag", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        Level world = entity.level;
        if (!world.isClientSide() && world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && namedMobsDropNameTag.get() && entity.hasCustomName()) {
            ItemStack nameTag = new ItemStack(Items.NAME_TAG);
            nameTag.setHoverName(entity.getCustomName());
            nameTag.getTag().putInt("RepairCost", 0);
            entity.spawnAtLocation(nameTag, 0);
            entity.setCustomName(null);
        }
    }
}