package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BeehiveTooltips extends Feature {

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

    /**
     * Shows the number of bees and honey level of bee hives
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onToolTipEvent(ItemTooltipEvent event) {
        if (event.getFlags().isAdvanced() && enableBeehiveTooltips.get() &&
                event.getItemStack().getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BeehiveBlock) {
            CompoundTag tag = event.getItemStack().getTag();
            if (tag != null) {
                CompoundTag beTag = tag.getCompound("BlockEntityTag");
                ListTag bees = beTag.getList("Bees", 10);
                int numBees = bees.size();
                CompoundTag blockStateTag = tag.getCompound("BlockStateTag");
                String honeyLvl = blockStateTag.getString("honey_level");
                event.getToolTip().add(new TranslatableComponent("vanillatweaks.bees").append(new TextComponent(String.format("%d", numBees))));
                event.getToolTip().add(new TranslatableComponent("vanillatweaks.honey.lvl").append(new TextComponent(String.format("%s", honeyLvl))));
            }
        }
    }
}
