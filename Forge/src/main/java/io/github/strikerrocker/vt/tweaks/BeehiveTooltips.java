package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BeehiveTooltips extends ForgeFeature {

    private ForgeConfigSpec.BooleanValue enableBeehiveTooltips;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableBeehiveTooltips = builder.
                translation("config.vanillatweaks:enableBeehiveTooltips").
                comment("Want to see the no of bees or the honey level of an beehive?")
                .define("enableBeehiveTooltips", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onToolTipEvent(ItemTooltipEvent event) {
        if (event.getFlags().isAdvanced() && enableBeehiveTooltips.get() &&
                event.getItemStack().getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BeehiveBlock) {
            TweaksImpl.addBeeHiveTooltip(event.getItemStack(), event.getToolTip());
        }
    }
}