package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onToolTipEvent(ItemTooltipEvent event) {
        if (event.getFlags().isAdvanced() && enableBeehiveTooltips.get()) {
            if (event.getItemStack().getItem() instanceof BlockItem && ((BlockItem) event.getItemStack().getItem()).getBlock() instanceof BeehiveBlock) {
                CompoundNBT tag = event.getItemStack().getTag();
                if (tag != null) {
                    CompoundNBT beTag = tag.getCompound("BlockEntityTag");
                    ListNBT bees = beTag.getList("Bees", 10);
                    int numBees = bees.size();
                    CompoundNBT blockStateTag = tag.getCompound("BlockStateTag");
                    String honeyLvl = blockStateTag.getString("honey_level");
                    System.out.println("bees " + bees);
                    System.out.println("lvl " + honeyLvl);
                    event.getToolTip().add(new TranslationTextComponent("vanillatweaks.bees").append(new StringTextComponent(String.format("%d", numBees))));
                    event.getToolTip().add(new TranslationTextComponent("vanillatweaks.honey.lvl").append(new StringTextComponent(String.format("%s", honeyLvl))));
                }
            }
        }
    }
}
